package com.szczep4niak.sukl.controller;

import com.szczep4niak.sukl.utility.ExcelParser;
import com.szczep4niak.sukl.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class DIS13Controller {
    @Autowired
    private ExcelParser excelParser;

    @Autowired
    @Qualifier("dis13Service")
    private ReportsService reportsService;

    @GetMapping("/dis13")
    public String dis13(Model model) {
        return "DIS13.html";
    }

    @PostMapping("/dis13")
    public String dis13(Model model, MultipartFile file) {
        model.addAttribute("resp", excelParser.mapToDis(file));
        model.addAttribute("hlaseni", reportsService.getHlaseni());
        return "DIS13.html";
    }

    @PostMapping("/dis13/send")
    public String sendForm(Model model) {
        model.addAttribute("hlaseni", reportsService.getHlaseni());
        model.addAttribute("resp", reportsService.sendForm());
        return "DIS13.html";
    }

    @GetMapping("/dis13/send")
    public String redirectSendForm(Model model) {
        return "DIS13.html";
    }

}
