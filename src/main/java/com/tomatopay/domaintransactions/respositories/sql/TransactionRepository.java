package com.tomatopay.domaintransactions.respositories.sql;

import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/** This repository exposes rest to CRUD with transactions entity
 *
 */
@RepositoryRestResource(collectionResourceRel = "transactions", path = "transactions")
public interface TransactionRepository extends PagingAndSortingRepository<TransactionDO, String> {
}
