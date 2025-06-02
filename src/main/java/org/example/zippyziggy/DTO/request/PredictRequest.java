package org.example.zippyziggy.DTO.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredictRequest {

    private int age;

    @JsonProperty("ASBP")
    private double asbp;

    private int sex;

    @JsonProperty("exam1_age")
    private int exam1_age;

    private int smoke;

    @JsonProperty("PRSice2")
    private double prs;

}
