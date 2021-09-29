package com.tomatopay.domaintransactions.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.tomatopay.domaintransactions.constants.TransactionTypeEnum;
import com.tomatopay.domaintransactions.domainobjects.nosql.LedgerBalanceDO;
import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import com.tomatopay.domaintransactions.respositories.nosql.LedgerBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerBalanceServicesTest {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    LedgerBalanceServices ledgerBalanceServices;

    @Autowired
    LedgerBalanceRepository ledgerBalanceRepository;

    @BeforeEach
    public void beforeEach() {
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        if (!amazonDynamoDB.listTables().getTableNames().contains("LedgerBalances")) {
            CreateTableRequest tableRequest = dynamoDBMapper
                    .generateCreateTableRequest(LedgerBalanceDO.class);
            tableRequest.setProvisionedThroughput(
                    new ProvisionedThroughput(1L, 1L));

            amazonDynamoDB.createTable(tableRequest);

        }
        dynamoDBMapper.batchDelete(
                (List<LedgerBalanceDO>)ledgerBalanceRepository.findAll());
    }

    @Test
    void updateBalance_account_already_exists() {

        LedgerBalanceDO ledgerBalanceDO = new LedgerBalanceDO("accountId", new BigDecimal("11.11"));
        ledgerBalanceRepository.save(ledgerBalanceDO);


        TransactionDO transactionDO = new TransactionDO();
        transactionDO.setId("sampleID");
        transactionDO.setAccountId("accountId");
        transactionDO.setAmount(new BigDecimal("22.22"));
        transactionDO.setCurrency("GBP");
        transactionDO.setType(TransactionTypeEnum.CREDIT);

        ledgerBalanceServices.updateBalance(transactionDO);

        Optional<LedgerBalanceDO> optionalLedgerBalanceDO = ledgerBalanceRepository.findById(transactionDO.getAccountId());

        assertAll(
                () -> assertFalse(optionalLedgerBalanceDO.isEmpty()),
                () -> assertEquals(optionalLedgerBalanceDO.get().getBalance(), new BigDecimal("33.33"))
        );

    }


    @Test
    void updateBalance_account_does_not_exists() {

        TransactionDO transactionDO = new TransactionDO();
        transactionDO.setId("sampleID");
        transactionDO.setAccountId("accountId");
        transactionDO.setAmount(new BigDecimal("22.22"));
        transactionDO.setCurrency("GBP");
        transactionDO.setType(TransactionTypeEnum.CREDIT);

        ledgerBalanceServices.updateBalance(transactionDO);

        Optional<LedgerBalanceDO> optionalLedgerBalanceDO = ledgerBalanceRepository.findById(transactionDO.getAccountId());

        assertAll(
                () -> assertFalse(optionalLedgerBalanceDO.isEmpty()),
                () -> assertEquals(optionalLedgerBalanceDO.get().getBalance(), new BigDecimal("22.22"))
        );

    }
}