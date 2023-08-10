package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

import jakarta.transaction.Transactional;
// Jparepository를 상속받음(객체, 객체기본키의 객체자료형) 

@Transactional  // JPA Query로 변화를 처리해야할 때 사용(commit 처리)
public interface BoardRepository extends JpaRepository<Board, Integer>{
	/*
	 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.modifying-queries
	 *
	 * @Modifying : 쿼리를 수정할 때 사용
	 * @query : sql문
	 * */
	@Modifying  
	@Query("update Board b set b.viewCount = b.viewCount + 1 where b.no = :no")
	int updateView(int no);
	
	// 특정 작성자가 쓴 게시물 삭제 메소드
	@Modifying  // 쿼리를 수정할 때 사용 / DML 중 insert, update, delete 사용할 때 사용하는 어노테이션
	@Query("delete from Board b where b.writer = :member")
	public void deleteWriter(@Param("member")Member member);
	// 엔티티에 있는 자료형 타입을 그대로 가져와야한다.(String타입으로 받으면 타입이 맞지않다고 에러 발생)
}
