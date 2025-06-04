package org.example.zippyziggy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ModelLogResponse {

    private LocalDate testDate;

    private double result;

}
