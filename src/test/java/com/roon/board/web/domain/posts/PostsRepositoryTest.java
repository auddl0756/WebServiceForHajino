package com.roon.board.web.domain.posts;

import com.roon.board.domain.posts.Posts;
import com.roon.board.domain.posts.PostsRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)    //SpringRunner is an alias for the SpringJUnit4ClassRunner.
@SpringBootTest //Annotation that can be specified on a test class that runs Spring Boot based tests
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After  // @After causes that method to be run after the Test method.
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장후_불러오기(){
        //given
        String title = "test title";
        String content ="test content";

        postsRepository.save(
                Posts.builder()
                .title(title)
                .content(content)
                .author("leemr@naver.com")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);

        //System.out.println(post.getTitle()+" "+post.getContent());
    }

    @Test
    public void BaseTimeEntity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2021,4,19,0,0,0);
        postsRepository.save(
                Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build()
        );

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts post = postsList.get(0);

        System.out.println("createdDate = "+post.getCreatedDate() +" modifiedDate ="+post.getModifiedDate());

        assertThat(post.getCreatedDate()).isAfter(now);
        assertThat(post.getModifiedDate()).isAfter(now);

    }
}
