package com.example.benbankemulator.dto.common;

import lombok.Data;


import javax.validation.constraints.*;

@Data
public class HeaderInfoGW {

    @NotBlank
    private String bkCd;

    @NotBlank
    private String brCd;

    @NotBlank
    private String trnDt;

    @NotBlank
    private String direction;

    @NotBlank
    private String reqResGb;

    @NotBlank
    private String refNo;

    private String errCode;

    private String errDesc;


}
