/*
 * 以下大部分源代码来自:
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * 
 * */

package cn.gxf.spring.struts.integrate.security;

import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.Assert;

import cn.gxf.spring.struts2.integrate.dao.UserDao;
import cn.gxf.spring.struts2.integrate.service.SimpleRateLimitService;

public class WcDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{
	//~ Static fields/initializers =====================================================================================

    /**
     * The plaintext password used to perform {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
     * not found to avoid SEC-2056.
     */
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private Logger logger = LogManager.getLogger();
    //~ Instance fields ================================================================================================
    
    
    private PasswordEncoder passwordEncoder;
    
    private UserDao userDao;

    /**
     * The password used to perform {@link PasswordEncoder#isPasswordValid(String, String, Object)} on when the user is
     * not found to avoid SEC-2056. This is necessary, because some {@link PasswordEncoder} implementations will short circuit if the
     * password is not in a valid format.
     */
    private String userNotFoundEncodedPassword;

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;
    
    private SimpleRateLimitService rateLimitService;
    private int period; // seconds
    private int maxCount; // max tries in period no matter success or failure

    public WcDaoAuthenticationProvider() {
        setPasswordEncoder(new PlaintextPasswordEncoder());
    }

    //~ Methods ========================================================================================================

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
            messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                "Only UsernamePasswordAuthenticationToken is supported"));

        // Determine username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        
        // limiter
        boolean allowedFlag = rateLimitService.isActionAllowed(username, "login", 30/*second*/, 3/*maxCount*/);
        if (!allowedFlag){
        	throw new MyUserAuthorityException("当前用户请勿频繁操作");
        }
        String remoteAddr = ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
        allowedFlag = rateLimitService.isActionAllowed(remoteAddr, "login", 30/*second*/, 3/*maxCount*/);
        if (!allowedFlag){
        	throw new MyUserAuthorityException("请勿频繁操作");
        }

        boolean cacheWasUsed = true;
        //UserDetails user = this.userCache.getUserFromCache(username);
        UserDetails user = this.getUserCache().getUserFromCache(username);

        if (user == null) {
            cacheWasUsed = false;

            try {
                user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            } catch (UsernameNotFoundException notFound) {
                logger.debug("User '" + username + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                } else {
                    throw notFound;
                }
            }

            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }

        try {
            //preAuthenticationChecks.check(user);
        	this.getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
        } catch (AuthenticationException exception) {
        	////
        	if (exception instanceof BadCredentialsException){
        		userDao.decreaseUserAttempts(username);
        		int attempts = userDao.getUserAttempts(username); // 目前还剩余登录次数
        		if (attempts > 0){
        			throw new MyUserAuthorityException("密码错误, 还可以尝试"+attempts+"次");
        		}else{
        			throw new MyUserAuthorityException("用户被锁定, 无法登陆");
        		}
        	}
        	////
        	
            if (cacheWasUsed) {
                // There was a problem, so try again after checking
                // we're using latest data (i.e. not from the cache)
                cacheWasUsed = false;
                user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
                //preAuthenticationChecks.check(user);
                this.getPreAuthenticationChecks().check(user);
                additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
            } else {
                throw exception;
            }
        }

        //postAuthenticationChecks.check(user);
        this.getPostAuthenticationChecks().check(user);

        if (!cacheWasUsed) {
            //this.userCache.putUserInCache(user);
        	this.getUserCache().putUserInCache(user);
        }

        Object principalToReturn = user;

        //if (forcePrincipalAsString) {
        if (this.isForcePrincipalAsString()) {
            principalToReturn = user.getUsername();
        }

        WebAuthenticationDetails wauth = (WebAuthenticationDetails) authentication.getDetails();
        logger.debug("LoginUsername: " +authentication.getPrincipal() + ", LoginAddr: " + wauth.getRemoteAddress());
        
        // 验证成功, 尝试次数恢复
        userDao.resetUserAttemptLimit(username);
        // 记录登录信息
        userDao.recordUserLoginInfo(user.getUsername(), new Date(), wauth.getRemoteAddress());
        
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }
    
    @SuppressWarnings("deprecation")
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;

        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), userDetails);
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }

    protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException notFound) {
            if(authentication.getCredentials() != null) {
                String presentedPassword = authentication.getCredentials().toString();
                passwordEncoder.isPasswordValid(userNotFoundEncodedPassword, presentedPassword, null);
            }
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords.
     * If not set, the password will be compared as plain text.
     * <p>
     * For systems which are already using salted password which are encoded with a previous release, the encoder
     * should be of type {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise,
     * the recommended approach is to use {@code org.springframework.security.crypto.password.PasswordEncoder}.
     *
     * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder} types.
     */
    public void setPasswordEncoder(Object passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        if (passwordEncoder instanceof PasswordEncoder) {
            setPasswordEncoder((PasswordEncoder) passwordEncoder);
            return;
        }

        if (passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder) {
            final org.springframework.security.crypto.password.PasswordEncoder delegate =
                    (org.springframework.security.crypto.password.PasswordEncoder)passwordEncoder;
            setPasswordEncoder(new PasswordEncoder() {
                public String encodePassword(String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.encode(rawPass);
                }

                public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
                    checkSalt(salt);
                    return delegate.matches(rawPass, encPass);
                }

                private void checkSalt(Object salt) {
                    Assert.isNull(salt, "Salt value must be null when used with crypto module PasswordEncoder");
                }
            });

            return;
        }

        throw new IllegalArgumentException("passwordEncoder must be a PasswordEncoder instance");
    }

    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        this.userNotFoundEncodedPassword = passwordEncoder.encodePassword(USER_NOT_FOUND_PASSWORD, null);
        this.passwordEncoder = passwordEncoder;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code>
     * is a valid value, meaning the <code>DaoAuthenticationProvider</code>
     * will present <code>null</code> to the relevant <code>PasswordEncoder</code>.
     * <p>
     * Instead, it is recommended that you use an encoder which uses a random salt and combines it with
     * the password field. This is the default approach taken in the
     * {@code org.springframework.security.crypto.password} package.
     *
     * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */
    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    public void setRateLimitService(SimpleRateLimitService rateLimitService) {
		this.rateLimitService = rateLimitService;
	}
    
    public void setPeriod(int period) {
		this.period = period;
	}
    
    public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
    
    public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }
    
}
