package com.example.zookeeperscheduler.company.service;

import com.example.zookeeperscheduler.company.model.Company;
import com.example.zookeeperscheduler.company.model.CompanyEntity;
import com.example.zookeeperscheduler.company.model.CompanyEntityFactory;
import com.example.zookeeperscheduler.company.model.FetchStatus;
import com.example.zookeeperscheduler.company.repository.CompanyRepository;
import com.example.zookeeperscheduler.validator.BindingResultFactory;
import com.example.zookeeperscheduler.validator.InputParameterValidator;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class CompanyService {

    private CompanyRepository companyRepository;
    private CompanyEntityFactory companyEntityFactory;
    private SmartValidator smartValidator;
    private InputParameterValidator inputParameterValidator;
    private BindingResultFactory bindingResultFactory;

    @NonNull
    public CompanyEntity getOne(long id) throws EntityNotFoundException {
        return companyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @NonNull
    public CompanyEntity getOrCreateOne(long id) {
        return companyRepository.findById(id).orElse(companyEntityFactory.createNewFromId(id));
    }

    @Transactional
    public Long save(@NonNull CompanyEntity companyEntity) {
        // validate
        BindingResult bindingResult = bindingResultFactory.createForTarget(companyEntity);
        smartValidator.validate(companyEntity, bindingResult);
        inputParameterValidator.validateParameters(bindingResult);

        // store
        log.info("Saving entity: {}", companyEntity);
        return companyRepository.save(companyEntity).getId();
    }

    public void saveCompany(
            @NonNull CompanyEntity companyEntity,
            @NonNull FetchStatus status,
            Company company
    ) {
        if (status.equals(FetchStatus.DONE)) {
            Assert.notNull(company, "Status mismatch");
        }
        companyEntity.setData(company);
        companyEntity.setStatus(status);
        save(companyEntity);
    }
}
