	package com._4s_.requestsApproval.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name="login_users")
public class LoginUsers  implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="login_users_seq")
		@SequenceGenerator(name="login_users_seq",sequenceName="login_users_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
		private Long id;
		
		private String empCode;
		private String name;
		private String ename;
		@Column(name="end_serv")
		private Date endServ;
		private String email;
//		private String permissions;

	
		//**************************************************************************************	
		public String getEntityDisplayName() {
			return "LoginUsers Addition";
		}
		@Override
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("name", this.name)
			 .append("ename", this.ename)
			 .toString();
		}
		@Override
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof LoginUsers)) {
			 return false;
			 }
			 LoginUsers rhs = (LoginUsers) o;
			 return new EqualsBuilder()
			 .append(this.name, rhs.getName())
			 .append(this.ename, rhs.getEname())
			 .isEquals();
		}
		@Override
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.getId())

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
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
		public String getEmpCode() {
			return empCode;
		}
		public void setEmpCode(String empCode) {
			this.empCode = empCode;
		}
//		public void setPermissions(String permissions) {
//			this.permissions = permissions;
//		}
//		public String getPermissions() {
//			return permissions;
//		}
//		public void setPassword(String password) {
//			this.password = password;
//		}
//		public String getPassword() {
//			return password;
//		}
		public void setEndServ(Date endServ) {
			this.endServ = endServ;
		}
		public Date getEndServ() {
			return endServ;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getEmail() {
			return email;
		}

}
