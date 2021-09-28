package com.tomatopay.domaintransactions.respositories.sql.controllers;

import com.tomatopay.domaintransactions.constants.KafkaConstants;
import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import com.tomatopay.domaintransactions.respositories.sql.TransactionRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Repository rest controller for transactions to override the save behaviour
 * to allow transactions to be pushed to an async process for further processing
 */

@RepositoryRestController
public class TransactionRepositoryRestController {

    private final TransactionRepository transactionRepository;

    private final KafkaTemplate<String, TransactionDO> kafkaTemplate;

    public TransactionRepositoryRestController(TransactionRepository transactionRepository, KafkaTemplate<String, TransactionDO> kafkaTemplate) {
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    /** POST transactions handler
     *
     * @param transactionDO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public @ResponseBody ResponseEntity<?> postTransaction(@RequestBody TransactionDO transactionDO) {

        URI location = null;

        // check if transaction ID already exists
        if (transactionRepository.existsById(transactionDO.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // else save and return location
        transactionRepository.save(transactionDO);
        location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(transactionDO.getId())
                .toUri();

        kafkaTemplate.send(KafkaConstants.TOPICS_LEDGER_BALANCE, transactionDO);

        return ResponseEntity.created(location).build();
    }
}
