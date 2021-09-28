package com.tomatopay.domaintransactions.services;

import com.tomatopay.domaintransactions.constants.KafkaConstants;
import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private final LedgerBalanceServices ledgerBalanceServices;

    public KafkaListenerService(LedgerBalanceServices ledgerBalanceServices) {
        this.ledgerBalanceServices = ledgerBalanceServices;
    }

    @KafkaListener(topics = KafkaConstants.TOPICS_LEDGER_BALANCE,
            groupId = KafkaConstants.GROUP_SAMPLE)
    public void listen(TransactionDO transactionDO) throws InterruptedException {
        ledgerBalanceServices.updateBalance(transactionDO);
        System.out.println("Hello");
    }

}
