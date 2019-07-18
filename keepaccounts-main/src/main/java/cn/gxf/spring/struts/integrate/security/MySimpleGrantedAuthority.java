package cn.gxf.spring.struts.integrate.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/*
 * almost same to 'org.springframework.security.core.authority.SimpleGrantedAuthority',
 * but to avoid the following exception:
 * 
 * org.springframework.data.redis.serializer.SerializationException: 
 * Cannot deserialize; 
 * nested exception is com.esotericsoftware.kryo.KryoException: 
 * Class cannot be created (missing no-arg constructor): 
 * org.springframework.security.core.authority.SimpleGrantedAuthority
 * 
 * */
public final class MySimpleGrantedAuthority implements GrantedAuthority {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String role;

	public MySimpleGrantedAuthority(){
		role = "ROLE_USER";
	}
	
	public MySimpleGrantedAuthority(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	public String getAuthority() {
		return role;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof MySimpleGrantedAuthority) {
			return role.equals(((MySimpleGrantedAuthority) obj).role);
		}

		return false;
	}

	public int hashCode() {
		return this.role.hashCode();
	}

	public String toString() {
		return this.role;
	}
}