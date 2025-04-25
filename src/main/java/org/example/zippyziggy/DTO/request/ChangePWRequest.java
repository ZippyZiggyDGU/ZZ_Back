package org.example.zippyziggy.DTO.request;

import lombok.Getter;

@Getter
public class ChangePWRequest {

    private String currentPW;

    private String targetPW;

}
