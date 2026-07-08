package com._4s_.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@Entity
@Table(name = "imonthes")
public class Month implements Serializable, Auditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8323925978685157774L;

	public String getEntityDisplayName() {
		return this.getCode();
	}
	
	@Id
	private String code;

	public String toString() {
		return new ToStringBuilder(this).append("code", this.getCode())
				.toString();
	}

	public boolean equals(Object o) {// //my Business Key is (name)
		if (o == this) {
			return true;
		}
		if (!(o instanceof Month)) {
			return false;
		}
		Branch rhs = (Branch) o;
		return new EqualsBuilder().append(this.code, rhs.getCode())
				.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.code)
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


	
}
