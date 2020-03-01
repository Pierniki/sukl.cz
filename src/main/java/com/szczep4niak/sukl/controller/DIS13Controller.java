package com.szczep4niak.sukl.controller;

import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.excel.ExcelParser;
import com.szczep4niak.sukl.service.DIS13Service;
import com.szczep4niak.sukl.utility.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Controller
public class DIS13Controller {
    @Autowired
    private ExcelParser excelParser;
    @Autowired
    private DIS13Service dis13Service;

    @GetMapping("/dis13")
    public String dis13(Model model) {
        model.addAttribute("hlaseni", dis13Service.getHlaseni());
        return "DIS13.html";
    }

    @PostMapping("/dis13")
    public String dis13(Model model, MultipartFile file) throws IOException {
        if (file != null) {
            dis13Service.setHlaseni(excelParser.parse(file));
            model.addAttribute("hlaseni", dis13Service.getHlaseni());
        }
        return "DIS13.html";
    }

    @PostMapping("/dis13/send")
    public String sendForm(Model model) {
        model.addAttribute("hlaseni", dis13Service.getHlaseni());
        model.addAttribute("resp", dis13Service.sendForm());
        return "DIS13.html";
    }

    @GetMapping("/dis13/send")
    public String redirectSendForm(Model model) {
        if (dis13Service.getHlaseni() != null) {
            model.addAttribute(dis13Service.getHlaseni());
        }
        return "DIS13.html";
    }

}
