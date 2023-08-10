package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass // jpa가 해당 클래스는 테이블로 생성하지 않음 - 변화를 감지하는 엔터티이기에 수정되면 안됨 
@EntityListeners(value= {AuditingEntityListener.class})  // 해당 엔티티를 변화를 감지하는 리스너 지정
@Getter
abstract class BaseEntity {
	@CreatedDate  // 인스턴스가 생성되는것을 감지해서 생성된 현재시간을 저장
	LocalDateTime regDate;
	
	@LastModifiedDate  // 인스턴스가 수정되는 것을 감지해서 수정된 현재시간을 저장 
	LocalDateTime modDate;

}
