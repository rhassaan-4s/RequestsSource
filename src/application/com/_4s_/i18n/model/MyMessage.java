package com._4s_.i18n.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @hibernate.class table = "i18n_message"
 */
@Entity
@Table (name = "i18n_message")
public class MyMessage implements Serializable,Auditable{
	@Id
	private Long id;
	@ManyToOne
	//@JoinColumn (name="key")
	private Key key;
	@ManyToOne
	@JoinColumn (name="myLocale")
	private MyLocale myLocale;

	private String message;

	public MyMessage() {
	}

	public MyMessage(MyLocale myLocale) {
		this.myLocale = myLocale;
	}

	public MyMessage(Key key, MyLocale myLocale, String message) {
		this.key = key;
		this.myLocale = myLocale;
		this.message = message;
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
	 * @hibernate.many-to-one column = "messageKey" class ="com._4s_.i18n.model.Key" outer-join="true"
	 */
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	/**
	 * @return
	 * 
	 * @hibernate.many-to-one column = "myLocale" class ="com._4s_.i18n.model.MyLocale" outer-join="true"
	 */
	public MyLocale getMyLocale() {
		return myLocale;
	}

	public void setMyLocale(MyLocale myLocale) {
		this.myLocale = myLocale;
	}

	/**
	 * @return
	 * 
	 * @hibernate.property column = "message"
	 */
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (!(object instanceof MyMessage)) {
			return false;
		}
		MyMessage rhs = (MyMessage) object;
		return new EqualsBuilder()
					.append(this.key, rhs.key)
					.append(this.myLocale, rhs.myLocale)
					.append(this.message, rhs.message)
				.isEquals();
	}
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(669239501, -1389337661)
					.append(this.key)
					.append(this.myLocale)
					.append(this.message)
				.toHashCode();
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
					.append("key", this.key)
					.append("myLocale", this.myLocale)
					.append("message", this.message)
				.toString();
	}

	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return this.message;
	}
}
