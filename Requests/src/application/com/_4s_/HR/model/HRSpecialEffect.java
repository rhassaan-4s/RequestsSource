package com._4s_.HR.model;

import java.io.Serializable;

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

@Entity//(access=AccessType.FIELD)
@Table(name="hr_special_effect")
public class HRSpecialEffect implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="HR_SPECIAL_EFFECT_SEQ")
	@SequenceGenerator(name="HR_SPECIAL_EFFECT_SEQ",sequenceName="HR_SPECIAL_EFFECT_SEQ")//(generate=GeneratorType.IDENTITY)
	private Long id;
	
	private String effcode;
	private String effname;
	private String eng_name;
	private String scrbrif;
	private Boolean secured ;//DEFAULT '0'

	@ManyToOne
	@JoinColumn (name="effnature")
	private HREffectNature effnature;
	
	@ManyToOne
	@JoinColumn (name="effrule1")
	private  HREffectRule effrule1;
	
	private String efftax;  //       DEFAULT 'F'
	private String effinsur;  //       DEFAULT 'F'
	private String effdam;
	
	@ManyToOne
	@JoinColumn (name="effdisc_days")
	private  HREffectDiscDays effdisc_days ;
	
	private Double anualmarg;
	
	@ManyToOne
	@JoinColumn (name="anualmarg_is_ratio")
	private HREffectExemptionLimit anualmarg_is_ratio; //DEFAULT 'F'
	
	private  String group_code;
	private String scrpos;
	
	@ManyToOne
	@JoinColumn (name="round_type")
	private HREffectRoundType round_type;// DEFAULT 1
	
	private Double round_value=new Double(0) ; // DEFAULT 0.01
	
	private String displayed_flag ; //       DEFAULT 'T'

	private Integer calac_method; 
	private String anualincom;
	private Long affect_illness;
	private Long both_pos;  //     DEFAULT 999
	
	public String getEntityDisplayName(){
		return "SpecialEffect Addition";
	}
	@Override
	public String toString(){
		return new ToStringBuilder(this)
		.append("name", this.effname)
		.append("ename", this.eng_name)
		.toString();
	}
	@Override
	public boolean equals(Object o){
		 if (o == this){
			 return true;
		 }
		 if (!(o instanceof HRSpecialEffect)){
			 return false;
		 }
		 HRSpecialEffect rhs = (HRSpecialEffect) o;
		 return new EqualsBuilder()
		 .append(this.effname, rhs.getEffname())
		 .append(this.eng_name, rhs.getEng_name())
		 .isEquals();
	}
	@Override
	public int hashCode(){
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
	public String getEffcode() {
		return effcode;
	}
	public void setEffcode(String effcode) {
		this.effcode = effcode;
	}
	public String getEffname() {
		return effname;
	}
	public void setEffname(String effname) {
		this.effname = effname;
	}
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public String getScrbrif() {
		return scrbrif;
	}
	public void setScrbrif(String scrbrif) {
		this.scrbrif = scrbrif;
	}
	public Boolean getSecured() {
		return secured;
	}
	public void setSecured(Boolean secured) {
		this.secured = secured;
	}
	public HREffectNature getEffnature() {
		return effnature;
	}
	public void setEffnature(HREffectNature effnature) {
		this.effnature = effnature;
	}
	public HREffectRule getEffrule1() {
		return effrule1;
	}
	public void setEffrule1(HREffectRule effrule1) {
		this.effrule1 = effrule1;
	}
	public String getEfftax() {
		return efftax;
	}
	public void setEfftax(String efftax) {
		this.efftax = efftax;
	}
	public String getEffinsur() {
		return effinsur;
	}
	public void setEffinsur(String effinsur) {
		this.effinsur = effinsur;
	}
	public String getEffdam() {
		return effdam;
	}
	public void setEffdam(String effdam) {
		this.effdam = effdam;
	}
	public HREffectDiscDays getEffdisc_days() {
		return effdisc_days;
	}
	public void setEffdisc_days(HREffectDiscDays effdisc_days) {
		this.effdisc_days = effdisc_days;
	}
	public Double getAnualmarg() {
		return anualmarg;
	}
	public void setAnualmarg(Double anualmarg) {
		this.anualmarg = anualmarg;
	}
	public HREffectExemptionLimit getAnualmarg_is_ratio() {
		return anualmarg_is_ratio;
	}
	public void setAnualmarg_is_ratio(HREffectExemptionLimit anualmarg_is_ratio) {
		this.anualmarg_is_ratio = anualmarg_is_ratio;
	}
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	public String getScrpos() {
		return scrpos;
	}
	public void setScrpos(String scrpos) {
		this.scrpos = scrpos;
	}
	public HREffectRoundType getRound_type() {
		return round_type;
	}
	public void setRound_type(HREffectRoundType round_type) {
		this.round_type = round_type;
	}
	public Double getRound_value() {
		return round_value;
	}
	public void setRound_value(Double round_value) {
		this.round_value = round_value;
	}
	public String getDisplayed_flag() {
		return displayed_flag;
	}
	public void setDisplayed_flag(String displayed_flag) {
		this.displayed_flag = displayed_flag;
	}
	public Integer getCalac_method() {
		return calac_method;
	}
	public void setCalac_method(Integer calac_method) {
		this.calac_method = calac_method;
	}
	public String getAnualincom() {
		return anualincom;
	}
	public void setAnualincom(String anualincom) {
		this.anualincom = anualincom;
	}
	public Long getAffect_illness() {
		return affect_illness;
	}
	public void setAffect_illness(Long affect_illness) {
		this.affect_illness = affect_illness;
	}
	public Long getBoth_pos() {
		return both_pos;
	}
	public void setBoth_pos(Long both_pos) {
		this.both_pos = both_pos;
	}
}
