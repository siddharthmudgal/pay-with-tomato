package com.tomatopay.domaintransactions.domainobjects;

import com.tomatopay.domaintransactions.constants.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/** This domain object interfaces to database and stores the transaction entity
 * in a sql db
 */

@Entity
@Table (
        name = "transactions"
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionDO {

    @Id
    private String id;

    @Column(nullable = false, name = "account_id")
    private String accountId;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum type;

}
