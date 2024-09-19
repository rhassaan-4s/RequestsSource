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

public class EmpReqApprovalJSON   {
		
		public EmpReqApprovalJSON() {
		// TODO Auto-generated constructor stub
	}


		private String user_id;
		private Long level_id;
		private Date approval_date;
		private String note;
		private int approval;
		
		public Long getLevel_id() {
			return level_id;
		}


		public void setLevel_id(Long level_id) {
			this.level_id = level_id;
		}

	
		public String getUser_id() {
			return user_id;
		}


		public void setUser_id(String user_id) {
			this.user_id = user_id;
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


		public EmpReqApprovalJSON(String user_id, Long level_id, Date approval_date, String note,
				int approval) {
			super();
			this.user_id = user_id;
			this.level_id = level_id;
			this.approval_date = approval_date;
			this.note = note;
			this.approval = approval;
		}
		
		
}
