package com.szczep4niak.sukl.service;

import com.szczep4niak.sukl.domain.Hlaseni;

public interface DIS13Service {
    String sendForm();
    void setHlaseni(Hlaseni hlaseni);
    Hlaseni getHlaseni();
}
