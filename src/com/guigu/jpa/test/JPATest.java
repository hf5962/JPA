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
	
	/**
	 * 若传入得是一个游离状态的对象，即传入的对象有OID
	 * 1、若在EntityManager缓存中有该对象
	 * 2、JPA会把游离状态的属性复制到EntityManager缓存中的对象中
	 * 3、EntityManager缓存中的对象象执行update
	 */
	@Test
	public void testMerge4(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");
		customer.setId(4);
	
		Customer customer2=entityManager.find(Customer.class, 4);
		
		entityManager.merge(customer);
		System.out.println(customer==customer2);//false
		
	}
	
	/**
	 * 若传入得是一个游离状态的对象，即穿入的对象有OID
	 * 1、若在EntityManager缓存中没有该对象
	 * 2、若在数据库中有改记录
	 * 3JPA会查询对应的记录，然后返回该记录对应的一个对象，再然后会把游离对象的属性复制到查询到的对象中
	 * 4、对查询到的对象执行update操作
	 */
	@Test
	public void testMerge3(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("ee@163.com");
		customer.setLastName("EE");
		customer.setId(4);
		Customer customer2=entityManager.merge(customer);
		System.out.println(customer==customer2);//false
		
	}
	
	
	/**
	 * 若传入得是一个游离状态的对象，即穿入的对象有OID
	 * 1、若在EntityManager缓存中没有该对象
	 * 2、若在数据库中也没有改记录
	 * 3JPA会创建一个新对象，然后把前游离对象的属性复制到新创建的对象中
	 * 4、对新创建的对象执行insert操作
	 */
	@Test
	public void testMerge2(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("hello@163.com");
		customer.setLastName("李四");
		customer.setId(100);
		Customer customer2=entityManager.merge(customer);
		System.out.println("customer#id"+customer.getId());
		System.out.println("customer2#id"+customer2.getId());
		
	}
	
	
	/*
	 * 总的来说：类似于hibernate session 的saveOrUpdate方法
	 * 1.若传入得是一个临时对象则会创建一个新对象，把零时对象的属性复制到新对象中，然后对新对象执行持久化操作
	 * 所以新的对象有id,但是以前的零时对象中没有id
	 */
	@Test
	public void testMerge1(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("hello@163.com");
		customer.setLastName("李四");
		
		Customer customer2=entityManager.merge(customer);
		System.out.println("customer#id"+customer.getId());
		System.out.println("customer2#id"+customer2.getId());
		
	}
	//类似于hibernated的delete方法，把对象对应的记录从数据库中移除
	//但注意：该方法只能移除持久化对象，而hibernate的delete方法实际上还可以移除游离对象
	@Test
	public void testRemove(){
//		Customer customer =new Customer();
//		customer.setId(2);
		Customer customer=entityManager.find(Customer.class, 2);
		entityManager.remove(customer);
		
		
		
		
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