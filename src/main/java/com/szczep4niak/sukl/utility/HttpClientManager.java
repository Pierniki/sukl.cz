package com.szczep4niak.sukl.utility;

import com.szczep4niak.sukl.utility.KeyStoreManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class HttpClientManager {

    private char[] password;

    public HttpClientManager() throws IOException {
        password = new String(Files.readAllBytes(Paths.get("certificate/pas.txt"))).toCharArray();
    }

    public HttpClient HttpClient(String certificatePath) throws Exception {
        return HttpClients.custom().setSSLContext(SSLContextBuilder.create()
                .loadKeyMaterial(KeyStoreManager.keyStore(certificatePath, password), password)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build())
                .build();
    }
}
