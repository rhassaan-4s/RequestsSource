package com._4s_.attendance.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.auditing.model.Auditable;
import com._4s_.requestsApproval.model.Vacation;

@Entity
@Table (name = "vacrule")

public class VacRule implements Serializable,Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6544437517628503952L;
	@Id
	@ManyToOne
	@JoinColumn (name="vacation")
	private Vacation vacation;
	@Id
	@Column(name="srv_year")
	private Integer srvYear;
	@Column(name="entiteld")
	private Integer entitled;
	private String code = "001";
	private String may_transfer = "0";
	private Integer for_contracts = 0;
	private String sex = "3";
	
	public Long getId() {
		return null;
	}

	
		public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



		public Vacation getVacation() {
		return vacation;
	}



	public void setVacation(Vacation vac) {
		this.vacation = vac;
	}



	public Integer getSrvYear() {
		return srvYear;
	}



	public void setSrvYear(Integer srvYear) {
		this.srvYear = srvYear;
	}



	public Integer getEntitled() {
		return entitled;
	}



	public void setEntitled(Integer entitled) {
		this.entitled = entitled;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getMay_transfer() {
		return may_transfer;
	}



	public void setMay_transfer(String may_transfer) {
		this.may_transfer = may_transfer;
	}



	public Integer getFor_contracts() {
		return for_contracts;
	}



	public void setFor_contracts(Integer for_contracts) {
		this.for_contracts = for_contracts;
	}



		public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "vac rule";
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("vacation ", this.vacation).append("service years " , this.srvYear)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof VacRule)) {
			return false;
		}
		VacRule rhs = (VacRule) o;
		return new EqualsBuilder().append(this.vacation, rhs.getVacation()).append(this.srvYear, rhs.getSrvYear()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.vacation).append(this.srvYear).toHashCode();
	}

}
