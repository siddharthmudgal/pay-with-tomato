package com.tomatopay.domaintransactions.domainobjects.nosql;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@DynamoDBTable(
        tableName = "LedgerBalances"
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LedgerBalanceDO {

    @DynamoDBHashKey
    private String accountId;

    @DynamoDBAttribute
    private BigDecimal balance;

}
