package com._4s_.HR.model;

import java.io.Serializable;

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
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_vacation")
public class HRVacation implements Auditable,Serializable {
	
	public HRVacation() {
		// TODO Auto-generated constructor stub
	}
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_vacation_seq")
	@SequenceGenerator(name="hr_vacation_seq",sequenceName="hr_vacation_seq")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String code;
	private String name;
	private String ename;
	private Boolean isMigrationAllowed;
	private Long maxNumberForMigration;
	
	@ManyToOne
	@JoinColumn (name="vacationType")
	private HRVacationType vacationType;
	
	@ManyToOne
	@JoinColumn (name="migratedTo")
	private HRVacation migratedTo;
	private Long monthsBeforeLeaveEntitlement;
	
	public String getEntityDisplayName() {
		return "vacation Addition";
	}
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.name)
		 .append("ename", this.ename)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HRVacation)) {
		 return false;
		 }
		 HRVacation rhs = (HRVacation) o;
		 return new EqualsBuilder()
		 .append(this.name, rhs.getName())
		 .append(this.ename, rhs.getEname())
		 .isEquals();
	}
	public int hashCode() {
		return new HashCodeBuilder(991383961, 1226766147)
		.append(this.id)

		.toHashCode();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Boolean getIsMigrationAllowed() {
		return isMigrationAllowed;
	}
	public void setIsMigrationAllowed(Boolean isMigrationAllowed) {
		this.isMigrationAllowed = isMigrationAllowed;
	}
	public Long getMaxNumberForMigration() {
		return maxNumberForMigration;
	}
	public void setMaxNumberForMigration(Long maxNumberForMigration) {
		this.maxNumberForMigration = maxNumberForMigration;
	}
	public HRVacationType getVacationType() {
		return vacationType;
	}
	public void setVacationType(HRVacationType vacationType) {
		this.vacationType = vacationType;
	}
	public HRVacation getMigratedTo() {
		return migratedTo;
	}
	public void setMigratedTo(HRVacation migratedTo) {
		this.migratedTo = migratedTo;
	}
	public Long getMonthsBeforeLeaveEntitlement() {
		return monthsBeforeLeaveEntitlement;
	}
	public void setMonthsBeforeLeaveEntitlement(Long monthsBeforeLeaveEntitlement) {
		this.monthsBeforeLeaveEntitlement = monthsBeforeLeaveEntitlement;
	}

}
