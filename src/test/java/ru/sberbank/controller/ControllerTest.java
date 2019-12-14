package ru.sberbank.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ViewController controller;

    @Test
    public void textNotNull() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void index() {
        assertThat(this.restTemplate.getForObject("http://localhost:8080" + "/rbk",
                String.class)).contains("64.3507");
    }

}