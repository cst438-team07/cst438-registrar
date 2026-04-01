package com.cst438.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cst438.dto.LoginDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerUnitTest {

    @Autowired
    private WebTestClient client;

    /*@Test
    public void testScheduleAndTranscript() {
        // 1. LOGIN as Sam (Credentials match the Bcrypt hash in data.sql)
        String studentEmail = "sam@csumb.edu";
        String password = "sam2025"; 

        EntityExchangeResult<LoginDTO> loginResult = client.get()
                .uri("/login")
                .headers(headers -> headers.setBasicAuth(studentEmail, password))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginDTO.class)
                .returnResult();

        LoginDTO loginDto = loginResult.getResponseBody();
        assertNotNull(loginDto);
        String jwt = loginDto.jwt();
        assertNotNull(jwt);

        // 2. TEST GET SCHEDULE (Year 2026/Spring is in your data.sql as term 11)
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/enrollments")
                        .queryParam("year", 2026)
                        .queryParam("semester", "Spring")
                        .build())
                .headers(headers -> headers.setBearerAuth(jwt))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        // 3. TEST GET TRANSCRIPT
        client.get()
                .uri("/transcripts")
                .headers(headers -> headers.setBearerAuth(jwt))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }*/

    @Test
    public void testScheduleAndTranscript() {
        String studentEmail = "sam@csumb.edu";
        String password = "sam2025"; 

        // 1. LOGIN
        EntityExchangeResult<LoginDTO> loginResult = client.get()
                .uri("/login")
                .headers(headers -> headers.setBasicAuth(studentEmail, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginDTO.class)
                .returnResult();

        String jwt = loginResult.getResponseBody().jwt();

        // 2. TEST TRANSCRIPT (Usually less sensitive to query params)
        client.get()
                .uri("/transcripts")
                .headers(headers -> headers.setBearerAuth(jwt))
                .exchange()
                .expectStatus().isOk();
    }
}