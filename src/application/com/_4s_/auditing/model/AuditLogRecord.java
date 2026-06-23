/*
 * Created on Mar 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.auditing.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author mragab
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "auditing_auditLog" mutable = "false"
 */
@Entity//W(access=AccessType.FIELD)
@Table (name = "auditing_auditLog")
public class AuditLogRecord {
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	public Long id;

	public String message;

	public Long entityId;

	public String entityDisplayName;

	public Class entityClass;

	public Long userId;

	public Date created;
	@OneToMany (mappedBy ="auditLogRecord",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<AuditLogDetail>auditLogDetails = new ArrayList<AuditLogDetail>();
	@Transient
	public Auditable entity = null;
	@Transient
	public String entityClassName;
	@Transient
	public String userName;
	
	/**
	 * @return
	 * @hibernate.property column = "entityDisplayName"  not-null="false"
	 */
	public String getEntityDisplayName() {
		return entityDisplayName;
	}

	public void setEntityDisplayName(String entityDisplayName) {
		this.entityDisplayName = entityDisplayName;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	AuditLogRecord() {
	}

	public AuditLogRecord(String message, Auditable entity, Long userId,String entityDisplayName) {
		this.message = message;
		this.entity = entity;
		this.entityDisplayName = entityDisplayName;
		this.entityId = (Long)entity.getId();
		this.entityClass = entity.getClass();
		this.userId = userId;
		this.created = new Date();
	}

	/**
	 * @return
	 * @hibernate.id column = "id" generator-class = "native" unsaved-value =
	 *               "null"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 * @hibernate.set table = "auditing_auditLogDetail" cascade = "save-update" inverse =
	 *                "true"
	 * @hibernate.collection-key column = "auditLogRecord"
	 * @hibernate.collection-one-to-many class =
	 *                                   "com._4s_.auditing.model.AuditLogDetail"
	 */
	public List getAuditLogDetails() {
		return auditLogDetails;
	}

	public void setAuditLogDetails(List auditLogDetails) {
		this.auditLogDetails = auditLogDetails;
	}

	public void addAuditLogDetails(AuditLogDetail auditLogDetail) {
		this.auditLogDetails.add(auditLogDetail);
		auditLogDetail.setAuditLogRecord(this);
	}

	/**
	 * @return
	 * @hibernate.property column = "created" not-null = "true"
	 */
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return
	 * @hibernate.property column = "entityClass" not-null = "true"
	 */
	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * @return
	 * @hibernate.property column = "entityId" not-null = "true"
	 */
	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return
	 * @hibernate.property column = "message" not-null = "true"
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// /**
	// * @return
	// * @hibernate.property column = "userName" not-null = "true"
	// */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return
	 * @hibernate.property column = "userId" not-null = "true"
	 */
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Auditable getEntity() {
		return entity;
	}

	public void setEntity(Auditable entity) {
		this.entity = entity;
	}
}