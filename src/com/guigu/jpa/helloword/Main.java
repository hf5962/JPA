package com.guigu.jpa.helloword;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		//1. 创建EntitymanagerFactory
		String persistenceUnitName="jpa-1";
		Map<String,Object> properties=new HashMap<String,Object>();
		properties.put("hibernate.show_sql", true);
		
		EntityManagerFactory entityManagerFactory= Persistence.createEntityManagerFactory(persistenceUnitName);
		//EntityManagerFactory entityManagerFactory=Persistence.createEntityManagerFactory(persistenceUnitName, properties);
		
		//2. 创建EntityManager类似于hibernate的sessionFactory
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		//3. 开启事物
		EntityTransaction transaction=entityManager.getTransaction();
		transaction.begin();
		//4. 进行持久化操作
		Customer customer=new Customer();
		customer.setAge(13);
		customer.setEmail("318889659@qq.com");
		customer.setLastName("helloworld");
				
		
		
		entityManager.persist(customer);
		//5. 提交事物
		transaction.commit();
		//6. 关闭EntityManager
		entityManager.close();
		//7. 关闭EntitymanagerFactory
		entityManagerFactory.close();
	}

}
