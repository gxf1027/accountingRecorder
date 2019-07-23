package cn.gxf.spring.struts.integrate.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserLogin implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5431007489807218880L;
	
	private int id;
	private String username;
	private String password;
	private String enabled;
	private String description;
	private String emailAddr;
	private Integer attemptLimit;
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * mybatis:isEnabled与setEnabled产生混淆，所以注释掉一个
	 * */
	/*public String getEnabled() {
		return enabled;
	}*/

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmailAddr() {
		return emailAddr;
	}
	
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	
	public Integer getAttemptLimit() {
		return attemptLimit;
	}
	
	public void setAttemptLimit(Integer attemptLimit) {
		this.attemptLimit = attemptLimit;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		/*return this.enabled == "1" ? true : false;*/
		
		if (this.enabled == null)
			return false;
		
		return this.enabled.equals("1") ? true : false;

	}

	@Override
	public String toString() {
		return "UserLogin [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", description=" + description + ", authorities=" + authorities + "]";
	}

}
