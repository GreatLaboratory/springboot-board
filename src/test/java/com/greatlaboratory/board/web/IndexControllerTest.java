package com.greatlaboratory.board.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void loadMainPage(){
        // when
        String body = restTemplate.getForObject("/", String.class);

        // then
        assertThat(body).contains("GreatLaboratory Board");
    }
}
