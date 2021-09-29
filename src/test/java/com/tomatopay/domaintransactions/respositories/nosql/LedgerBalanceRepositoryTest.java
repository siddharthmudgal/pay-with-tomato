package com.tomatopay.domaintransactions.respositories.nosql;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.tomatopay.domaintransactions.domainobjects.nosql.LedgerBalanceDO;
import com.tomatopay.domaintransactions.respositories.nosql.LedgerBalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerBalanceRepositoryTest {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    LedgerBalanceRepository ledgerBalanceRepository;

    @Test
    public void saveTransaction() {

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

        LedgerBalanceDO ledgerBalanceDO = new LedgerBalanceDO("accountId", new BigDecimal("11.11"));
        ledgerBalanceRepository.save(ledgerBalanceDO);


        List<LedgerBalanceDO> results = (List<LedgerBalanceDO>) ledgerBalanceRepository.findAll();

        assertAll(
                () -> assertEquals(results.size(), 1),
                () -> assertEquals(results.get(0).getAccountId(), "accountId"),
                () -> assertEquals(results.get(0).getBalance(), new BigDecimal("11.11"))
        );
    }
}