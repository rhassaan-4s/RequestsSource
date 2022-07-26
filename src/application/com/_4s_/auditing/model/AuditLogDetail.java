/*
 * Created on Apr 3, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.model;

//import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author mragab
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "auditing_auditLogDetail" mutable = "false"
 */
@Entity//(access=AccessType.FIELD) -- implicitly declared depending on whether @Id or @EmbeddedId is on field or methods
@Table (name = "auditing_auditLogDetail")
public class AuditLogDetail {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)//(generate=GeneratorType.IDENTITY)
	public Long id;
	@ManyToOne
	@JoinColumn (name="auditLogRecord")
	public AuditLogRecord auditLogRecord;
	
	public String propertyName;
	
	public String removed;
	public String added;

	
	public AuditLogDetail() {
		
	}
	
	/**
	 * @param propertyName
	 * @param removed
	 * @param added
	 */
	public AuditLogDetail(String propertyName, String removed, String added) {
		this.propertyName = propertyName;
		this.removed = removed;
		this.added = added;
	}
	
	/**
	 * @return
	 * @hibernate.id column = "id"
	 * 		generator-class = "native" unsaved-value = "null"
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.many-to-one column = "auditLogRecord" class = "com._4s_.auditing.model.AuditLogRecord" 
	 */
	public AuditLogRecord getAuditLogRecord() {
		return auditLogRecord;
	}
	public void setAuditLogRecord(AuditLogRecord auditLogRecord) {
		this.auditLogRecord = auditLogRecord;
	}

	/**
	 * @return
	 * @hibernate.property column = "propertyName" not-null = "true" 
	 */
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return
	 * @hibernate.property column = "removed" type = "text" 
	 */
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}

	/**
	 * @return
	 * @hibernate.property column = "added" type = "text" 
	 */
	public String getAdded() {
		return added;
	}
	public void setAdded(String added) {
		this.added = added;
	}
}
