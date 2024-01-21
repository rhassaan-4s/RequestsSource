package com._4s_.common.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com._4s_.attendance.model.AttendanceDepartment;
import com._4s_.attendance.model.MaritalStatus;
import com._4s_.attendance.model.Qualification;
import com._4s_.attendance.model.QualificationSpeciality;
import com._4s_.attendance.model.Religion;
import com._4s_.attendance.model.Title;
import com._4s_.auditing.model.Auditable;
import com._4s_.common.web.validators.Mandatory;
import com._4s_.common.web.validators.MandatoryDate;
import com._4s_.common.web.validators.Unique;

@Entity
@Table (name = "EMPBASIC")
public class EmpBasic implements Serializable,Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9030836215327821538L;
	@Id
	@GenericGenerator(name="empBasic_seq",strategy="com._4s_.attendance.dao.EmpBasicStringKeyGenerator")
	@GeneratedValue(generator="empBasic_seq")
	private String empCode;
	@Mandatory(value=EmpBasic.class,property="empName")
	@Unique(value=EmpBasic.class,property = "empName")
	private String empName;
	@Mandatory(value=EmpBasic.class,property="e_emp_name")
	@Unique(value=EmpBasic.class,property = "e_emp_name")
	private String e_emp_name;
	private String permenant="T";
	@ManyToOne
	@JoinColumn (name="title")
	private Title title;
	@Mandatory(value=EmpBasic.class,property="address")
	private String address;
	private String eAddress;
	private String should_sign="1";
	@Mandatory(value=EmpBasic.class,property="natnl_no")
	@Unique(value=EmpBasic.class,property = "natnl_no")
	private String natnl_no;
//	@Temporal(TemporalType.DATE)
//	@Mandatory(value=EmpBasic.class,property="emplDate")
	@MandatoryDate(value=EmpBasic.class,property="emplDate")
	private Timestamp emplDate;
//	@Temporal(TemporalType.DATE)
//	@Mandatory(value=EmpBasic.class,property="birthdate")
	@MandatoryDate(value=EmpBasic.class,property="birthdate")
	private Timestamp birthdate;
//	@Temporal(TemporalType.DATE)
	private Timestamp job_join;
	private String phone;
	private String mobile;
	private String sex;
//	@Temporal(TemporalType.DATE)
	private Timestamp end_serv;
	@ManyToOne
	@JoinColumn (name="eldiana")
	private Religion eldiana;
	@ManyToOne
	@JoinColumn (name="martial")
	private MaritalStatus maritalStatus;
	@ManyToOne
	@JoinColumn (name="location")
	private AttendanceDepartment department;
	@ManyToOne
	@JoinColumn (name="qual_specific")
	private QualificationSpeciality qual_specific;
	@ManyToOne
	@JoinColumn (name="qual")
	private Qualification qual;
	@Column(name="qual_year")
	private Integer qualYear;
	
	private Double basic;
	private Double workingH=8d;
	private Double workingD=30d;
	private String lang = "A";
	private String form = "1";
	private Double total_no_hour=240d;
	private String display_flag="T";
	private Integer apply_overtime=1;
	private String overtime_code="001";
	private String vacrule_code="001";
	private String illness_code="1";
	private Integer work_status=1;
	private String handicapped="0";
	private String currency="01";
	
	public Double getBasic() {
		return basic;
	}

	public void setBasic(Double basic) {
		this.basic = basic;
	}

	public Long getId() {
		return null;
	}

	public Date getBirthdate() {
		return birthdate;
	}



	public void setBirthdate(Timestamp birthdate) {
		this.birthdate = birthdate;
	}



	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getE_emp_name() {
		return e_emp_name;
	}

	

	public AttendanceDepartment getDepartment() {
		return department;
	}

	public void setDepartment(AttendanceDepartment department) {
		this.department = department;
	}

	public void setE_emp_name(String e_emp_name) {
		this.e_emp_name = e_emp_name;
	}


	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}


	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	public String getPermenant() {
		return permenant;
	}


	public void setPermenant(String permenant) {
		this.permenant = permenant;
	}

	public Title getTitle() {
		return title;
	}


	public void setTitle(Title title) {
		this.title = title;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String geteAddress() {
		return eAddress;
	}


	public void seteAddress(String eAddress) {
		this.eAddress = eAddress;
	}


	public String getShould_sign() {
		return should_sign;
	}


	public void setShould_sign(String should_sign) {
		this.should_sign = should_sign;
	}


	public String getNatnl_no() {
		return natnl_no;
	}

	public void setNatnl_no(String natnl_no) {
		this.natnl_no = natnl_no;
	}

	public Timestamp getEmplDate() {
		return emplDate;
	}


	public void setEmplDate(Timestamp emplDate) {
		this.emplDate = emplDate;
	}


	public Timestamp getJob_join() {
		return job_join;
	}


	public void setJob_join(Timestamp job_join) {
		this.job_join = job_join;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public Religion getEldiana() {
		return eldiana;
	}


	public void setEldiana(Religion eldiana) {
		this.eldiana = eldiana;
	}

	public QualificationSpeciality getQual_specific() {
		return qual_specific;
	}


	public void setQual_specific(QualificationSpeciality qual_specific) {
		this.qual_specific = qual_specific;
	}


	public Qualification getQual() {
		return qual;
	}


	public void setQual(Qualification qual) {
		this.qual = qual;
	}


	public Integer getQualYear() {
		return qualYear;
	}


	public void setQualYear(Integer qualYear) {
		this.qualYear = qualYear;
	}

	public Double getWorkingH() {
		return workingH;
	}

	public void setWorkingH(Double workingH) {
		this.workingH = workingH;
	}

	public Double getWorkingD() {
		return workingD;
	}

	public void setWorkingD(Double workingD) {
		this.workingD = workingD;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public Date getEnd_serv() {
		return end_serv;
	}

	public void setEnd_serv(Timestamp end_serv) {
		this.end_serv = end_serv;
	}

	public Double getTotal_no_hour() {
		return total_no_hour;
	}

	public void setTotal_no_hour(Double total_no_hour) {
		this.total_no_hour = total_no_hour;
	}

	public String getDisplay_flag() {
		return display_flag;
	}

	public void setDisplay_flag(String display_flag) {
		this.display_flag = display_flag;
	}

	public Integer getApply_overtime() {
		return apply_overtime;
	}

	public void setApply_overtime(Integer apply_overtime) {
		this.apply_overtime = apply_overtime;
	}

	public String getOvertime_code() {
		return overtime_code;
	}

	public void setOvertime_code(String overtime_code) {
		this.overtime_code = overtime_code;
	}

	public String getVacrule_code() {
		return vacrule_code;
	}

	public void setVacrule_code(String vacrule_code) {
		this.vacrule_code = vacrule_code;
	}

	public String getIllness_code() {
		return illness_code;
	}

	public void setIllness_code(String illness_code) {
		this.illness_code = illness_code;
	}

	public Integer getWork_status() {
		return work_status;
	}

	public void setWork_status(Integer work_status) {
		this.work_status = work_status;
	}

	public String getHandicapped() {
		return handicapped;
	}

	public void setHandicapped(String handicapped) {
		this.handicapped = handicapped;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("natNi_no", this.natnl_no)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof EmpBasic)) {
			return false;
		}
		EmpBasic rhs = (EmpBasic) o;
		return new EqualsBuilder().append(this.natnl_no, rhs.getNatnl_no()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.natnl_no).toHashCode();
	}


	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "empBasic";
	}
}
