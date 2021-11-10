	package com._4s_.HR.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
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

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee")
public class HREmployee  implements Auditable,Serializable {
		
		@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_employee_seq")
		@SequenceGenerator(name="hr_employee_seq",sequenceName="hr_employee_seq")//(generate=GeneratorType.IDENTITY)
		private Long id;
		
		private String empCode;
		private String name;
		private String ename;
		private String gender;
		private Date birthDate;
		private String phone;
		private String emergencyPhone;
		private String mobile;
		private String email;
		private String idNumber;
		private String releasePoint;
		private Date releaseDate;
		private String releaseCity;
		private Blob employeePhoto;
		private String employeePhotoName;
		private Date empldate;
		private Integer start_sal;
		private Date subscriptionDate;
		private String insurNo;
		private String permenant;
		private String remark;	
		private Long insurCode;
		private Date insuranceDate;
		private Date insuranceEnd;
		private Integer basic;
		private String flag_exp;
		private Date end_serv;
		private Integer basicti;
		private Integer basicri;
		private Integer basicrt;
		private Integer basicai;
		private Integer basicat;
		private Integer basichour;
		private Integer basictt;
		private Integer no_govern_raise;
		private Integer first_employment;
		private Integer no_var_insu;
		private String halftime;
		private String display_flag;
		private Integer apply_overtime;
		private Integer total_salary;
		private Integer net_sal;
		private Integer contract_salary;
		private Integer workingd;
		private Integer workingh;
		private Integer total_no_hour;
		private String members;

		@Transient
		private Long balance;
		
		@Transient
		private Date serviceDate;
		
		@Transient
		private String year;
		
		@Transient
		private String month;
		
		@Transient
		private String day;
		
	/*	@Transient
		private Date fromDate;
		
		@Transient
		private Date toDate;
		
		@Transient
		private Long consumed;
		
		@Transient
		private HRVacation vacation;*/
		
		@ManyToOne
		@JoinColumn (name="empReligion")
		private HRReligion empReligion;
		
		@ManyToOne
		@JoinColumn (name="militaryService")
		private HRMilitaryService militaryService;
		
		@ManyToOne
		@JoinColumn (name="maritalStatus")
		private HRMaritalStatus maritalStatus;
	
		@ManyToOne
		@JoinColumn (name="empQualification")
		private HREmployeeQualification empQualification;
		
		@ManyToOne
		@JoinColumn (name="empJob")
		private HREmployeeJob currentEmpJob;
		
		@ManyToOne
		@JoinColumn (name="empAddress")
		private HREmployeeAddress empAddress;
		
		@ManyToOne
		@JoinColumn (name="title")
		private HRTitle title;
		
		@ManyToOne
		@JoinColumn (name="currentEmployeeStatus")
		private HREmployeeEmployeeStatus currentEmployeeStatus;
		
		@ManyToOne
		@JoinColumn (name="currentEmployeeSponsor")
		private HREmployeeSponsor currentEmployeeSponsor;
		
		@ManyToOne
		@JoinColumn (name="nationality")
		private HRGeographicalDivision nationality;
		
		@OneToOne
		@JoinColumn (name="sector")
		private Sector sector;
		
		@OneToOne
		@JoinColumn (name="region")
		private HRRegion region;
		
		@OneToOne
		@JoinColumn (name="degCode")
		private Degree degCode;
		
		@ManyToOne
		@JoinColumn (name="location")
		private HRLocation location;
		
		@OneToOne
		@JoinColumn (name="syndicate")
		private HRSyndicate syndicate;		
		
		@OneToOne
		@JoinColumn (name="casher")
		private HRBank casher;
		
		@OneToOne
		@JoinColumn (name="tax_code")
		private HRTax tax_code;
		
		private Date job_join;		

//		@OneToOne
//		@JoinColumn (name="costcode")
//		private HREmployeeCostCenter costcode;
//		
//		@OneToOne
//		@JoinColumn (name="costcode2")
//		private HREmployeeCostCenter costcode2;
		
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeRelative> employeeRelatives=new ArrayList<HREmployeeRelative>();
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeEmployeeStatus> employeeStatusList=new ArrayList<HREmployeeEmployeeStatus>();
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeCourse> employeeCourses=new ArrayList<HREmployeeCourse> ();
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeDocuments> employeeDocuments=new ArrayList<HREmployeeDocuments> ();
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeSponsor> employeeSponsors=new ArrayList<HREmployeeSponsor> ();
		
