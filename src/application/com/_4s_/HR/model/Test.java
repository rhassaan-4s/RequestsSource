package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="test")
public class Test implements Auditable,Serializable {
	
	
	
	public Test() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="test_seq")
	@SequenceGenerator(name="test_seq",sequenceName="test_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	private String name;
    private Long salary ;
    private Long netSalary ;
	
	public String getEntityDisplayName() {
		return "test Addition";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof Test)) {
		 return false;
		 }
		 Test rhs = (Test) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .isEquals();
	}
	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSalary() {
		return salary;
	}
	public void setSalary(Long salary) {
		this.salary = salary;
	}
	public Long getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(Long netSalary) {
		this.netSalary = netSalary;
	}
	

}

