package cn.gxf.spring.cxf;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallback implements CallbackHandler{
	
    private SignatureUser user;

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

        WSPasswordCallback callback = (WSPasswordCallback) callbacks[0];

        String clientUsername = callback.getIdentifier();
        String serverPassword = user.getUserMap().get(clientUsername);
        if (serverPassword != null) {
            callback.setPassword(serverPassword);
        }

    }
    
    public SignatureUser getUser() {
		return user;
	}
    
    public void setUser(SignatureUser user) {
		this.user = user;
	}
}