		@OneToMany (mappedBy="employee",cascade=CascadeType.ALL)
		private List <HREmployeeJob> employeeJobs=new ArrayList<HREmployeeJob>();
		
		
		
		
	/*	@ManyToOne
		@JoinColumn (name="costCenter")
		private HREmployeeCostCenter costCenter;
	*/	
		/*@ManyToOne
		@JoinColumn (name="anotherCostCenter")
		private HREmployeeCostCenter anotherCostCenter;*/
		
		//new
		//@OneToMany(mappedBy="emp_code", cascade = CascadeType.ALL)
		//private List<AInsuranceCala> aics=new ArrayList <AInsuranceCala>(); 

	
		//**************************************************************************************	
		public String getEntityDisplayName() {
			return "employee Addition";
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
			 if (!(o instanceof HREmployee)) {
			 return false;
			 }
			 HREmployee rhs = (HREmployee) o;
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
		
		@Transient
		public String getNameForCertainLocale(){
			LocaleUtil localeUtil = LocaleUtil.getInstance();
			if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
				return getEname();
			}
			return getName();
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
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		
		public HRReligion getEmpReligion() {
			return empReligion;
		}
		public void setEmpReligion(HRReligion empReligion) {
			this.empReligion = empReligion;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
			public String getIdNumber() {
			return idNumber;
		}
		public void setIdNumber(String idNumber) {
			this.idNumber = idNumber;
		}
		public String getReleasePoint() {
			return releasePoint;
		}
		public void setReleasePoint(String releasePoint) {
			this.releasePoint = releasePoint;
		}
		public Date getReleaseDate() {
			return releaseDate;
		}
		public void setReleaseDate(Date releaseDate) {
			this.releaseDate = releaseDate;
		}
		public String getReleaseCity() {
			return releaseCity;
		}
		public void setReleaseCity(String releaseCity) {
			this.releaseCity = releaseCity;
		}
	
		public List<HREmployeeRelative> getEmployeeRelatives() {
			return employeeRelatives;
		}
		public void setEmployeeRelatives(List<HREmployeeRelative> employeeRelatives) {
			this.employeeRelatives = employeeRelatives;
		}
		public List<HREmployeeCourse> getEmployeeCourses() {
			return employeeCourses;
		}
		public void setEmployeeCourses(List<HREmployeeCourse> employeeCourses) {
			this.employeeCourses = employeeCourses;
		}
		public Blob getEmployeePhoto() {
			return employeePhoto;
		}
		public void setEmployeePhoto(Blob employeePhoto) {
			this.employeePhoto = employeePhoto;
		}
		public String getEmployeePhotoName() {
			return employeePhotoName;
		}
		public void setEmployeePhotoName(String employeePhotoName) {
			this.employeePhotoName = employeePhotoName;
		}
		public HREmployeeQualification getEmpQualification() {
			return empQualification;
		}
		public void setEmpQualification(HREmployeeQualification empQualification) {
			this.empQualification = empQualification;
		}
	
		public HREmployeeAddress getEmpAddress() {
			return empAddress;
		}
		public void setEmpAddress(HREmployeeAddress empAddress) {
			this.empAddress = empAddress;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public Date getBirthDate() {
			return birthDate;
		}
		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}
		public HRMilitaryService getMilitaryService() {
			return militaryService;
		}
		public void setMilitaryService(HRMilitaryService militaryService) {
			this.militaryService = militaryService;
		}
		public HRMaritalStatus getMaritalStatus() {
			return maritalStatus;
		}
		public void setMaritalStatus(HRMaritalStatus maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		public HRTitle getTitle() {
			return title;
		}
		public void setTitle(HRTitle title) {
			this.title = title;
		}
		public Integer getStart_sal() {
			return start_sal;
		}
		public void setStart_sal(Integer start_sal) {
			this.start_sal = start_sal;
		}
		public HRGeographicalDivision getNationality() {
			return nationality;
		}
		public void setNationality(HRGeographicalDivision nationality) {
			this.nationality = nationality;
		}
		public String getEmergencyPhone() {
			return emergencyPhone;
		}
		public void setEmergencyPhone(String emergencyPhone) {
			this.emergencyPhone = emergencyPhone;
		}
		public List<HREmployeeEmployeeStatus> getEmployeeStatusList() {
			
			for (int m = 0; m < this.employeeStatusList.size() - 1; m++) {
				HREmployeeEmployeeStatus app=this.employeeStatusList.get(m);
				for (int n = 0; n < this.employeeStatusList.size() - m - 1; n++) {
					HREmployeeEmployeeStatus empStatus=this.employeeStatusList.get(n);
					HREmployeeEmployeeStatus empStatus1=this.employeeStatusList.get(n+1);
					if (empStatus1.getStatusStartDate().after( empStatus.getStatusStartDate())) {
						HREmployeeEmployeeStatus tempEmpStatus = this.employeeStatusList.get(n);
						this.employeeStatusList.set(n, this.employeeStatusList.get(n + 1));
						this.employeeStatusList.set(n + 1, tempEmpStatus);
					}
				}
			}
			return employeeStatusList;
		}
		public void setEmployeeStatusList(
				List<HREmployeeEmployeeStatus> employeeStatusList) {
			this.employeeStatusList = employeeStatusList;
		}
		public HREmployeeEmployeeStatus getCurrentEmployeeStatus() {
		
			
			//to get current status
			for(int i=0;i<this.getEmployeeStatusList().size();i++)
			{
				HREmployeeEmployeeStatus empStatus=	this.getEmployeeStatusList().get(i);
			
			if (empStatus.getStatusStartDate().before(new Date())|| empStatus.getStatusStartDate().equals(new Date()))
			{
			   currentEmployeeStatus=empStatus;
			   break;
			}
		}
			return currentEmployeeStatus;
		}
		public void setCurrentEmployeeStatus(
				HREmployeeEmployeeStatus currentEmployeeStatus) {
			this.currentEmployeeStatus = currentEmployeeStatus;
		}
		public List<HREmployeeDocuments> getEmployeeDocuments() {
			return employeeDocuments;
		}
		public void setEmployeeDocuments(List<HREmployeeDocuments> employeeDocuments) {
			this.employeeDocuments = employeeDocuments;
		}
		public HREmployeeSponsor getCurrentEmployeeSponsor() {
			
			for(int i=0;i<this.getEmployeeSponsors().size();i++)
			{
				HREmployeeSponsor empSponsor=this.getEmployeeSponsors().get(i);
			
			if ((empSponsor.getSponsorStartDate().before(new Date()) && empSponsor.getSponsorStartDate().equals(new Date())) && (empSponsor.getSponsorEndDate().after(new Date())))
			{
			   currentEmployeeSponsor=empSponsor;
			   break;
			}
		}
		  
			return currentEmployeeSponsor;
		}
		public void setCurrentEmployeeSponsor(HREmployeeSponsor currentEmployeeSponsor) {
			this.currentEmployeeSponsor = currentEmployeeSponsor;
		}
		public List<HREmployeeSponsor> getEmployeeSponsors() {
			
			for (int m = 0; m < this.employeeSponsors.size() - 1; m++) {
				HREmployeeSponsor app=this.employeeSponsors.get(m);
				for (int n = 0; n < this.employeeSponsors.size() - m - 1; n++) {
					HREmployeeSponsor empStatus=this.employeeSponsors.get(n);
					HREmployeeSponsor empStatus1=this.employeeSponsors.get(n+1);
					if (empStatus1.getSponsorStartDate().after( empStatus.getSponsorStartDate())) {
						HREmployeeSponsor tempEmpStatus = this.employeeSponsors.get(n);
						this.employeeSponsors.set(n, this.employeeSponsors.get(n + 1));
						this.employeeSponsors.set(n + 1, tempEmpStatus);
					}
				}
			}
			return employeeSponsors;
		}
		public void setEmployeeSponsors(List<HREmployeeSponsor> employeeSponsors) {
			this.employeeSponsors = employeeSponsors;
		}
		public HREmployeeJob getCurrentEmpJob() {
			
			for(int i=0;i<this.getEmployeeJobs().size();i++)
			{
				HREmployeeJob empJob=this.getEmployeeJobs().get(i);
			
			if ((empJob.getJob_start_date().before(new Date())) || (empJob.getJob_start_date().equals(new Date())))
			{
			   currentEmpJob=empJob;
			   break;
			}
		}
			return currentEmpJob;
		}
		public void setCurrentEmpJob(HREmployeeJob currentEmpJob) {
			this.currentEmpJob = currentEmpJob;
		}
		public List<HREmployeeJob> getEmployeeJobs() {
			
			for (int m = 0; m < this.employeeJobs.size() - 1; m++) {
				HREmployeeJob app=this.employeeJobs.get(m);
				for (int n = 0; n < this.employeeJobs.size() - m - 1; n++) {
					HREmployeeJob empJob=this.employeeJobs.get(n);
					HREmployeeJob empJob1=this.employeeJobs.get(n+1);
					if (empJob1.getJob_start_date().after( empJob.getJob_start_date())) {
						HREmployeeJob tempEmpJob = this.employeeJobs.get(n);
						this.employeeJobs.set(n, this.employeeJobs.get(n + 1));
						this.employeeJobs.set(n + 1, tempEmpJob);
					}
				}
			}
			return employeeJobs;
		}
		public void setEmployeeJobs(List<HREmployeeJob> employeeJobs) {
			this.employeeJobs = employeeJobs;
		}
		public Long getBalance() {
			return balance;
		}
		public void setBalance(Long balance) {
			this.balance = balance;
		}
		public Date getServiceDate() {
			return serviceDate;
		}
		public void setServiceDate(Date serviceDate) {
			this.serviceDate = serviceDate;
		}
		public String getYear() {
			return year;
		}
		public void setYear(String year) {
			this.year = year;
		}
		public String getMonth() {
			return month;
		}
		public void setMonth(String month) {
			this.month = month;
		}
		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public Date getSubscriptionDate() {
			return subscriptionDate;
		}
		public void setSubscriptionDate(Date subscriptionDate) {
			this.subscriptionDate = subscriptionDate;
		}
		
		public Long getInsurCode() {
			return insurCode;
		}
		public void setInsurCode(Long insurCode) {
			this.insurCode = insurCode;
		}
		
		
		public String getPermenant() {
			return permenant;
		}
		public void setPermenant(String permenant) {
			this.permenant = permenant;
		}
		public void setInsurNo(String insurNo) {
			this.insurNo = insurNo;
		}
		public String getInsurNo() {
			return insurNo;
		}
		public void setEmpldate(Date empldate) {
			this.empldate = empldate;
		}
		public Date getEmpldate() {
			return empldate;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getRemark() {
			return remark;
		}
		public void setInsuranceDate(Date insuranceDate) {
			this.insuranceDate = insuranceDate;
		}
		public Date getInsuranceDate() {
			return insuranceDate;
		}
		public void setBasic(Integer basic) {
			this.basic = basic;
		}
		public Integer getBasic() {
			return basic;
		}
		public void setFlag_exp(String flag_exp) {
			this.flag_exp = flag_exp;
		}
		public String getFlag_exp() {
			return flag_exp;
		}
		public Date getEnd_serv() {
			return end_serv;
		}
		public void setEnd_serv(Date end_serv) {
			this.end_serv = end_serv;
		}

		public HRTax getTax_code() {
			return tax_code;
		}
		public void setTax_code(HRTax tax_code) {
			this.tax_code = tax_code;
		}
		public Date getJob_join() {
			return job_join;
		}
		public void setJob_join(Date job_join) {
			this.job_join = job_join;
		}
		public Integer getBasicri() {
			return basicri;
		}
		public void setBasicri(Integer basicri) {
			this.basicri = basicri;
		}
		public Integer getBasicrt() {
			return basicrt;
		}
		public void setBasicrt(Integer basicrt) {
			this.basicrt = basicrt;
		}
		public Integer getBasicai() {
			return basicai;
		}
		public void setBasicai(Integer basicai) {
			this.basicai = basicai;
		}
		public Integer getBasicat() {
			return basicat;
		}
		public void setBasicat(Integer basicat) {
			this.basicat = basicat;
		}
		public Integer getBasichour() {
			return basichour;
		}
		public void setBasichour(Integer basichour) {
			this.basichour = basichour;
		}
		public void setBasictt(Integer basic, Integer basicrt, Integer basicat) {
			this.basictt = basic-basicrt-basicat;
		}
		public Integer getBasictt() {
			return basictt;
		}
		public Integer getNo_govern_raise() {
			return no_govern_raise;
		}
		public void setNo_govern_raise(Integer no_govern_raise) {
			this.no_govern_raise = no_govern_raise;
		}
		public Integer getFirst_employment() {
			return first_employment;
		}
		public void setFirst_employment(Integer first_employment) {
			this.first_employment = first_employment;
		}
		public Integer getNo_var_insu() {
			return no_var_insu;
		}
		public void setNo_var_insu(Integer no_var_insu) {
			this.no_var_insu = no_var_insu;
		}
		public String getHalftime() {
			return halftime;
		}
		public void setHalftime(String halftime) {
			this.halftime = halftime;
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
		public Integer getTotal_salary() {
			return total_salary;
		}
		public void setTotal_salary(Integer total_salary) {
			this.total_salary = total_salary;
		}
		public Integer getNet_sal() {
			return net_sal;
		}
		public void setNet_sal(Integer net_sal) {
			this.net_sal = net_sal;
		}
		public Integer getContract_salary() {
			return contract_salary;
		}
		public void setContract_salary(Integer contract_salary) {
			this.contract_salary = contract_salary;
		}
		public Integer getWorkingd() {
			return workingd;
		}
		public void setWorkingd(Integer workingd) {
			this.workingd = workingd;
		}
		public Integer getWorkingh() {
			return workingh;
		}
		public void setWorkingh(Integer workingh) {
			this.workingh = workingh;
		}
		public Integer getTotal_no_hour() {
			return total_no_hour;
		}
		public void setTotal_no_hour(Integer total_no_hour) {
			this.total_no_hour = total_no_hour;
		}
		public String getMembers() {
			return members;
		}
		public void setMembers(String members) {
			this.members = members;
		}
		public Sector getSector() {
			return sector;
		}
		public void setSector(Sector sector) {
			this.sector = sector;
		}
		public HRRegion getRegion() {
			return region;
		}
		public void setRegion(HRRegion region) {
			this.region = region;
		}
		public Degree getDegCode() {
			return degCode;
		}
		public void setDegCode(Degree degCode) {
			this.degCode = degCode;
		}
		public HRLocation getLocation() {
			return location;
		}
		public void setLocation(HRLocation location) {
			this.location = location;
		}
		public HRSyndicate getSyndicate() {
			return syndicate;
		}
		public void setSyndicate(HRSyndicate syndicate) {
			this.syndicate = syndicate;
		}
		public HRBank getCasher() {
			return casher;
		}
		public void setCasher(HRBank casher) {
			this.casher = casher;
		}
//		public HREmployeeCostCenter getCostcode() {
//			return costcode;
//		}
//		public void setCostcode(HREmployeeCostCenter costcode) {
//			this.costcode = costcode;
//		}
//		public HREmployeeCostCenter getCostcode2() {
//			return costcode2;
//		}
//		public void setCostcode2(HREmployeeCostCenter costcode2) {
//			this.costcode2 = costcode2;
//		}
		public void setBasictt(Integer basictt) {
			this.basictt = basictt;
		}
		public void setInsuranceEnd(Date insuranceEnd) {
			this.insuranceEnd = insuranceEnd;
		}
		public Date getInsuranceEnd() {
			return insuranceEnd;
		}
		public void setBasicti(Integer basicti) {
			this.basicti = basicti;
		}
		public Integer getBasicti() {
			return basicti;
		}
		
		
		/*public HREmployeeCostCenter getCostCenter() {
			return costCenter;
		}
		public void setCostCenter(HREmployeeCostCenter costCenter) {
			this.costCenter = costCenter;
		}*/
		/*public HREmployeeCostCenter getAnotherCostCenter() {
			return anotherCostCenter;
		}
		public void setAnotherCostCenter(HREmployeeCostCenter anotherCostCenter) {
			this.anotherCostCenter = anotherCostCenter;
		}
		*/
		
		/*public Date getFromDate() {
			return fromDate;
		}
		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}
		public Date getToDate() {
			return toDate;
		}
		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}
		public Long getConsumed() {
			return consumed;
		}
		public void setConsumed(Long consumed) {
			this.consumed = consumed;
		}
		public HRVacation getVacation() {
			return vacation;
		}
		public void setVacation(HRVacation vacation) {
			this.vacation = vacation;
		}*/

	}


