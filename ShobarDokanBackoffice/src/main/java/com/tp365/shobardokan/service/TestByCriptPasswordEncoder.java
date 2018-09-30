package com.datasoft.DemoApplication.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestByCriptPasswordEncoder {

    public static void main(String[] args) {
        String password = "12345";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode(password));
    }
}