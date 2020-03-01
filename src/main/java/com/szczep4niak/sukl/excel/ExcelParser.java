package com.szczep4niak.sukl.excel;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.domain.Reglp;
import com.szczep4niak.sukl.domain.Sw;
import com.szczep4niak.sukl.utility.FileManager;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;

@Service
public class ExcelParser {
    private Map<String, Boolean> primaDodavkaLPMap = ImmutableMap.<String, Boolean> builder()
            .put("ne", false)
            .put("tak", true)
            .build();

    private Map<String, Integer> typHlaseniMap = ImmutableMap.<String, Integer> builder()
            .put("Dodávka", 1)
            .put("Distribuce", 2)
            .build();

    private Map<String, Integer> typPohybuMap = ImmutableMap.<String, Integer> builder()
            .put("Dodávka zboží", 1)
            .put("Vratka zboží", 2)
            .build();

    private Map<String, Integer> typOdberateleMap = ImmutableMap.<String, Integer> builder()
            .put("Lékař", 1)
            .put("Lékárna", 2)
            .put("Nukleární medicína", 3)
            .put("Hygienická stanice", 4)
            .put("Prodejce VLP", 5)
            .put("Osoba poskytující ZP", 6)
            .put("Transfuzní služba", 7)
            .put("Veterinární lékař", 8)
            .put("Reklamní vzorky", 9)
            .put("Výdej v zahraničí", 10)
            .put("Distributor ČR", 11)
            .put("Distributor v EU", 12)
            .put("Distributor mimo EU", 13)
            .build();


    public Hlaseni parse(MultipartFile excel_file) {
        try {
            FileInputStream file = new FileInputStream("xlsx/" + FileManager.convert(excel_file));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            DataFormatter formatter = new DataFormatter();

            Hlaseni hlaseni = mapToHlaseni(workbook.getSheetAt(0), formatter);
            hlaseni.setReglp(mapToReglp(workbook.getSheetAt(1), formatter));
            hlaseni.setNereglp(new ArrayList<>());
            hlaseni.setSw(mapToSw(workbook.getSheetAt(3), formatter));

            file.close();
            return hlaseni;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to read file.");
        }
        return null;
    }

    private Hlaseni mapToHlaseni(XSSFSheet sheet, DataFormatter formatter) {
        return new Hlaseni(
                UUID.randomUUID().toString(),
                formatter.formatCellValue(sheet.getRow(1).getCell(1)),
                formatter.formatCellValue(sheet.getRow(2).getCell(1))
        );
    }

    private List<Reglp> mapToReglp(XSSFSheet sheet, DataFormatter formatter) {
        List<Reglp> reglp = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Reglp reg = new Reglp();
            reg.setKodSUKL(formatter.formatCellValue(row.getCell(0)));
            reg.setNazev(formatter.formatCellValue(row.getCell(1)));
            reg.setDoplnek(formatter.formatCellValue(row.getCell(2)));
            reg.setSarze(formatter.formatCellValue(row.getCell(3)));
            reg.setMnozstvi((int)row.getCell(4).getNumericCellValue());
            reg.setCena(row.getCell(5).getNumericCellValue());
            reg.setTypHlaseni(typHlaseniMap.get(formatter.formatCellValue(row.getCell(6))));
            reg.setTypPohybu(typPohybuMap.get(formatter.formatCellValue(row.getCell(7))));
            reg.setTypOdberatele(typOdberateleMap.get(formatter.formatCellValue(row.getCell(8))));
            reg.setPrimaDodavkaLP(primaDodavkaLPMap.get(formatter.formatCellValue(row.getCell(9))));
            reg.setPolozkaID(UUID.randomUUID().toString());
            reglp.add(reg);
        }
        return reglp;
    }

    private Sw mapToSw(XSSFSheet sheet, DataFormatter formatter) {
        Row row = sheet.getRow(1);
        return new Sw(
                formatter.formatCellValue(row.getCell(0)),
                formatter.formatCellValue(row.getCell(1)),
                formatter.formatCellValue(row.getCell(2))
        );
    }

    private Map initMap(Object[][] values, DataFormatter formatter) {
        return Stream.of(values).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    }
}