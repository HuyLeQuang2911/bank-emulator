package com.example.benbankemulator.controller;

import com.example.benbankemulator.dto.request.payment.PaymentRequestNAPAS;
import com.example.benbankemulator.dto.response.payment.PaymentResponseGW;
import com.example.benbankemulator.dto.response.payment.PaymentResponseNAPAS;
import com.example.benbankemulator.service.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/issuerbank/qr/v1")
public class IssuerBankController {

    @Autowired
    private QRService qrService;

    //gửi trả bản tin về core
    @PostMapping(value = "/payment/sentCore")
    public void receivePaymentResGW(@RequestBody PaymentResponseGW paymentResponseGW){
        qrService.coreReceivePaymentResGWFromGW(paymentResponseGW);
    }

    //gửi bản tin sang napas
    @PostMapping(value = "/payment/sentNapas")
    public PaymentResponseNAPAS payment(@RequestBody PaymentRequestNAPAS paymentRequestNAPAS){
        return qrService.genPaymentResNAPAS(paymentRequestNAPAS);
    }
}
