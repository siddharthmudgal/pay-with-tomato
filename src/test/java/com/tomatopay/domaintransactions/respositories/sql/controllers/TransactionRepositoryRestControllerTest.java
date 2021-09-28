package com.tomatopay.domaintransactions.respositories.sql.controllers;

import com.tomatopay.domaintransactions.domainobjects.sql.TransactionDO;
import com.tomatopay.domaintransactions.respositories.sql.TransactionRepository;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.Future;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class TransactionRepositoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("classpath:data.sql")
    void postTransactionSuccess() throws Exception {
        this.mockMvc.perform(
                post("/transactions")
                        .content(
                                "{\n" +
                                "\t\"id\": \"f1fb5da5-5418-4ac7Az\",\n" +
                                "\t\"accountId\": \"d4fb5ta7-8028-5cf8\",\n" +
                                "\t\"currency\": \"GBPA\",\n" +
                                "\t\"amount\": 234.9523,\n" +
                                "\t\"description\": \"Tesco Holborn Station\",\n" +
                                "\t\"type\": \"CREDIT\"\n" +
                                "}"
                        ).contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(header().exists("location"));
    }

    @Test
    @Sql("classpath:data.sql")
    void postTransactionFail() throws Exception {
        this.mockMvc.perform(
                post("/transactions")
                        .content(
                                "{\n" +
                                        "\t\"id\": \"f1fb5da5-5418-4ac7\",\n" +
                                        "\t\"accountId\": \"d4fb5ta7-8028-5cf8\",\n" +
                                        "\t\"currency\": \"GBPA\",\n" +
                                        "\t\"amount\": 234.9523,\n" +
                                        "\t\"description\": \"Tesco Holborn Station\",\n" +
                                        "\t\"type\": \"CREDIT\"\n" +
                                        "}"
                        ).contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print()).andExpect(status().isConflict());
    }

}