package com.softvision.digital.invoice.rest.v1;

import com.softvision.digital.common.model.ResultDto;
import com.softvision.digital.common.util.ResultUtil;
import com.softvision.digital.invoice.rest.v1.model.InvoiceDto;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/invoice/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        value = "All operations pertaining to sample")
public class InvoiceRest {

    private static final List<InvoiceDto> INVOICES = new ArrayList<>();

    @PostConstruct
    private void init() {
        String[][] data = {
                { "100401", "Design Works",         "Carlson Limited",  "87956621", "15 Dec 2017",  "Paid",             "887" },
                { "100402", "UX Wireframes",        "Adobe",            "87956421", "12 Apr 2017",  "Pending",          "1200" },
                { "100403", "New Dashboard",        "Bluewolf",         "87956421", "23 Oct 2017",  "Pending",          "534" },
                { "100404", "Landing Page",         "Salesforce",       "87956421", "2 Sep 2017",   "Due in 2 Weeks",   "1500" },
                { "100405", "Marketting Templates", "Printic",          "87956421", "29 Jan 2018",  "Paid Today",       "648" }
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

    @GetMapping("/slow")
    public List<InvoiceDto> getAllSlow() {
        sleep();
        return getAll();
    }

    @GetMapping("/{id}")
    public InvoiceDto get(@PathVariable("id") String id) {
        sleep();
        return INVOICES.stream().filter(invoice -> id.equals(invoice.getId())).findFirst().orElse(null);
    }

    @PostMapping("/")
    public ResultDto add(@RequestBody @Valid InvoiceDto invoiceDto) {
        sleep();
        Optional<InvoiceDto> optional = INVOICES.stream().filter(invoice -> invoiceDto.getId().equals(invoice.getId())).findFirst();
        if (optional.isPresent()) {
            return ResultUtil.getFailure("Invoice already exists with Id");
        }
        String nextId = String.valueOf(Integer.parseInt(INVOICES.get(INVOICES.size() - 1).getId())+1);
        invoiceDto.setId(nextId);
        INVOICES.add(invoiceDto);
        return ResultUtil.getSuccess("Invoice has been added successfully", invoiceDto);
    }

    @PostMapping("/{id}")
    public ResultDto update(@PathVariable("id") String id, @RequestBody @Valid InvoiceDto invoiceDto) {
        sleep();
        INVOICES.stream().filter(invoice -> id.equals(invoice.getId())).findFirst().ifPresent(invoice -> {
            invoice.setClient(invoiceDto.getClient());
            invoice.setCreated(invoiceDto.getCreated());
            invoice.setPrice(invoiceDto.getPrice());
            invoice.setStatus(invoiceDto.getStatus());
            invoice.setSubject(invoiceDto.getSubject());
            invoice.setVat(invoiceDto.getVat());
        });
        return ResultUtil.getSuccess("Invoice has been updated successfully", invoiceDto);
    }

    @DeleteMapping("/{id}")
    public ResultDto delete(@PathVariable("id") String id) {
        sleep();
        boolean removed = INVOICES.removeIf(invoice -> id.equals(invoice.getId()));
        if (removed) {
            return ResultUtil.getSuccess("Invoice has been deleted successfully");
        } else {
            return ResultUtil.getSuccess("Could not find the invoice to delete");
        }
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
