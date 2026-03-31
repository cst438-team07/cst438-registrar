package com.cst438.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.LoginDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerUnitTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        User user = userRepository.findByEmail("sam@csumb.edu");
        if (user == null) {
            user = new User();
            user.setEmail("sam@csumb.edu");
            user.setPassword("$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2K8WBR3Go9BEyiSXYMGC"); 
            user.setName("Sam");
            // Use setType instead of setRole to satisfy the database
            user.setType("STUDENT"); 
            userRepository.save(user);
        }
    }

    @Test
    @WithMockUser(username = "sam@csumb.edu", authorities = {"SCOPE_ROLE_STUDENT"})
    public void testScheduleAndTranscript() {
        String email = "sam@csumb.edu";
        String password = "sam2025";

        // login to get JWT
        EntityExchangeResult<LoginDTO> login = client.get()
                .uri("/login")
                .headers(headers -> headers.setBasicAuth(email, password))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginDTO.class)
                .returnResult();

        String jwt = login.getResponseBody().jwt();
        assertNotNull(jwt);

        // test schedule
        client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/enrollments")
                        .queryParam("year", 2025)
                        .queryParam("semester", "Fall")
                        .build())
                .headers(headers -> headers.setBearerAuth(jwt))
                .exchange()
                .expectStatus().isOk();

        // test transcript
        client.get()
                .uri("/transcripts")
                .headers(headers -> headers.setBearerAuth(jwt))
                .exchange()
                .expectStatus().isOk();
    }

    
}
