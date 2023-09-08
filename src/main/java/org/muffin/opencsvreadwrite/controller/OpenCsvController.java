package org.muffin.opencsvreadwrite.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import lombok.extern.slf4j.Slf4j;
import org.muffin.opencsvreadwrite.model.SimpleFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/open-csv")
@Slf4j
public class OpenCsvController {

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<SimpleFile>> writeFileToDisk(@RequestPart("file") MultipartFile multipartFile) throws IOException {

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(multipartFile.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.UTF_8));
        CsvToBean<SimpleFile> csvToBean = new CsvToBeanBuilder<SimpleFile>(br)
                .withType(SimpleFile.class)
                .withIgnoreEmptyLine(true)
                .withSeparator(';')
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .withOrderedResults(true)
                .withThrowExceptions(false)
                .withIgnoreQuotations(true) // -> ignore double quotes and escape with backslash
                .withIgnoreEmptyLine(true)
                .build();

        try {
            return ResponseEntity.ok(csvToBean.stream().collect(Collectors.toList()));
        } catch (Exception exception) {
            log.error("Something went wrong: ", exception);
            throw exception;
        }
    }
}
