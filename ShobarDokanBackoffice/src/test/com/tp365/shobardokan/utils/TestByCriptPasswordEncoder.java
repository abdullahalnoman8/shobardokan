package com.tp365.shobardokan.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by Masum on 10/11/2018.
 */

@Slf4j
@WebAppConfiguration
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TestByCriptPasswordEncoder {

    @Test
    public void TestPasswordEncoder() {
        String password = "aaaaa@";
        log.info("Generated Password: "+ new BCryptPasswordEncoder().encode(password));
    }
}
