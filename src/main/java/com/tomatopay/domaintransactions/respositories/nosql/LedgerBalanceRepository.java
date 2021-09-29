package com.tomatopay.domaintransactions.respositories.nosql;

import com.tomatopay.domaintransactions.domainobjects.nosql.LedgerBalanceDO;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@EnableScan
@RepositoryRestResource(collectionResourceRel = "ledgerbalance", path = "ledgerbalance")
public interface LedgerBalanceRepository extends CrudRepository<LedgerBalanceDO, String> {
}
