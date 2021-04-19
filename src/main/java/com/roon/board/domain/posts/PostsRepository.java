package com.roon.board.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    // public interface JpaRepository<T, ID>
    //extends org.springframework.data.repository.PagingAndSortingRepository<T, ID>,  org.springframework.data.repository.query.QueryByExampleExecutor<T>
    //JPA specific extension of org.springframework.data.repository.Repository.

    @Query("select p from Posts p order by p.id desc")
    List<Posts> findAllDesc();
}
