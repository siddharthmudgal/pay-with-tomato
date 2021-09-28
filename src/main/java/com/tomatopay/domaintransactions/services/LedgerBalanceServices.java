package com.tomatopay.domaintransactions.services;

import com.tomatopay.domaintransactions.constants.TransactionTypeEnum;
import com.tomatopay.domaintransactions.domainobjects.nosql.LedgerBalanceDO;
import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import com.tomatopay.domaintransactions.respositories.nosql.LedgerBalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/** This service is responsible to update account balances of various accounts
 *
 */
@Service
public class LedgerBalanceServices {

    private final LedgerBalanceRepository ledgerBalanceRepository;

    public LedgerBalanceServices(LedgerBalanceRepository ledgerBalanceRepository) {
        this.ledgerBalanceRepository = ledgerBalanceRepository;
    }

    public void updateBalance(TransactionDO transactionDO) {

        Optional<LedgerBalanceDO> ledgerBalanceDOOptional = ledgerBalanceRepository
                .findById(transactionDO.getAccountId());

        LedgerBalanceDO ledgerBalanceDO = null;

        if (ledgerBalanceDOOptional.isEmpty()) {

            ledgerBalanceDO = new LedgerBalanceDO(
                    transactionDO.getAccountId(), new BigDecimal("0.0")
            );
            ledgerBalanceRepository.save(ledgerBalanceDO);
        } else {
            ledgerBalanceDO = ledgerBalanceDOOptional.get();
        }

        BigDecimal currentBalance = ledgerBalanceDO.getBalance();

        BigDecimal transactionAmount = transactionDO.getAmount();

        if (transactionDO.getType().equals(TransactionTypeEnum.DEBIT)) {

            currentBalance = currentBalance.subtract(transactionAmount);

        } else {

            currentBalance = currentBalance.add(transactionAmount);

        }
        ledgerBalanceDO.setBalance(currentBalance);
        ledgerBalanceRepository.save(ledgerBalanceDO);

    }

}
