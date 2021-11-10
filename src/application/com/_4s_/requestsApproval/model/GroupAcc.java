package com._4s_.requestsApproval.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="group_acc")
public class GroupAcc implements Auditable,Serializable {
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="group_acc_seq")
	@SequenceGenerator(name="group_acc_seq",sequenceName="group_acc_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String title;
	
	@OneToMany (mappedBy="level_id",cascade=CascadeType.ALL)
	private List <AccessLevels> accessLevel=new ArrayList<AccessLevels>();
	
	
	public Long getId(){
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "GroupAcc Addition";
	}
	
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("title", this.title)
		 .toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.getId())
		.toHashCode();
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setAccessLevel(List <AccessLevels> accessLevel) {
		this.accessLevel = accessLevel;
	}
	public List <AccessLevels> getAccessLevel() {
		return accessLevel;
	}
}
