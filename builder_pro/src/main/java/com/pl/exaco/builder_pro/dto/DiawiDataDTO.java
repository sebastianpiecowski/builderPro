package com.pl.exaco.builder_pro.dto;

import com.pl.exaco.builder_pro.utils.datetimeParser;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class DiawiDataDTO {
    private String url;
    private Long expirationTimestamp;
    private String expirationDate;

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate= datetimeParser.parseToString(expirationDate);
    }
}
