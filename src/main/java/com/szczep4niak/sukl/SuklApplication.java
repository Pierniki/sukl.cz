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
		ApplicationContext ac = SpringApplication.run(SuklApplication.class, args);

		RestTemplate rt = (RestTemplate) ac.getBean("rest");

		rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient()));
		ExcelParser ep = new ExcelParser();
		Hlaseni hh = ep.parse("xlsx/HlaseniDIS13_00150022329_202001 - test report DIS13.xlsx");
		hh.setObdobi("202002");

		String resp = rt.postForObject("https://testapi.sukl.cz/dis13/v5/hlaseni", hh, String.class);
		System.out.println(resp);
	}

	private static HttpClient httpClient() throws Exception {
		char[] password = new String(Files.readAllBytes(Paths.get("certificate/pas.txt"))).toCharArray();

		SSLContext sslContext = SSLContextBuilder.create()
				.loadKeyMaterial(keyStore("certificate/DISSUKL.jks", password), password)
				.loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

		return HttpClients.custom().setSSLContext(sslContext).build();
	}

	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}

	private static KeyStore keyStore(String file, char[] password) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		File key = ResourceUtils.getFile(file);
		try (InputStream in = new FileInputStream(key)) {
			keyStore.load(in, password);
		}
		return keyStore;
	}
}
