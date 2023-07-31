package com.example.zookeeperscheduler.company.repository;

import com.example.zookeeperscheduler.company.model.CompanyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, Long> {

}
