package com._4s_.HR.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;


@Entity//(access=AccessType.FIELD)
@Table(name="hr_END_SERV_REASONS")
public class EndServiceReasons  implements Auditable,Serializable {
		
		@Id 
		@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_endserv_seq")
		@SequenceGenerator(name="hr_endserv_seq",sequenceName="hr_endserv_seq")
		private Long id;
		
		private String code;
		private String aname;
		private String ename;
		private Double reward_ratio ;
		
		
		

		public EndServiceReasons() {
			// TODO Auto-generated constructor stub
		}


		public String getEntityDisplayName() {
			return "EndService Addition";
		}
		
		
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("name", this.aname)
			 .append("ename", this.ename)
			 .toString();
		}
		public boolean equals(Object o) {
			 if (o == this) {
			 return true;
			 }
			 if (!(o instanceof EndServiceReasons)) {
			 return false;
			 }
			 EndServiceReasons rhs = (EndServiceReasons) o;
			 return new EqualsBuilder()
			 .append(this.aname, rhs.getAname())
			 .append(this.ename, rhs.getEname())
			 .isEquals();
		}
		public int hashCode() {
			return new HashCodeBuilder(991383961, 1226766147)
			.append(this.code)

			.toHashCode();
		}
		
		@Transient
		public String getName(){
			LocaleUtil localeUtil = LocaleUtil.getInstance();
			if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
				return getEname();
			}
			return getAname();
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getAname() {
			return aname;
		}
		public void setAname(String aname) {
			this.aname = aname;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}


		public Double getReward_ratio() {
			return reward_ratio;
		}


		public void setReward_ratio(Double reward_ratio) {
			this.reward_ratio = reward_ratio;
		}


		
		
		
}
