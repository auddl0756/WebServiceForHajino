package com.roon.board.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   //Superclass임을 명시 (모든 entity들의 상위 클래스)
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate //Declares a field as the one representing the date the entity containing the field was created.
    private LocalDateTime createdDate;

    @LastModifiedDate //Declares a field as the one representing the date the entity containing the field was recently modified.
    private LocalDateTime modifiedDate;
}
