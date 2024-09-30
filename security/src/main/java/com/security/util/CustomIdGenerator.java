package com.security.util;


import java.util.UUID;

public class CustomIdGenerator {

    public static String generateId(String entityName) {
        String prefix = entityName.substring(0, 3).toLowerCase();
        String uuid = UUID.randomUUID().toString();
        return prefix + "-" + uuid;
    }
}
