package com.szczep4niak.sukl.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import com.google.common.collect.ImmutableMap;
import com.szczep4niak.sukl.domain.Hlaseni;
import com.szczep4niak.sukl.domain.Reglp;
import com.szczep4niak.sukl.domain.Sw;
import com.szczep4niak.sukl.service.ReportsService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelParser {

    @Autowired
    private ReportsService reportsService;


    private DataFormatter formatter = new DataFormatter();
    private XSSFWorkbook workbook;

    private Map<String, Boolean> primaDodavkaLPMap = ImmutableMap.<String, Boolean> builder()
            .put("ne", false)
            .put("tak", true)
            .build();

    private Map<String, Integer> typHlaseniDisMap = ImmutableMap.<String, Integer>builder()
            .put("Dodávka", 1)
            .put("Distribuce", 2)
            .build();

    private Map<String, Integer> typHlaseniRegMap = ImmutableMap.<String, Integer>builder()
            .put("Hlášení dodávek LP lékárnám/osobám oprávněným k výdeji v ČR", 1)
            .put("Hlášení dodávek LP distributorům v ČR", 2)
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


    private void readFile(MultipartFile excel_file) throws IOException {
        FileInputStream fs = new FileInputStream("xlsx/" + FileManager.convert(excel_file));
        workbook = new XSSFWorkbook(Objects.requireNonNull(fs));

    }

    public String mapToDis(MultipartFile file) {
        try {
            readFile(file);
            Hlaseni hlaseni = mapToHlaseni(workbook.getSheetAt(0));
            hlaseni.setReglp(mapToDisReglp(workbook.getSheetAt(1)));
            hlaseni.setNereglp(new ArrayList<>());
            hlaseni.setSw(mapToSw(workbook.getSheetAt(3)));
            reportsService.setHlaseni(hlaseni);
        } catch (Exception e) {
            reportsService.setHlaseni(null);
            return "Failed to parse file";
        }
        return null;
    }

    public String mapToReg(MultipartFile file) {
        try {
            readFile(file);
            Hlaseni hlaseni = mapToHlaseni(workbook.getSheetAt(0));
            hlaseni.setReglp(mapToRegReglp(workbook.getSheetAt(1)));
            hlaseni.setSw(mapToSw(workbook.getSheetAt(2)));
            reportsService.setHlaseni(hlaseni);
        } catch (Exception e) {
            reportsService.setHlaseni(null);
            return "Failed to parse file";
        }
        return null;
    }

    private Hlaseni mapToHlaseni(XSSFSheet sheet) {
        Row row = sheet.getRow(1);
        return new Hlaseni(
                UUID.randomUUID().toString(),
                formatter.formatCellValue(row.getCell(0)),
                formatter.formatCellValue(row.getCell(1))
        );
    }

    private List<Reglp> mapToDisReglp(XSSFSheet sheet) {
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
            reg.setTypHlaseni(typHlaseniDisMap.get(formatter.formatCellValue(row.getCell(6))));
            reg.setTypPohybu(typPohybuMap.get(formatter.formatCellValue(row.getCell(7))));
            reg.setTypOdberatele(typOdberateleMap.get(formatter.formatCellValue(row.getCell(8))));
            reg.setPrimaDodavkaLP(primaDodavkaLPMap.get(formatter.formatCellValue(row.getCell(9))));
            reg.setPolozkaID(UUID.randomUUID().toString());
            reglp.add(reg);
        }
        return reglp;
    }

    private List<Reglp> mapToRegReglp(XSSFSheet sheet) {
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
            reg.setMnozstvi((int) row.getCell(4).getNumericCellValue());
            reg.setCena(row.getCell(5).getNumericCellValue());
            reg.setTypHlaseni(typHlaseniRegMap.get(formatter.formatCellValue(row.getCell(6))));
            reg.setTypPohybu(typPohybuMap.get(formatter.formatCellValue(row.getCell(7))));
            reg.setPolozkaID(UUID.randomUUID().toString());
            reglp.add(reg);
        }
        return reglp;
    }

    private Sw mapToSw(XSSFSheet sheet) {
        Row row = sheet.getRow(1);
        return new Sw(
                formatter.formatCellValue(row.getCell(0)),
                formatter.formatCellValue(row.getCell(1)),
                formatter.formatCellValue(row.getCell(2))
        );
    }
}