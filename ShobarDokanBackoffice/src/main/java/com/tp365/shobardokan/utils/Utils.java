package com.tp365.shobardokan.utils;

import com.tp365.shobardokan.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.util.*;

public class Utils {

    public static void randomAlphabetic(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        System.out.println(generatedString);
    }

    public static Date convertTimeStampToDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return new Date(timestamp.getTime());
    }

    public static Timestamp convertDateToTimeStamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }
	
	public static Set<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		roles.stream().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRole().name())));
		return authorities;
	}
}