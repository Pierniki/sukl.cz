package com.szczep4niak.sukl.config;

import com.szczep4niak.sukl.utility.KeyStoreManager;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class HttpClientConfig {
    @Bean
    public HttpClient httpClient() throws Exception {
        char[] password = new String(Files.readAllBytes(Paths.get("certificate/pas.txt"))).toCharArray();

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(KeyStoreManager.keyStore("certificate/DISSUKL.jks", password), password)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        return HttpClients.custom().setSSLContext(sslContext).build();
    }
}
