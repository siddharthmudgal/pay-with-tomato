package com.tomatopay.domaintransactions.respositories.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
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