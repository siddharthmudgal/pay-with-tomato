package com.tomatopay.domaintransactions.services;

import com.tomatopay.domaintransactions.constants.KafkaConstants;
import com.tomatopay.domaintransactions.domainobjects.TransactionDO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    public KafkaListenerService() {

    }

    @KafkaListener(topics = KafkaConstants.TOPICS_LEDGER_BALANCE,
            groupId = KafkaConstants.GROUP_SAMPLE)
    public void listen(TransactionDO transactionDO) throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("Received Messasge in group - group-id: " + transactionDO.getId());
    }

}
