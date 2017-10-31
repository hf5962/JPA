package com.guigu.jpa.test;



import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.guigu.jpa.helloword.Customer;

public class JPATest {
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction transaction;
	@Before
	public void init(){
		entityManagerFactory= Persistence.createEntityManagerFactory("jpa-1");
		entityManager=entityManagerFactory.createEntityManager();
		transaction=entityManager.getTransaction();
		transaction.begin();//开启事务
		
	}
	@After
	public void destroy(){
		transaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	//类似于hibernate的save方法使对象由临时状态转变为持久化状态
	//和hibernatede save方法不同之处：对象由id则不能执行insert操作，会抛出异常
	@Test
	public void testPersistence(){
		Customer customer=new Customer();
		customer.setAge(22);
		
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("@123456.conm");	
		customer.setLastName("张三");
//		customer.setId(100);//hi抛出异常
		entityManager.persist(customer);
		System.err.println(customer.getId());
		
	}
	
	//类似于hibernate 中的session中的load方法
	@Test
	public void testGetReference(){
		Customer customer=entityManager.getReference(Customer.class, 1);//1表示id
		System.out.println(customer.getClass().getName());
		System.out.println("-------------------------------");
//		transaction.commit();
//		entityManagerFactory.close();
		System.out.println(customer);
		
	}
	//类似于hibernate 中的session中的get方法
	@Test
	public void testFind(){
		Customer customer=entityManager.find(Customer.class, 1);//1表示id
		System.out.println("-------------------------------");
		System.out.println(customer);
		
	}

}
