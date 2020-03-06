package com.szczep4niak.sukl.utility;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Objects;

class KeyStoreManager {
    static KeyStore keyStore(String file, char[] password) throws Exception {
        java.security.KeyStore keyStore = java.security.KeyStore.getInstance("JKS");
        File key = ResourceUtils.getFile(Objects.requireNonNull(getCertificate(file, password)));
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }

    private static String getCertificate(String file, char[] password) {
        if (!new File(file + ".jks").exists()) {
            try {
                String command = " -importkeystore" +
                        " -srckeystore " + file + ".pfx" +
                        " -srcstoretype pkcs12" +
                        " -srcstorepass " + new String(password) +
                        " -destkeystore " + file + ".jks" +
                        " -deststoretype JKS" +
                        " -deststorepass " + new String(password);

                sun.security.tools.keytool.Main.main(command.trim().split("\\s+"));
                return file + ".jks";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return file + ".jks";
        }
    }
}
