package org.example;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

    private static final Map<String, String> cache = new HashMap<>();

    public static boolean contains(String key) {
        return cache.containsKey(key);
    }

    public static String get(String key) {
        return cache.get(key);
    }

    public static void put(String key, String value) {
        cache.put(key, value);
    }

    public static void clearCache() {
        cache.clear();
    }
}
