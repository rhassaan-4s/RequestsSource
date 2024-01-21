package com._4s_.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity
@Table(name = "web_branch")
public class WebBranch implements Serializable, Auditable {
	public String getEntityDisplayName() {
		return this.getDescr();
	}
	
	@Id
	@SequenceGenerator(name = "branch", sequenceName = "COMMON_webbranch_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch")
	//@Column(name="branch")
	private Long branch;
	@Column(name="NAME")
	private String descr;
	@Column(name="ENAME")
	private String name_en;
//	@ManyToOne
//	@JoinColumn(name="company")
	@Transient
	private Company company;

	private Float branchLat;
	private Float branchLong;
	
	public Float getBranchLat() {
		return branchLat;
	}

	public void setBranchLat(Float branchLat) {
		this.branchLat = branchLat;
	}

	public Float getBranchLong() {
		return branchLong;
	}

	public void setBranchLong(Float branchLong) {
		this.branchLong = branchLong;
	}

	
	
	public String toString() {
		return new ToStringBuilder(this).append("name", this.getName())
				.toString();
	}

	public boolean equals(Object o) {// //my Business Key is (name)
		if (o == this) {
			return true;
		}
		if (!(o instanceof WebBranch)) {
			return false;
		}
		WebBranch rhs = (WebBranch) o;
		return new EqualsBuilder().append(this.branch, rhs.getBranch()).append(this.descr, rhs.getDescr())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.descr)
				.toHashCode();
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return this.branch;
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

	@Transient
	public String getName(){
		LocaleUtil localeUtil = LocaleUtil.getInstance();
		if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
			return getName_en();
		}
		return getDescr();
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
	
}
