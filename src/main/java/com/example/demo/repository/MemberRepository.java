package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Member;


@Transactional
public interface MemberRepository extends JpaRepository<Member, String>{

}
