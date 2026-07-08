package com._4s_.attendance.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;

@Entity
@Table (name = "trigger_vac_balance")

public class TriggerVacBalance implements Serializable,Auditable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654777149248438986L;
	@Id
//	@GenericGenerator(name="religion_seq",strategy="com._4s_.attendance.dao.ReligionStringKeyGenerator")
//	@GeneratedValue(generator="religion_seq")
	private Timestamp indate;
	private String is_handeled="0";//0 or 1
	
	public Timestamp getIndate() {
		return indate;
	}


	public void setIndate(Timestamp indate) {
		this.indate = indate;
	}


	public String getIs_handeled() {
		return is_handeled;
	}


	public void setIs_handeled(String is_handeled) {
		this.is_handeled = is_handeled;
	}


	public Long getId() {
		return null;
	}

	
	public String getEntityDisplayName() {
		// TODO Auto-generated method stub
		return "Trigger Vac Balance";
	}





	public String toString() {
		return new ToStringBuilder(this)
		.append("name", this.indate)
		/*.append("watchedThreads", this.watchedThreads)*/.toString();
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TriggerVacBalance)) {
			return false;
		}
		TriggerVacBalance rhs = (TriggerVacBalance) o;
		return new EqualsBuilder().append(this.indate, rhs.getIndate()).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder(2090939697, 874530185).append(this.indate).toHashCode();
	}

}
