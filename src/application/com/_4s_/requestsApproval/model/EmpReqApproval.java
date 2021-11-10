	package com._4s_.requestsApproval.model;

	import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="emp_req_approval")
public class EmpReqApproval  implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="emp_req_approval_seq")
		@SequenceGenerator(name="emp_req_approval_seq",sequenceName="emp_req_approval_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
		private Long id;
		
		@ManyToOne
		@JoinColumn(name="user_id")
		private LoginUsers user_id;
		
		@ManyToOne
		@JoinColumn(name="level_id")
		private EmpReqTypeAcc level_id;

		@ManyToOne
		@JoinColumn(name="req_id")
		private LoginUsersRequests req_id;
		
		private Date approval_date;
		
		private String note;
		
		private int approval;
		
		public EmpReqTypeAcc getLevel_id() {
			return level_id;
		}


		public void setLevel_id(EmpReqTypeAcc level_id) {
			this.level_id = level_id;
		}

	
		//**************************************************************************************	
		public String getEntityDisplayName() {
			return "EmpReqApproval Addition";
		}


		@Override
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.level_id).append(this.req_id)

			.toHashCode();
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof EmpReqApproval)) {
				return false;
			}
			EmpReqApproval rhs = (EmpReqApproval) o;
			return new EqualsBuilder().append(this.level_id, rhs.getLevel_id())
			.append(this.req_id, rhs.getReq_id()).isEquals();
		}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}





		public LoginUsers getUser_id() {
			return user_id;
		}


		public void setUser_id(LoginUsers user_id) {
			this.user_id = user_id;
		}


		public LoginUsersRequests getReq_id() {
			return req_id;
		}


		public void setReq_id(LoginUsersRequests req_id) {
			this.req_id = req_id;
		}


		public void setApproval_date(Date approval_date) {
			this.approval_date = approval_date;
		}


		public Date getApproval_date() {
			return approval_date;
		}


		public void setApproval(int approval) {
			this.approval = approval;
		}


		public int getApproval() {
			return approval;
		}


		public void setNote(String note) {
			this.note = note;
		}


		public String getNote() {
			return note;
		}


	
		
	
}
