	package com._4s_.requestsApproval.model;

	import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.requestsApproval.model.GroupAcc;
import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity//(access=AccessType.FIELD)
@Table(name="emp_reqtype_acc")
public class EmpReqTypeAcc  implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="emp_reqtype_acc_seq")
		@SequenceGenerator(name="emp_reqtype_acc_seq",sequenceName="emp_reqtype_acc_seq")//(generate=GeneratorType.IDENTITY)
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



	
}
