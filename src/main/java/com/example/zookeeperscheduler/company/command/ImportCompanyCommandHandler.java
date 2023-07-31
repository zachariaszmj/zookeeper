package com.example.zookeeperscheduler.company.command;


import com.example.zookeeperscheduler.company.model.Company;
import com.example.zookeeperscheduler.company.model.CompanyEntity;
import com.example.zookeeperscheduler.company.model.FetchStatus;
import com.example.zookeeperscheduler.company.provider.CompanyProvider;
import com.example.zookeeperscheduler.company.service.CompanyService;
import com.example.zookeeperscheduler.exception.BailedRequestException;
import de.triology.cb.CommandBus;
import de.triology.cb.CommandHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;


import javax.persistence.EntityNotFoundException;

@Component
@Slf4j
@AllArgsConstructor
public class ImportCompanyCommandHandler implements CommandHandler<Void, ImportCompanyCommand> {

    private CompanyService companyService;
    private CompanyProvider companyProvider;
    private CommandBus commandBus;

    @Override
    public Void handle(final ImportCompanyCommand command) throws EntityNotFoundException {
        final CompanyEntity companyEntity = companyService
                .getOne(command.getCompanyId());

        if (companyEntity.getStatus().equals(FetchStatus.NEW)) {
            new ImportTask(command, companyEntity).run();
        }

        return null;
    }

    @AllArgsConstructor
    class ImportTask implements Runnable {
        private ImportCompanyCommand command;
        private CompanyEntity companyEntity;

        @Override
        public void run() {
            // set mark download in progress
            log.info("Downloading company id: " + command.getCompanyId());
            companyService.saveCompany(companyEntity, FetchStatus.DOWNLOADING, null);

            companyProvider.getCompanyAsync(
                    command.getCompanyId(),
                    command.getXUserId()
            ).addCallback(onSuccess, onFailure);
        }

        final SuccessCallback<Company> onSuccess = company -> {
            log.info("Downloading company " + command.getCompanyId() + " success");
            companyService.saveCompany(companyEntity, FetchStatus.DONE, company);
        };

        final FailureCallback onFailure = ex -> {
            log.error("Downloading company " + command.getCompanyId() + " error", ex);
            if (ex instanceof BailedRequestException) {
                companyService.saveCompany(companyEntity, FetchStatus.ERROR, null);
            } else {
                companyService.saveCompany(companyEntity, FetchStatus.NEW, null);
                commandBus.execute(command);
            }
        };
    }
}