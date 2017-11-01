package com.guigu.jpa.helloword;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 单向多对一
 * 一个订单可以有多个用户
 * 一个用户可以有多个订单
 * @author z
 *
 */
@Table(name="JPA_ORDERS")
@Entity
public class Order {
	private Integer id;
	private String orderName;
	
//	private Customer customer;
	@GeneratedValue//使用默认的主键生成方式
    @Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    @Column(name="ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
    //映射单向n-1的关联关系
	//使用@ManyToOne 来映射多对一的关系
	//使用@JoinColumn 来映射外键
	//可以使用@ManyToOne的fetch属性来修改默认的关联属性的加载策略
//	@JoinColumn(name="CUSTOMER_ID")//外键列的列名
//	@ManyToOne(fetch=FetchType.LAZY)
//	public Customer getCustomer() {
//		return customer;
//	}

//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderName=" + orderName + ", customer="  + "]";
	}
	

}
