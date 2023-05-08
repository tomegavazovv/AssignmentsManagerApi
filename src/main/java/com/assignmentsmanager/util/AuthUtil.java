package com.assignmentsmanager.util;

import com.assignmentsmanager.domain.User;
import com.assignmentsmanager.enums.AuthorityEnum;

public class AuthUtil {

    public static boolean hasRole(AuthorityEnum role, User user){
        return user.getAuthorities().stream().anyMatch(roles -> roles.getAuthority().equals(role.name()));
    }
}
