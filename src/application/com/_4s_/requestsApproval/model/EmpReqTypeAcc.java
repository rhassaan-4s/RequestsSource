	package com._4s_.requestsApproval.model;

	import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com._4s_.auditing.model.Auditable;

@Entity//(access=AccessType.FIELD)
@Table(name="emp_reqtype_acc")
public class EmpReqTypeAcc  implements Auditable,Serializable {
		
		public EmpReqTypeAcc() {
		// TODO Auto-generated constructor stub
	}


		@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="emp_reqtype_acc_seq")
		@SequenceGenerator(name="emp_reqtype_acc_seq",sequenceName="emp_reqtype_acc_seq", allocationSize = 1)//(generate=GeneratorType.IDENTITY)
		private Long id;
		
		@ManyToOne
		@JoinColumn(name="req_id")
		private RequestTypes req_id;
		
		@ManyToOne
		@JoinColumn(name="emp_id")
		private LoginUsers emp_id;
		
		@ManyToOne
		@JoinColumn(name="group_id")
		private GroupAcc group_id;
		
		@Column(name="priority")
		private long order;

	
		//**************************************************************************************	
		public String getEntityDisplayName() {
			return "LoginUsers Addition";
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


		public void setReq_id(RequestTypes req_id) {
			this.req_id = req_id;
		}


		public RequestTypes getReq_id() {
			return req_id;
		}


		public void setEmp_id(LoginUsers emp_id) {
			this.emp_id = emp_id;
		}


		public LoginUsers getEmp_id() {
			return emp_id;
		}


		public void setOrder(long order) {
			this.order = order;
		}


		public long getOrder() {
			return order;
		}


		public void setGroup_id(GroupAcc group_id) {
			this.group_id = group_id;
		}


		public GroupAcc getGroup_id() {
			return group_id;
		}


		public boolean equals(GroupAcc obj) {
			// TODO Auto-generated method stub
//			return super.equals(obj);
			return this.getId().equals(obj.getId());
		}


		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}



	
}
