package com.small.group.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.small.group.entity.Group;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntityManagerTest {
	
	@PersistenceContext
	private EntityManager em;
	
	
//	count = em.createQuery("select count(*) from tbl_user u where u.userId = :userId and u.password = :password", Long.class)
//            .setParameter("userId", userDTO.getUserId())
//            .setParameter("password", userDTO.getPassword())
//            .getSingleResult();
}
