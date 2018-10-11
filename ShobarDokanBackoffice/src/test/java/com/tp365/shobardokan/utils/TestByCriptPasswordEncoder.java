package com.tp365.shobardokan.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Masum on 10/11/2018.
 */
public class TestByCriptPasswordEncoder {

    @Test
    public void TestPasswordEncoder() {
        String password = "aaaaa@";
        System.out.println(new BCryptPasswordEncoder().encode(password));
    }
}
