package com.softvision.digital.invoice.rest.v1;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import com.softvision.digital.invoice.rest.v1.model.InvoiceDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/invoice/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "All operations pertaining to sample")
public class InvoiceRest {

    private static final List<InvoiceDto> INVOICES = new ArrayList<>();
    @PostConstruct
    private void init() throws IOException {
        String[][] data = {
                { "001401", "Design Works",         "Carlson Limited",  "87956621", "15 Dec 2017",  "Paid",             "887" },
                { "001402", "UX Wireframes",        "Adobe",            "87956421", "12 Apr 2017",  "Pending",          "1200" },
                { "001403", "New Dashboard",        "Bluewolf",         "87956421", "23 Oct 2017",  "Pending",          "534" },
                { "001404", "Landing Page",         "Salesforce",       "87956421", "2 Sep 2017",   "Due in 2 Weeks",   "1500" },
                { "001405", "Marketting Templates", "Printic",          "87956421", "29 Jan 2018",  "Paid Today",       "648" }
        };
        for (String[] row : data) {
            int index=0;
            InvoiceDto invoice = new InvoiceDto();
            invoice.setId(row[index++]);
            invoice.setSubject(row[index++]);
            invoice.setClient(row[index++]);
            invoice.setVat(row[index++]);
            invoice.setCreated(row[index++]);
            invoice.setStatus(row[index++]);
            invoice.setPrice(Integer.parseInt(row[index++]));
            INVOICES.add(invoice);
        }

    }

    @GetMapping("/")
    public List<InvoiceDto> getAll() {
        return INVOICES;
    }

    @GetMapping("/{id}")
    public InvoiceDto get(@PathVariable("id") String id) {
        return INVOICES.stream().filter(invoice -> id.equals(invoice.getId())).findFirst().orElse(null);
    }

    @PostMapping("/")
    public ResultDto add(@RequestBody @Valid InvoiceDto invoiceDto) {
        return ResultUtil.getSuccess("Invoice added successfully", INVOICES.add(invoiceDto));
    }

    @PostMapping("/{id}")
    public ResultDto update(@PathVariable("id") String id, @RequestBody @Valid InvoiceDto invoiceDto) {
        INVOICES.stream().filter(invoice -> id.equals(invoice.getId())).findFirst().ifPresent(invoice -> {
            invoice.setClient(invoiceDto.getClient());
            invoice.setCreated(invoiceDto.getCreated());
            invoice.setPrice(invoiceDto.getPrice());
            invoice.setStatus(invoiceDto.getStatus());
            invoice.setSubject(invoiceDto.getSubject());
            invoice.setVat(invoiceDto.getVat());
        });
        return ResultUtil.getSuccess("Invoice updated successfully", invoiceDto);
    }

    @DeleteMapping("/{id}")
    public ResultDto delete(@PathVariable("id") String id) {
        boolean removed = INVOICES.removeIf(invoice -> id.equals(invoice.getId()));
        if (removed) {
            return ResultUtil.getSuccess("Deleted successfully");
        } else {
            return ResultUtil.getSuccess("Could not find invoice to delete");
        }
    }
}
