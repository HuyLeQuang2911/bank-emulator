package com.example.benbankemulator.service;

import com.example.benbankemulator.dto.common.HeaderInfoGW;
import com.example.benbankemulator.dto.common.HeaderNAPAS;
import com.example.benbankemulator.dto.request.LookupBen.LookupBenReqInfoGW;
import com.example.benbankemulator.dto.request.payment.PaymentRequestNAPAS;
import com.example.benbankemulator.dto.response.LookupBen.LookupBenResInfoGW;
import com.example.benbankemulator.dto.response.LookupBen.LookupBenResNAPAS;
import com.example.benbankemulator.dto.response.payment.PaymentResponseGW;
import com.example.benbankemulator.dto.response.payment.PaymentResponseNAPAS;
import com.example.benbankemulator.exception.ValidationHelper;
import org.springframework.stereotype.Service;

@Service
public class QRService {

    //Luồng của lookup ben gửi sang ben bank
    public LookupBenResInfoGW genLookupBenResInfoGW(LookupBenReqInfoGW request){
        HeaderInfoGW header = request.getHeader();
        LookupBenResInfoGW response = new LookupBenResInfoGW();
        LookupBenResInfoGW.Data data = new LookupBenResInfoGW.Data();
        LookupBenResInfoGW.Participant participant = new LookupBenResInfoGW.Participant();
        LookupBenResInfoGW.Payment payment = new LookupBenResInfoGW.Payment();
        LookupBenResInfoGW.Recipient recipient = new LookupBenResInfoGW.Recipient();
        LookupBenResInfoGW.Address address = new LookupBenResInfoGW.Address();
        LookupBenResInfoGW.Order order = new LookupBenResInfoGW.Order();


        header.setReqResGb("RES");

        if (!ValidationHelper.isValid(request)) {
            header.setErrCode("004");
            header.setErrDesc("Wrong message format: " + ValidationHelper.fieldNames.get());
        }
        else {
            //data
            data.setResponseCode("00");
            data.setResponseDesc("Success");
            data.setFundingReference("12121213123123123");

            String amount = request.getData().getAmount();
            if(!amount.isEmpty()){
                data.setAmount(amount);
            }

            String currency = request.getData().getCurrency();
            if(!currency.isEmpty()){
                data.setCurrency(currency);
            }

            data.setRecipientAccount(request.getData().getRecipientAccount());

            //payment
            payment.setGenerationMethod(request.getData().getPayment().getGenerationMethod());

            String tag55Value = request.getData().getPayment().getIndicator();
            if(tag55Value != null){
                payment.setIndicator(tag55Value);
            }

            payment.setTrace("123456");

            payment.setExchangeRate("1");

            String tag56Value = request.getData().getPayment().getFeeFixed();
            if(tag56Value != null){
                payment.setFeeFixed(tag56Value);
            }

            String tag57Value = request.getData().getPayment().getFeePercentage();
            if(tag57Value != null){
                payment.setFeePercentage(tag57Value);
            }

            payment.setPayRefNo("2313123123df");

            //participant
            participant.setMerchantId(request.getData().getParticipant().getMerchantId());

            participant.setReceivingInstitutionId(request.getData().getParticipant().getReceivingInstitutionId());

            participant.setMerchantCategoryCode(request.getData().getParticipant().getMerchantCategoryCode());

            participant.setCardAcceptorId(request.getData().getParticipant().getMerchantId());

            participant.setCardAcceptorCountry("VN");

            participant.setCardAcceptorName("HuyQuangLe");

            participant.setCardAcceptorCity("HANOI");


            //address

            address.setLine1("Chuong My, Ha Noi");

            address.setCountry("Viet Nam");

            address.setPhone("0388416667");

            //recipient
            recipient.setFullName("Le Quang Huy");

            recipient.setDob("20011129");

            recipient.setAddress(address);

            //order
            String billNumber = request.getData().getOrder().getBillNumber();
            if (billNumber != null){
                order.setBillNumber(billNumber);
            }

            String mobileNumber = request.getData().getOrder().getMobileNumber();
            if (mobileNumber != null){
                order.setMobileNumber(mobileNumber);
            }

            String storeLable = request.getData().getOrder().getStoreLabel();
            if (storeLable != null){
                order.setStoreLabel(storeLable);
            }

            String loyaltyNumber = request.getData().getOrder().getLoyaltyNumber();
            if (loyaltyNumber != null){
                order.setLoyaltyNumber(loyaltyNumber);
            }

            String referenceLabel = request.getData().getOrder().getReferenceLabel();
            if (referenceLabel != null){
                order.setReferenceLabel(referenceLabel);
            }

            String customerLabel = request.getData().getOrder().getCustomerLabel();
            if (customerLabel != null){
                order.setCustomerLabel(customerLabel);
            }

            String terminalLabel = request.getData().getOrder().getTerminalLabel();
            if (terminalLabel != null){
                order.setTerminalLabel(terminalLabel);
            }

            String purposeOfTrans = request.getData().getOrder().getPurposeOfTrans();
            if (purposeOfTrans != null){
                order.setPurposeOfTrans(purposeOfTrans);
            }

            String additionCosumerData = request.getData().getOrder().getAdditionConsumerData();
            if (additionCosumerData != null){
                order.setAdditionConsumerData(additionCosumerData);
            }



            data.setPayment(payment);
            data.setParticipant(participant);
            data.setRecipient(recipient);
            data.setOrder(order);
            response.setData(data);

        }

        response.setHeader(header);
        return response;
    }


