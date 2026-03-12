package org.example;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        int port = 0;
        String origin = null;
        boolean clearCache = false;

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("--port")) {
                port = Integer.parseInt(args[i + 1]);
            }

            if (args[i].equals("--origin")) {
                origin = args[i + 1];
            }

            if (args[i].equals("--clear-cache")) {
                clearCache = true;
            }
        }

        if (clearCache) {
            CacheManager.clearCache();
            System.out.println("Cache cleared!");
            return;
        }

        if (port == 0 || origin == null) {
            System.out.println("Usage: caching-proxy --port <number> --origin <url>");
            return;
        }

        ProxyServer.start(port, origin);
    }
}