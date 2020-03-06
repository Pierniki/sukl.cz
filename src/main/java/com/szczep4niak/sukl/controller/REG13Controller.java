package com.szczep4niak.sukl.controller;

import com.szczep4niak.sukl.service.ReportsService;
import com.szczep4niak.sukl.utility.ExcelParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class REG13Controller {
    @Autowired
    private ExcelParser excelParser;
    @Autowired
    private ReportsService reportsService;

    private String apiUrl = "https://testapi.sukl.cz/reg13/v2/hlaseni";
    private String certificatePath = "certificate/MAHSUKL150022354G";

    @GetMapping("/reg13")
    public String reg13(Model model) {
        reportsService.setHlaseni(null);
        return "REG13.html";
    }

    @PostMapping("/reg13")
    public String reg13(Model model, MultipartFile file) throws IOException {
        model.addAttribute("resp", excelParser.mapToReg(file));
        model.addAttribute("hlaseni", reportsService.getHlaseni());
        return "REG13.html";
    }

    @PostMapping("/reg13/send")
    public String sendForm(Model model) {
        model.addAttribute("hlaseni", reportsService.getHlaseni());
        model.addAttribute("resp", reportsService.sendForm(apiUrl, certificatePath));
        return "REG13.html";
    }

    @GetMapping("/reg13/send")
    public String redirectSendForm(Model model) {
        return reg13(model);
    }
}
