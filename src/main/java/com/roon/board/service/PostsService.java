package com.roon.board.service;

import com.roon.board.domain.posts.Posts;
import com.roon.board.domain.posts.PostsRepository;
import com.roon.board.web.dto.PostsResponseDto;
import com.roon.board.web.dto.PostsSaveRequestDto;
import com.roon.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    //이거 requiredArgsConstructor 때문에 자동으로  @Autowired 된거임.
    private final PostsRepository repository;

    @Transactional  //Describes a transaction attribute on an individual method or on a class.
    public Long save(PostsSaveRequestDto requestDto){
        return repository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id,PostsUpdateRequestDto requestDto){
        Posts post = repository.findById(id).orElseThrow(()->new IllegalArgumentException("id ="+id+"인 게시물 없음"));
        //Return the contained value, if present, otherwise throw an exception to be created by the provided supplier.

        post.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    //이거 왜 필요한건지?
    public PostsResponseDto findById(Long id){
        Posts post = repository.findById(id).orElseThrow(()->new IllegalArgumentException("id ="+id+"인 게시물 없음"));
        return new PostsResponseDto(post);
    }
}
