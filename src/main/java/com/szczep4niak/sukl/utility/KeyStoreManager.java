package com.szczep4niak.sukl.utility;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class KeyStoreManager {
    public static KeyStore keyStore(String file, char[] password) throws Exception {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance("JKS");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }
}
