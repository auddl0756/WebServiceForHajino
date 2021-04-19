package com.roon.board.domain.posts;

import com.roon.board.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor  //Generates a no-args constructor.
@Entity //Specifies that the class is an entity.
public class Posts extends BaseTimeEntity {
    //@GeneratedValue
    //Provides for the specification of generation strategies for the values of primary keys.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=500,nullable=false)
    private String title;

    //columnDefinition 속성
    //The SQL fragment that is used when generating the DDL for the column
    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title,String content,String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }

    public void update(String title,String content){
        this.title=title;
        this.content=content;
    }

}
