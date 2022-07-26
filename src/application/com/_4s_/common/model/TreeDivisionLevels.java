package com._4s_.common.model;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com._4s_.auditing.model.Auditable;
import com._4s_.common.util.LocaleUtil;


@MappedSuperclass
public class TreeDivisionLevels implements Auditable,Serializable {
	
	
//	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hr_internal_level_seq")
//	@SequenceGenerator(name="hr_internal_level_seq",sequenceName="hr_internal_level_seq")//(generate=GeneratorType.IDENTITY)
//	private Long id;
	
	protected Integer levelNo;
	protected Integer length=3;
	protected Boolean isLastLevel;
	protected String name;
	protected String ename;
	
	
	public TreeDivisionLevels() {
		// TODO Auto-generated constructor stub
	}
	public String getEntityDisplayName() {
		return this.getName();
	}
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("levelNo", this.levelNo)
		 .append("name",this.name)
		 .toString();
	}
	
	@Transient
	public String getName(){
		LocaleUtil localeUtil = LocaleUtil.getInstance();
		if(localeUtil.getLocale() != null && localeUtil.getLocale().equalsIgnoreCase("en")) {
			return getEname();
		}
		return name;
	}
	
	
	public Integer getLevelNo() {
		return levelNo;
	}
	public void setLevelNo(Integer levelNo) {
		this.levelNo = levelNo;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Boolean getIsLastLevel() {
		return isLastLevel;
	}
	public void setIsLastLevel(Boolean isLastLevel) {
		this.isLastLevel = isLastLevel;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		
    this.ename=ename;
}
	public Long getId() {
//		return id;
		return null;
	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	
}