    //luồng của issuer payment gửi sang Napas
    public PaymentResponseNAPAS genPaymentResNAPAS(PaymentRequestNAPAS requestNAPAS){

        PaymentResponseNAPAS paymentResponseNAPAS = new PaymentResponseNAPAS();
        HeaderNAPAS header = requestNAPAS.getHeader();
        PaymentResponseNAPAS.Payload payload = new PaymentResponseNAPAS.Payload();
        PaymentResponseNAPAS.Payment payment = new PaymentResponseNAPAS.Payment();
        PaymentResponseNAPAS.OrderInfo orderInfo = new PaymentResponseNAPAS.OrderInfo();
//        PaymentResponseNAPAS.AdditionalInfo additionalInfo = new PaymentResponseNAPAS.AdditionalInfo();
//        PaymentResponseNAPAS.Instruction instruction = new PaymentResponseNAPAS.Instruction();
        PaymentResponseNAPAS.Sender sender = new PaymentResponseNAPAS.Sender();
        PaymentResponseNAPAS.Participant participant = new PaymentResponseNAPAS.Participant();
        PaymentResponseNAPAS.Recipient recipient = new PaymentResponseNAPAS.Recipient();
        PaymentResponseNAPAS.Address senderAddress = new PaymentResponseNAPAS.Address();
        PaymentResponseNAPAS.Address recipientAddress = new PaymentResponseNAPAS.Address();

        //header
        header.setOperation("RES");


        //payment
        payment.setReference(requestNAPAS.getPayload().getPayment().getPayment_reference());
        payment.setAuthorization_code("author001");
        payment.setFunding_reference(requestNAPAS.getPayload().getPayment().getFunding_reference());
        payment.setType(requestNAPAS.getPayload().getPayment().getType());
        payment.setGeneration_method(requestNAPAS.getPayload().getPayment().getGeneration_method());
        payment.setChannel(requestNAPAS.getPayload().getPayment().getChannel());
        payment.setDevice_id(requestNAPAS.getPayload().getPayment().getDevice_id());
        payment.setLocation(requestNAPAS.getPayload().getPayment().getLocation());
        payment.setTransaction_local_date_time(requestNAPAS.getPayload().getPayment().getTransaction_local_date_time());
        payment.setInterbank_amount(requestNAPAS.getPayload().getPayment().getInterbank_amount());
        payment.setInterbank_currency(requestNAPAS.getPayload().getPayment().getInterbank_currency());
        payment.setExchange_rate(requestNAPAS.getPayload().getPayment().getExchange_rate());
        payment.setIndicator(requestNAPAS.getPayload().getPayment().getIndicator());
        payment.setFee_fixed(requestNAPAS.getPayload().getPayment().getFee_fixed());
        payment.setFee_percentage(requestNAPAS.getPayload().getPayment().getFee_percentage());
        payment.setPayment_reference(requestNAPAS.getPayload().getPayment().getPayment_reference());
        payment.setEnd_to_end_reference(requestNAPAS.getPayload().getPayment().getEnd_to_end_reference());
        payment.setTrace(requestNAPAS.getPayload().getPayment().getTrace());

        //senderAddress
        senderAddress.setLine1(requestNAPAS.getPayload().getSender().getAddress().getLine1());
        senderAddress.setLine2(requestNAPAS.getPayload().getSender().getAddress().getLine2());
        senderAddress.setCity(requestNAPAS.getPayload().getSender().getAddress().getCity());
        senderAddress.setCountry_subdivision(requestNAPAS.getPayload().getSender().getAddress().getCountry_subdivision());
        senderAddress.setPostal_code(requestNAPAS.getPayload().getSender().getAddress().getPostal_code());
        senderAddress.setCountry(requestNAPAS.getPayload().getSender().getAddress().getCountry());
        senderAddress.setPhone(requestNAPAS.getPayload().getSender().getAddress().getPhone());
        senderAddress.setEmail(requestNAPAS.getPayload().getSender().getAddress().getEmail());

        //sender
        sender.setFirst_name(requestNAPAS.getPayload().getSender().getFirst_name());
        sender.setMiddle_name(requestNAPAS.getPayload().getSender().getMiddle_name());
        sender.setFull_name(requestNAPAS.getPayload().getSender().getFull_name());
        sender.setDate_of_birth(requestNAPAS.getPayload().getSender().getDate_of_birth());
        sender.setAddress(senderAddress);

        //participant
        participant.setOriginating_institution_id(requestNAPAS.getPayload().getParticipant().getOriginating_institution_id());
        participant.setReceiving_institution_id(requestNAPAS.getPayload().getParticipant().getReceiving_institution_id());
        participant.setMerchant_id(requestNAPAS.getPayload().getParticipant().getMerchant_id());
        participant.setMerchant_category_code(requestNAPAS.getPayload().getParticipant().getMerchant_category_code());
        participant.setCard_acceptor_id(requestNAPAS.getPayload().getParticipant().getCard_acceptor_id());
        participant.setCard_acceptor_name(requestNAPAS.getPayload().getParticipant().getCard_acceptor_name());
        participant.setCard_acceptor_city(requestNAPAS.getPayload().getParticipant().getCard_acceptor_city());
        participant.setCard_acceptor_country(requestNAPAS.getPayload().getParticipant().getCard_acceptor_country());
        participant.setCard_postal_code(requestNAPAS.getPayload().getParticipant().getCard_postal_code());
        participant.setCard_language_preference(requestNAPAS.getPayload().getParticipant().getCard_language_preference());
        participant.setCard_name_alternate_language(requestNAPAS.getPayload().getParticipant().getCard_name_alternate_language());
        participant.setCity_alternate_language(requestNAPAS.getPayload().getParticipant().getCard_city_alternate_language());
        participant.setCard_payment_system_specific(requestNAPAS.getPayload().getParticipant().getCard_payment_system_specific());

        //recipientAddress
        recipientAddress.setLine1(requestNAPAS.getPayload().getRecipient().getAddress().getLine1());
        recipientAddress.setLine2(requestNAPAS.getPayload().getRecipient().getAddress().getLine2());
        recipientAddress.setCity(requestNAPAS.getPayload().getRecipient().getAddress().getCity());
        recipientAddress.setCountry_subdivision(requestNAPAS.getPayload().getRecipient().getAddress().getCountry_subdivision());
        recipientAddress.setPostal_code(requestNAPAS.getPayload().getRecipient().getAddress().getPostal_code());
        recipientAddress.setCountry(requestNAPAS.getPayload().getRecipient().getAddress().getCountry());
        recipientAddress.setPhone(requestNAPAS.getPayload().getRecipient().getAddress().getPhone());
        recipientAddress.setEmail(requestNAPAS.getPayload().getRecipient().getAddress().getEmail());

        //recipient
        recipient.setFirst_name(requestNAPAS.getPayload().getRecipient().getFirst_name());
        recipient.setMiddle_name(requestNAPAS.getPayload().getRecipient().getMiddle_name());
        recipient.setFull_name(requestNAPAS.getPayload().getRecipient().getFull_name());
        recipient.setDate_of_birth(requestNAPAS.getPayload().getRecipient().getDate_of_birth());
        recipient.setAddress(recipientAddress);

        //payload
        payload.setPayment(payment);
        payload.setAmount(requestNAPAS.getPayload().getAmount());
        payload.setCurrency(requestNAPAS.getPayload().getCurrency());
        payload.setSender_account(requestNAPAS.getPayload().getSender_account());
        payload.setSender(sender);
        payload.setParticipant(participant);
        payload.setRecipient_account(requestNAPAS.getPayload().getRecipient_account());
        payload.setRecipient(recipient);


        //orderInfo
        orderInfo.setBill_number(requestNAPAS.getPayload().getOrder_info().getBill_number());
        orderInfo.setMobile_number(requestNAPAS.getPayload().getOrder_info().getMobile_number());
        orderInfo.setStore_label(requestNAPAS.getPayload().getOrder_info().getStore_label());
        orderInfo.setLoyalty_number(requestNAPAS.getPayload().getOrder_info().getLoyalty_number());
        orderInfo.setCustomer_label(requestNAPAS.getPayload().getOrder_info().getCustomer_label());
        orderInfo.setTerminal_label(requestNAPAS.getPayload().getOrder_info().getTerminal_label());
        orderInfo.setTransaction_purpose(requestNAPAS.getPayload().getOrder_info().getTransaction_purpose());
        orderInfo.setAdditional_data_request(requestNAPAS.getPayload().getOrder_info().getAdditional_data_request());

        //Instruction

        paymentResponseNAPAS.setHeader(header);
        paymentResponseNAPAS.setPayload(payload);
        paymentResponseNAPAS.setAdditional_message(requestNAPAS.getPayload().getAdditional_message());
        paymentResponseNAPAS.setOrder_info(orderInfo);



        return paymentResponseNAPAS;

    }


    //Luồng issuer payment gửi trả bản tin về core
    public void coreReceivePaymentResGWFromGW(PaymentResponseGW paymentResponseGW){

    }

    //luồng lookup ben gửi trả bản tin về napas
    public void napasReceiveLookupbenResNAPASFromGW(LookupBenResNAPAS lookupBenResNAPAS){

    }
}
