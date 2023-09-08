package org.muffin.opencsvreadwrite.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleFile {
    @CsvBindByName(column = "TEXT")
    String text;
    @CsvBindByName(column = "SHORT TEXT")
    String shortText;
    @CsvBindByName(column = "LONG TEXT")
    String longText;
}
