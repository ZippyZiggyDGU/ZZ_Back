package org.example.zippyziggy.DTO.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MypageResponse {

    private String userName;

    private int gender;

    private LocalDate birth;

    private int age;

}
