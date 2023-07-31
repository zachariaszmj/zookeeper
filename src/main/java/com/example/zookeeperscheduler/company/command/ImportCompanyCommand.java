package com.example.zookeeperscheduler.company.command;

import de.triology.cb.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImportCompanyCommand implements Command<Void> {
    private long companyId;
    private long xUserId;
}
