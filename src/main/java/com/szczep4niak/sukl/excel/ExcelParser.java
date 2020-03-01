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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

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


    public Hlaseni parse(File excel_file) {
        try {
            FileInputStream file = new FileInputStream(excel_file);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            Hlaseni hlaseni = mapToHlaseni(workbook.getSheetAt(0));
            hlaseni.setReglp(mapToReglp(workbook.getSheetAt(1)));
            hlaseni.setNereglp(new ArrayList<>());
            hlaseni.setSw(mapToSw(workbook.getSheetAt(3)));

            file.close();
            return hlaseni;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Hlaseni mapToHlaseni(XSSFSheet sheet) {
        return new Hlaseni(
                UUID.randomUUID().toString(),
                sheet.getRow(1).getCell(1).getStringCellValue(),
                sheet.getRow(2).getCell(1).getStringCellValue()
        );
    }

    private List<Reglp> mapToReglp(XSSFSheet sheet) {
        List<Reglp> reglp = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Reglp reg = new Reglp();
            reg.setKodSUKL(row.getCell(0).getStringCellValue());
            reg.setNazev(row.getCell(1).getStringCellValue());
            reg.setDoplnek(row.getCell(2).getStringCellValue());
            reg.setSarze(row.getCell(3).getStringCellValue());
            reg.setMnozstvi((int) row.getCell(4).getNumericCellValue());
            reg.setCena(row.getCell(5).getNumericCellValue());
            reg.setTypHlaseni(typHlaseniMap.get(row.getCell(6).getStringCellValue()));
            reg.setTypPohybu(typPohybuMap.get(row.getCell(7).getStringCellValue()));
            reg.setTypOdberatele(typOdberateleMap.get(row.getCell(8).getStringCellValue()));
            reg.setPrimaDodavkaLP(primaDodavkaLPMap.get(row.getCell(9).getStringCellValue()));
            reg.setPolozkaID(UUID.randomUUID().toString());
            reglp.add(reg);
        }
        return reglp;
    }

    private Sw mapToSw(XSSFSheet sheet) {
        Row row = sheet.getRow(1);
        return new Sw(
                row.getCell(0).getStringCellValue(),
                row.getCell(1).getStringCellValue(),
                row.getCell(2).getStringCellValue()
        );
    }

    private Map initMap(Object[][] values) {
        return Stream.of(values).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
    }
}