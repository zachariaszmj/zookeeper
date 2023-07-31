package com.example.zookeeperscheduler.company.model.listener;

import com.example.zookeeperscheduler.company.command.ImportCompanyCommand;
import com.example.zookeeperscheduler.company.model.CompanyEntity;
import com.example.zookeeperscheduler.company.model.FetchStatus;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class CompanyEntityListener {
    @Autowired
    private CommandBus commandBus;

    @PostUpdate
    @PostPersist
    public void startDownloadOnNewCompany(CompanyEntity companyEntity) {
        FetchStatus fs = companyEntity.getStatus();
        if (fs.equals(FetchStatus.NEW)) {
            //TODO: inject identity from controller
            commandBus.execute(new ImportCompanyCommand(companyEntity.getId(), 10116972));
        }
    }
}