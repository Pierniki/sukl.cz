package com.szczep4niak.sukl.controller;

import com.szczep4niak.sukl.excel.ExcelParser;
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
public class HlaseniController {
    @Autowired
    private
    ExcelParser excelParser;

    @GetMapping("/dis13")
    public String dis13(Model model) {
        return "DIS13.html";
    }

    @PostMapping("/dis13")
    public String dis13(Model model, MultipartFile excel_sheet) throws IOException {
        model.addAttribute("hlaseni", excelParser.parse(FileManager.convert(excel_sheet)));
        return "DIS13.html";
    }

}
