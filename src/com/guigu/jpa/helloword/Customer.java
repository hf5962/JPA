package com.guigu.jpa.helloword;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *数据库持久化类
 * @author z
 *
 */
@Table(name="JPA_CUSTOMERS")//主要是映射表名对应的数据库表名JPA_CUSTOMER默认情况下可以不写表名与持久化类名相同
@Entity //表明这是一个持久化类
public class Customer {
	
	private Integer id;
	private String lastName;
	private String email;
	private int  age ;
	
	private Date createdTime;
	private Date birth;
	
	/**
	 * 定义各数据列必须加在get方法上
	 * @return
	 */
	//定义主键，生成主键的策略AUTO自动的根据数据的类型生成主键
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id //定义数据列
//	@Column(name="ID")//定义数据库的列名如果与字段名一样可以省略
	
	//使用table生成主键
//	@TableGenerator(name="ID_GENERATOR",
//			table="JPA_ID_GENERATORS",
//			pkColumnName="PK_NAME",
//			pkColumnValue="CUSTOMER_ID",
//			valueColumnName="PK_VALUE",
//			allocationSize=100
//			
//			
//			)
//	
//	@GeneratedValue(strategy=GenerationType.TABLE,generator="ID_GENERATOR")
//	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="LAST_NAME",length=50,nullable=false) //nullable false表示此字段不能为空
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	
	
   //工具方法，不需要映射为数据表的一列
	@Transient
	public String getInfo(){
		return "lastName:"+lastName+",email:"+email;
	}

}
