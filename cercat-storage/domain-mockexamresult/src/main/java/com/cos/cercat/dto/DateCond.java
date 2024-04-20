package com.cos.cercat.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record DateCond(
        Integer year,
        Integer month,
        Integer weekOfMonth,
        @DateTimeFormat(pattern="yyyy-MM-dd") Date date
) {
}
