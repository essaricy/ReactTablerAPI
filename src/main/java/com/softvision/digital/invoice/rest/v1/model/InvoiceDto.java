package com.softvision.digital.invoice.rest.v1.model;

import lombok.Data;

@Data
public class InvoiceDto {

    private String id;

    private String subject;

    private String client;

    private String vat;

    private String created;

    private String status;

    private int price;

}
