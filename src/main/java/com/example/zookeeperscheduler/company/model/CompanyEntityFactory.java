package com.example.zookeeperscheduler.company.model;

import org.springframework.stereotype.Component;

@Component
public class CompanyEntityFactory {
    public CompanyEntity createNewFromId(long id) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(id);
        companyEntity.setStatus(FetchStatus.NEW);
        return companyEntity;
    }
}
