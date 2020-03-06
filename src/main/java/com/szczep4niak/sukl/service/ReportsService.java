package com.szczep4niak.sukl.service;

import com.szczep4niak.sukl.domain.Hlaseni;

public interface ReportsService {
    String sendForm(String apiUrl, String certificatePath);
    void setHlaseni(Hlaseni hlaseni);
    Hlaseni getHlaseni();
}
