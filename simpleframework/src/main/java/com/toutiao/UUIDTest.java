package com.toutiao;

import java.util.UUID;

public class UUIDTest {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
    }
}