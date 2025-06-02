package org.example.zippyziggy.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PredictResponse {

    private int label;

    private List<Double> probabilities;

}
