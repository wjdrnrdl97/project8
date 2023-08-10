package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity // 엔티티임을 선언 - 엔티티를 선언함에 다라 jpa가 관리하게됨
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder

/*
 * 생성자와 같이 객체를 생성하는 메소드추가. 생성자와 달리 필요한 값만 입력할 수 있음
 * AllArgsConstructor을 하면 모든 생성자를 입력해야하기때문에 필요하지 않은 생성자도 입력해야하는데
 * builder를 사용하여 불필요한 부분은 생성하지않고 필요한 생성자만 생성하게하는 메소드
 *  
 * 
 * */
public class Board extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키, 값이 +1만큼 자동증가
	private int no;
	
	@Column(length=100,nullable=false)  // 길이 100, default not null
	private String title;
	
	@Column(length=1500,nullable=false)  // 길이 1500, default not null
	private String content;
	
//	@Column(length=50,nullable=false)  // 길이 50, default not null
//	private String writer;
	
	// 조회수
	/*
	 * columnDefinition : DB의 컬럼정보를 직접 정의 / "타입 default 값"
	 * */
	@Column(columnDefinition = "integer default 0", length=50)
	private int viewCount;

	/* 작성자 필드는 외래키이며, 회원 엔티티를 참조한다*/
	@ManyToOne // 다대일 관계 적용, jpa가 어노테이션을 통해 외래키 확인한다.
	private Member writer;  //

	
	//	@Override
//	public LocalDateTime getModDate() {
//		// TODO Auto-generated method stub
//		return super.getModDate();
//	}
//
//	@Override
//	public LocalDateTime getRegDate() {
//		// TODO Auto-generated method stub
//		return super.getRegDate();
//	}
	/*
	 * builder로 인해 추상클래스 BaseEntity의 메소드를 따로 재정의할 필요없이 사용이 가능
	 * */
	
	
}
