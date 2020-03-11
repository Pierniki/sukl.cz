package com.szczep4niak.sukl.service;

import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.utility.HttpClientManager;
import com.szczep4niak.sukl.utility.ResponseParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service("dis13Service")
public class ReportsServiceImp implements ReportsService {

    private RestTemplate rt;
    private HttpClientManager hcm;
    private Hlaseni hlaseni;

    @Autowired
    public ReportsServiceImp(HttpClientManager hcm, RestTemplate rt) {
        this.hcm = hcm;
        this.rt = rt;
    }

    @Override
    public String sendForm(String apiUrl, String certificate) {
        String response;
        try {
            String certificateName = certificate.split("\\.")[0];
            System.out.println(certificateName);
            this.rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory(hcm.HttpClient(certificateName)));
            if (hlaseni != null) {
                try {
                    response = rt.postForObject(apiUrl, hlaseni, String.class);
                } catch (RestClientResponseException e) {
                    return e.getRawStatusCode() + " # " + ResponseParser.getPopisChyby(e.getResponseBodyAsString());
                }
            } else {
                response = "Select an excel file to parse.";
            }
        } catch (Exception e) {
            response = "Failed to load a certificate";
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
