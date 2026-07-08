package com._4s_.HR.model;

import java.io.Serializable;
import java.sql.Blob;
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
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @author hoda
 *
 */

@Entity//(access=AccessType.FIELD)
@Table(name="hr_employee_documents")
public class HREmployeeDocuments implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_employee_documents_seq")
	@SequenceGenerator(name="hr_employee_documents_seq",sequenceName="hr_employee_documents_seq")
	private Long id;
	
	@ManyToOne 
	@JoinColumn (name="employee")
	private HREmployee employee;
	
	@ManyToOne 
	@JoinColumn (name="document")
	private HRDocuments document;
	
	@ManyToOne 
	@JoinColumn (name="documentOwner")
	private HREmployeeRelative documentOwner;
	
	
	private Date expireDate;
	
	private Date releaseDate;
	private String releaseOrganization;
	
	private Blob documentPhoto;
	private String documentPhotoName;
	
	public String getEntityDisplayName() {
		return "Employee_employee_documents Addition";
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("employee", this.employee)
		 .append("document", this.document)
		 .toString();
	}
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREmployeeDocuments)) {
		 return false;
		 }
		 HREmployeeDocuments rhs = (HREmployeeDocuments) o;
		 return new EqualsBuilder()
		 .append(this.employee.getName(), rhs.employee.getName())
		 .append(this.employee.getEname(), rhs.employee.getEname())
		 .isEquals();
	}
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
	
	
	public HREmployee getEmployee() {
		return employee;
	}
	public void setEmployee(HREmployee employee) {
		this.employee = employee;
	}
	public HRDocuments getDocument() {
		return document;
	}
	public void setDocument(HRDocuments document) {
		this.document = document;
	}
	public HREmployeeRelative getDocumentOwner() {
		return documentOwner;
	}
	public void setDocumentOwner(HREmployeeRelative documentOwner) {
		this.documentOwner = documentOwner;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getReleaseOrganization() {
		return releaseOrganization;
	}
	public void setReleaseOrganization(String releaseOrganization) {
		this.releaseOrganization = releaseOrganization;
	}
	public Blob getDocumentPhoto() {
		return documentPhoto;
	}
	public void setDocumentPhoto(Blob documentPhoto) {
		this.documentPhoto = documentPhoto;
	}
	public String getDocumentPhotoName() {
		return documentPhotoName;
	}
	public void setDocumentPhotoName(String documentPhotoName) {
		this.documentPhotoName = documentPhotoName;
	}
	

}
