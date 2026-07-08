package com._4s_.security.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.model.Branch;
//import com._4s_.stores.model.StoreTrnsDef;

@Entity
@Table(name = "security_user_branch")
public class UserBranch implements Serializable,Auditable {
	

	public UserBranch() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@SequenceGenerator(name = "userBranchSeq", sequenceName = "security_user_branch_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userBranchSeq")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "userprivilege")
	private UserPrivilege userprivilege;
	@ManyToOne
	@JoinColumn (name="branch")
	private Branch branch;
	
	@Transient
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Branch getBranch() {
		return branch;
	}

	

	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public void setUserprivilege(UserPrivilege userprivilege) {
		this.userprivilege = userprivilege;
	}

	public UserPrivilege getUserprivilege() {
		return userprivilege;
	}


}
