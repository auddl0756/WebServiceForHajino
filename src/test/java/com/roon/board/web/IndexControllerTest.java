package com.roon.board.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    // RestTemplate 클래스는 REST 서비스를 호출하도록 설계되어 HTTP 프로토콜의 메서드
    // (ex. GET, POST, DELETE, PUT)에 맞게 여러 메서드를 제공합니다

    @Test
    public void 홈페이지(){
        //when
        String body = restTemplate.getForObject("/",String.class);

        //        public <T> T getForObject(String url,
        //                Class<T> responseType,
        //                Object... urlVariables)
        // : Retrieve a representation by doing a GET on the specified URL.

        //then
        assertThat(body).contains("web service for hajino");
    }
}
