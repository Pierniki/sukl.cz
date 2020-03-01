package com.szczep4niak.sukl;

import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.domain.Reglp;
import com.szczep4niak.sukl.excel.ExcelParser;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;

@SpringBootApplication
public class SuklApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SuklApplication.class, args);
	}
}
