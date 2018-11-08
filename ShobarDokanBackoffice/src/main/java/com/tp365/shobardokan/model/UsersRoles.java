package com.tp365.shobardokan.model;

import com.tp365.shobardokan.model.enums.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UsersRoles {
    private Integer id;
    private User user;
    private Role role;
    private Date createdAt;
    private Date deletedAt;
    
    public UsersRoles(Integer id, String role) {
    	this.user = new User();
    	this.role = new Role();
    	user.setId(id);
    	this.role.setRole(Roles.valueOf(role));
    }
}