package com.roon.board.web;

import com.roon.board.domain.posts.Posts;
import com.roon.board.domain.posts.PostsRepository;
import com.roon.board.web.dto.PostsSaveRequestDto;
import com.roon.board.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @Test
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

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> allPosts = postsRepository.findAll();

        assertThat(allPosts.get(0).getTitle()).isEqualTo(title);
        assertThat(allPosts.get(0).getContent()).isEqualTo(content);
    }

    @Test
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

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postList = postsRepository.findAll();

//        for(Posts p:postList){
//            System.out.println(p.getTitle()+" "+p.getContent());
//        }

        assertThat(postList.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(postList.get(0).getContent()).isEqualTo(expectedContent);
    }
}
