package com.tp365.shobardokan.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Masum on 11/5/2018.
 */
@Data
public class AuthenticatedUser extends org.springframework.security.core.userdetails.User {
	
    private Integer id;
    
    public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthenticatedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public AuthenticatedUser addId(Integer id) {
        this.id = id;
        return this;
    }
}
