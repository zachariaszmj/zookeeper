package com.example.zookeeperscheduler.company.provider;

import com.example.zookeeperscheduler.company.model.Company;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public interface CompanyProvider {
    Company getCompany(long companyId, long accountId);

    @Async
    default ListenableFuture<Company> getCompanyAsync(long companyId, long accountId) {
        try {
            Company company = getCompany(companyId, accountId);
            return AsyncResult.forValue(company);
        } catch (Exception ex) {
            return AsyncResult.forExecutionException(ex);
        }
    }
}