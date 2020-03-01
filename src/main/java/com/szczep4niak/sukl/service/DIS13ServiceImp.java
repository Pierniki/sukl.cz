package com.szczep4niak.sukl.service;

import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.utility.ResponseParser;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
public class DIS13ServiceImp implements DIS13Service {

    private RestTemplate rt;
    private Hlaseni hlaseni;

    @Autowired
    public DIS13ServiceImp(HttpClient httpClient, RestTemplate rt) {
        this.rt = rt;
        this.rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Override
    public String sendForm() {
        String response;
        if (hlaseni != null) {
            try {
                response = rt.postForObject("https://testapi.sukl.cz/dis13/v5/hlaseni", hlaseni, String.class);
            } catch (RestClientResponseException e) {
                return e.getRawStatusCode() + " # " + ResponseParser.getPopisChyby(e.getResponseBodyAsString());
            }
        }
        else {
            response = "Select an excel file to parse.";
        }
        return response;
    }

    @Override
    public void setHlaseni(Hlaseni hlaseni) {
        this.hlaseni = hlaseni;
    }

    @Override
    public Hlaseni getHlaseni() {
        return this.hlaseni;
    }
}
