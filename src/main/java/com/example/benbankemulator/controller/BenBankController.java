package com.example.benbankemulator.controller;

import com.example.benbankemulator.dto.request.LookupBen.LookupBenReqInfoGW;
import com.example.benbankemulator.dto.response.LookupBen.LookupBenResInfoGW;
import com.example.benbankemulator.dto.response.LookupBen.LookupBenResNAPAS;
import com.example.benbankemulator.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/benbank/qr/v1")
public class BenBankController {

    @Autowired
    private QRService qrService;

    //gửi bản tin sang ben bank
    @PostMapping(value = "/lookup/sentBenBank")
    public LookupBenResInfoGW lookup(@RequestBody LookupBenReqInfoGW lookupBenReqInfoGW){
        return qrService.genLookupBenResInfoGW(lookupBenReqInfoGW);
    }

    //gửi trả bản tin về napas
    @PostMapping(value = "/lookup/sentNapas")
    public void ReceiveLookupbenResNAPAS(@RequestBody LookupBenResNAPAS lookupBenResNAPAS){
        qrService.napasReceiveLookupbenResNAPASFromGW(lookupBenResNAPAS);
    }




}
