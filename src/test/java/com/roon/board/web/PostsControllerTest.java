package com.roon.board.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roon.board.domain.posts.Posts;
import com.roon.board.domain.posts.PostsRepository;
import com.roon.board.web.dto.PostsSaveRequestDto;
import com.roon.board.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsControllerTest {
    @LocalServerPort    //injects the HTTP port that got allocated at runtime.
    private int randomPort;

    private final String localhost="http://localhost";

    @Autowired
    private TestRestTemplate restTemplate;

    //TestRestTemplate : Convenient alternative of RestTemplate

//    org.springframework.web.client public class RestTemplate
//            extends InterceptingHttpAccessor
//            implements RestOperations
//
//    The RestTemplate offers templates for common scenarios by HTTP method

    @Autowired
    PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc= MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @WithMockUser(roles="USER")
    public void  Posts_등록() throws Exception{
        //given
        String title = "title";
        String content="content";
        String author="author";

        PostsSaveRequestDto requestDto
                = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();

        String url = localhost+":"+randomPort+"/api/v1/posts";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);

        mvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> allPosts = postsRepository.findAll();

        assertThat(allPosts.get(0).getTitle()).isEqualTo(title);
        assertThat(allPosts.get(0).getContent()).isEqualTo(content);
    }


    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정() throws Exception{
        //given
        Posts savedPosts =postsRepository.save(
                Posts.builder()
                .title("original title")
                .content("original content")
                .author("original author")
                .build()
        );

        Long updateId = savedPosts.getId(); //업데이트할 Id
        String expectedTitle = "updated title";
        String expectedContent = "updated content";

        PostsUpdateRequestDto requestDto
                = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url=localhost+":"+randomPort+"/api/v1/posts/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
        //Represents an HTTP request or response entity, consisting of headers and body.

//        assertThat(requestEntity.getBody().getTitle()).isEqualTo(expectedTitle);
//        assertThat(requestEntity.getBody().getContent()).isEqualTo(expectedContent);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestEntity,Long.class);
        //Extension of HttpEntity that adds a HttpStatus status code. Used in RestTemplate as well @Controller methods.

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());


        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postList = postsRepository.findAll();

        assertThat(postList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(postList.get(0).getContent()).isEqualTo(expectedContent);
    }
}
