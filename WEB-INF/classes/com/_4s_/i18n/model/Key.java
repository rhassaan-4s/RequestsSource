package com._4s_.i18n.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

/**
 * @author Heba Fouad
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 * 
 * @hibernate.class table = "i18n_key"
 */
@Entity
@Table (name = "i18n_key")
public class Key implements Serializable,Auditable {
	@Id
	private Long id;
	@Column(unique=true)
	private String name;

	private String description;
//	@OneToMany (fetch = FetchType.LAZY, mappedBy ="key",cascade=CascadeType.ALL)
//	private List<MyMessage> messages = new ArrayList<MyMessage>();

	public Key() {
	}

	public Key(String name) {
		this.name = name;
	}

	public Key(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * @return
	 * 
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
	 * 
	 * @hibernate.property column = "name" not-null = "true" unique = "true"
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 * 
	 * @hibernate.property column = "description"
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.set table = "i18n_message" cascade = "save-update" inverse ="true"
	 * @hibernate.collection-key column = "messageKey"
	 * @hibernate.collection-one-to-many class = "com._4s_.i18n.model.MyMessage" 
	 */
//	public List getMessages() {
//		return messages;
//	}
//
//	public void setMessages(List messages) {
//		this.messages = messages;
//	}
/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof Key)) {
			return false;
		}
		Key rhs = (Key) object;
		return new EqualsBuilder()
					.append(this.name, rhs.name)
					.append(this.description, rhs.description)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-1366881971, 1044840463)
					.append(this.name)
					.append(this.description)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
					.append("name", this.name)
					.append("description", this.description)
				.toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
