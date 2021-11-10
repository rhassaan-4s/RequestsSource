package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;

@MappedSuperclass
public class TreeDivisions   implements Auditable,Serializable {
	
	
	
//	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="hr_geog_division_seq")
//	@SequenceGenerator(name="hr_geog_division_seq",sequenceName="hr_geog_division_seq")//(generate=GeneratorType.IDENTITY)
//	private Long id;

	    protected String  code;
	    protected String ardesc;
		protected String endesc;
		protected Boolean isLast;
		protected String longCode;
		protected String oldLongCode;
		@Transient
		protected Integer order;
		
		
		
	

		
		@Transient
		public String getName(){
			LocaleUtil localeUtil = LocaleUtil.getInstance();
			if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
				return getEndesc();
			}
			return getArdesc();
		}
		
		public String getEntityDisplayName() {
			return this.getArdesc();
		}
		public String toString() {
			 return new ToStringBuilder(this)
			 .append("ardesc", this.ardesc)
			 .append("endesc", this.endesc)
			 .toString();
		}
		
		public String getLongCode() {
			return longCode;
		}
		public void setLongCode(String longCode) {
			this.longCode = longCode;
		}
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getArdesc() {
			return ardesc;
		}
		public void setArdesc(String ardesc) {
			this.ardesc = ardesc;
		}
		public String getEndesc() {
			return endesc;
		}
		public void setEndesc(String endesc) {
			this.endesc = endesc;
		}
		public Boolean getIsLast() {
			return isLast;
		}
		public void setIsLast(Boolean isLast) {
			this.isLast = isLast;
		}
		
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			this.order = order;
		}

		public Long getId() {
//			return id;
			return null;
		}

//		public void setId(Long id) {
//			this.id = id;
//		}

		public String getOldLongCode() {
			return oldLongCode;
		}

		public void setOldLongCode(String oldLongCode) {
			this.oldLongCode = oldLongCode;
		}

	

		

		

		
		

}
