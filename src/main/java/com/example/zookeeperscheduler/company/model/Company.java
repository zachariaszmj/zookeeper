package com.example.zookeeperscheduler.company.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Company {
    @JsonProperty
    private Long id;

    @JsonProperty("legal_name")
    private String name;

    @JsonProperty
    private String email;

    @JsonProperty("vat_id")
    private String vatId;

    @JsonProperty
    private String telephone;

    @JsonProperty("additional_telephones")
    private List<String> additionalTelephones;

    @JsonProperty("fax_number")
    private String faxNumber;
}
