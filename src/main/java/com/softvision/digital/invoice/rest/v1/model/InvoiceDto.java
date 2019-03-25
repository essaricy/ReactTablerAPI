package com.softvision.digital.invoice.rest.v1.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InvoiceDto {

    @NotBlank(message="Id is required")
    private String id;

    @NotBlank(message="subject is required")
    private String subject;

    @NotBlank(message="client is required")
    private String client;

    @NotBlank(message="VAT is required")
    private String vat;

    @NotBlank(message="created is required")
    private String created;

    @NotBlank(message="status is required")
    private String status;

    private int price;

}
