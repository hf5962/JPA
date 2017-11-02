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
import com.guigu.jpa.helloword.Order;

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
	
	//修改
	@Test
	public void testUpdate(){
		Customer customer=entityManager.find(Customer.class, 4);
		customer.getOrders().iterator().next().setOrderName("O-XXX-10");
		
	}
	
     //删除
	//默认情况下，若删除1的一端，则会先把关联的多的一段的外键置空,然后删除一的一端
	////可以通过@OneToMany的cascade 属性来修改默认的删除策略
	@Test
	public void testOneToManyRemove(){
		Customer customer=entityManager.find(Customer.class, 7);
		entityManager.remove(customer);
		
	}
	
	//默认对关联多的一方使用懒加载的加载策略(延迟加载)
	//可以使用@OneToMany的fetch 属性来修改默认的加载策略
	//查询
	@Test
	public void testOneToManyFind(){
		Customer customer=entityManager.find(Customer.class,6);
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());
		
	}
	
	//双向一对多的关联关系在执行保存时
	//若先保存多的一端，在保存一的一端，默认情况下，会多出四条update语句
	//若先保存一的一端则会多出2(n)条update语句
	//在进行双向一对多的关联关系时，建议使用多的一方来维护关联关系，而1的一方不维护关联关系，这样会有效的减少sql语句
	//注意：若在一的一端@oneToMany 中使用mapperBy属性，则@oneToMany端就不能在使用@JoinColumn(name="CUSTOMER_ID")属性
	
	//单向一对多保存时，一定会多出update语句
	//因为多的一端在插入时不会同时插入外键列
	//保存
	@Test
	public void testOneToManyPersist(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("CC@163.com");
		customer.setLastName("AA");
		
		Order order1=new Order();
		order1.setOrderName("o-CC-1");
		
		Order order2=new Order();
		order2.setOrderName("o-CC-2");
		
		//建立关联关系
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		//执行保存操作
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);	
		
	}
	
	
	
	
	
	
	/**
	 * 更新操作
	 */
	
	/*@Test
	public void testManyToOneUpdate(){
		Order order=entityManager.find(Order.class, 2);
		order.getCustomer().setLastName("eeaa");
	}
	*//**
	 * 单向多对一删除
	 * 注意不能直接删除一的一端因为有关系约束
	 *//*
	@Test
	public void testManyToOneRemove(){
//		Order order=entityManager.find(Order.class, 1);
//		entityManager.remove(order);//删除多的一端正常删除
		Customer customer=entityManager.find(Customer.class, 5);//删除失败
		entityManager.remove(customer);
		
	}
	
	
	
	//默认情况下，使用左外链接的方式获取n的一端的对象和其关联的1的一段的对象
	//可以使用@ManyToOne的fetch属性来修改默认的关联属性的加载策略
	@Test
	public void testManyToOneFind(){
		Order order=entityManager.find(Order.class, 1);
		System.out.println(order.getOrderName());
		
		System.out.println(order.getCustomer().getLastName());
	
	}
	*//**
	 * 保存多对一，建议先保存1的一段，后保存n的一段，这样不会多出额外的UPDATE语句
	 *//*
	//可以使用@ManyToOne的fetch属性来修改默认的关联属性的加载策略
	@Test
	public void testManyToOnePersist(){
		Customer customer=new Customer();
		customer.setAge(16);
		customer.setBirth(new Date());
		customer.setCreatedTime(new Date());
		customer.setEmail("AA@163.com");
		customer.setLastName("AA");
		
		Order order1=new Order();
		order1.setOrderName("o-AA-1");
		
		Order order2=new Order();
		order2.setOrderName("o-FF-2");
		
		//设置关联关系
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行保存操作
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);	
	}
	*/
	
	
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
