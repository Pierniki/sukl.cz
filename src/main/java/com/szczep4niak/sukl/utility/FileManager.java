package com.szczep4niak.sukl.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FileManager {
    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File("tmp.xlsx");
        FileOutputStream fos = new FileOutputStream("xlsx/" + convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static List<String> getCertificates() {
        Stream<String> cerfiticates = Stream.of(Objects.requireNonNull(Paths.get("./certificate/")
                .toFile().list((pFile, pString) -> pString.endsWith(".pfx"))));
        return cerfiticates.collect(Collectors.toList());
    }
}
