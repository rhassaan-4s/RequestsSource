package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity
@Table(name = "branch")
public class Branch implements Serializable, Auditable {
	public String getEntityDisplayName() {
		return this.getDescr();
	}
	
	@Id
	@Column(name="branch")
	private String code;
	@Column(name="NAME")
	private String descr;
//	private String warehouse;
//	private String add_items;
//	private String reserve1;
//	private String reserve2;
//	private String reserve3;
//	private String branch_address;
//	private String line1;
//	private String line2;
//	private String line3;
//	private String line4;
//	private Date receiveFromDate;
//	private Date receiveToDate;
	@Column(name="ENAME")
	private String name_en;
//	private String dbServer;
//	private String dbUser;
//	private String str_receiveFromDate;
//	private String str_receiveToDate;
//	private String logo_path;
	
//	@ManyToOne
//	@JoinColumn(name="company")
	@Transient
	private Company company;
	
//	private String lehaa_code;
//	private String lehaa_lost_code;
//	private String lehaa_branch_code;
//	
//	private String transfersDepartment;
//	private String branchDepartment;
//	
//	private Double uploading_price ;

	
	public String toString() {
		return new ToStringBuilder(this).append("name", this.getDescr())
				.toString();
	}

	public boolean equals(Object o) {// //my Business Key is (name)
		if (o == this) {
			return true;
		}
		if (!(o instanceof Branch)) {
			return false;
		}
		Branch rhs = (Branch) o;
		return new EqualsBuilder().append(this.descr, rhs.getDescr())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.descr)
				.toHashCode();
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	

//	public String getWarehouse() {
//		return warehouse;
//	}
//
//	public void setWarehouse(String warehouse) {
//		this.warehouse = warehouse;
//	}
//
//	public String getAdd_items() {
//		return add_items;
//	}
//
//	public void setAdd_items(String add_items) {
//		this.add_items = add_items;
//	}
//
//	public String getReserve1() {
//		return reserve1;
//	}
//
//	public void setReserve1(String reserve1) {
//		this.reserve1 = reserve1;
//	}
//
//	public String getReserve2() {
//		return reserve2;
//	}

//	public void setReserve2(String reserve2) {
//		this.reserve2 = reserve2;
//	}
//
//	public String getReserve3() {
//		return reserve3;
//	}
//
//	public void setReserve3(String reserve3) {
//		this.reserve3 = reserve3;
//	}
//
//	public String getBranch_address() {
//		return branch_address;
//	}
//
//	public void setBranch_address(String branch_address) {
//		this.branch_address = branch_address;
//	}
//
//	public String getLine1() {
//		return line1;
//	}
//
//	public void setLine1(String line1) {
//		this.line1 = line1;
//	}
//
//	public String getLine2() {
//		return line2;
//	}
//
//	public void setLine2(String line2) {
//		this.line2 = line2;
//	}
//
//	public String getLine3() {
//		return line3;
//	}
//
//	public void setLine3(String line3) {
//		this.line3 = line3;
//	}
//
//	public String getLine4() {
//		return line4;
//	}
//
//	public void setLine4(String line4) {
//		this.line4 = line4;
//	}
//
//	public Date getReceiveFromDate() {
//		return receiveFromDate;
//	}
//
//	public void setReceiveFromDate(Date receiveFromDate) {
//		this.receiveFromDate = receiveFromDate;
//	}
//
//	public Date getReceiveToDate() {
//		return receiveToDate;
//	}
//
//	public void setReceiveToDate(Date receiveToDate) {
//		this.receiveToDate = receiveToDate;
//	}

	public Branch() {
		// TODO Auto-generated constructor stub
	}

	public String getName_en() {
		return name_en;
	}

	public void setName_en(String name_en) {
		this.name_en = name_en;
	}

//	public String getDbServer() {
//		return dbServer;
//	}
//
//	public void setDbServer(String dbServer) {
//		this.dbServer = dbServer;
//	}
//
//	public String getDbUser() {
//		return dbUser;
//	}
//
//	public void setDbUser(String dbUser) {
//		this.dbUser = dbUser;
//	}
//
//	public String getStr_receiveFromDate() {
//		return str_receiveFromDate;
//	}
//
//	public void setStr_receiveFromDate(String str_receiveFromDate) {
//		this.str_receiveFromDate = str_receiveFromDate;
//	}
//
//	public String getStr_receiveToDate() {
//		return str_receiveToDate;
//	}
//
//	public void setStr_receiveToDate(String str_receiveToDate) {
//		this.str_receiveToDate = str_receiveToDate;
//	}
//
//	public String getLogo_path() {
//		return logo_path;
//	}
//
//	public void setLogo_path(String logo_path) {
//		this.logo_path = logo_path;
//	}

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
//
//	public String getLehaa_code() {
//		return lehaa_code;
//	}
//
//	public void setLehaa_code(String lehaa_code) {
//		this.lehaa_code = lehaa_code;
//	}
//
//	public String getLehaa_lost_code() {
//		return lehaa_lost_code;
//	}
//
//	public void setLehaa_lost_code(String lehaa_lost_code) {
//		this.lehaa_lost_code = lehaa_lost_code;
//	}
//
//	public String getLehaa_branch_code() {
//		return lehaa_branch_code;
//	}
//
//	public void setLehaa_branch_code(String lehaa_branch_code) {
//		this.lehaa_branch_code = lehaa_branch_code;
//	}
//
//	public void setTransfersDepartment(String transfersDepartment) {
//		this.transfersDepartment = transfersDepartment;
//	}
//
//	public String getTransfersDepartment() {
//		return transfersDepartment;
//	}
//
//	public void setBranchDepartment(String branchDepartment) {
//		this.branchDepartment = branchDepartment;
//	}
//
//	public String getBranchDepartment() {
//		return branchDepartment;
//	}
//
//	public Double getUploading_price() {
//		return uploading_price;
//	}
//
//	public void setUploading_price(Double uploading_price) {
//		this.uploading_price = uploading_price;
//	}

	
}
