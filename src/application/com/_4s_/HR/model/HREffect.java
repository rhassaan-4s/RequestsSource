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
@Table(name="hr_effect")
public class HREffect implements Auditable,Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HR_EFFECT_SEQ")
	@SequenceGenerator(name="HR_EFFECT_SEQ",sequenceName="HR_EFFECT_SEQ")

	
	
	private Long id ;
	private String effcode  ;
	private String effname ;
	private String eng_name;
	private String scrbrif ;
	//private  Date fromdate;
	//private Date todate ;
	
	@ManyToOne
	@JoinColumn (name="effnature")
	private HREffectNature effnature  ;
	private String efftax ;
	private String effinsur ;
	private String scrpos  ;
	/*private Long prnpos;
	
	private String prnbrif;
	private String anualincom;
	private  Double anualmarg;*/
	private String effdam ;
	
	@ManyToOne
	@JoinColumn (name="effrule1")
	private  HREffectRule effrule1;
	private  String group_code;
	//private  String str_fromdate;
	//private String str_todate;
	
	private String displayed_flag ; //       DEFAULT 'T'
	
	@ManyToOne
	@JoinColumn (name="effdisc_days")
	private  HREffectDiscDays effdisc_days ;
	//private  String consider_basic;
	private String pay_mandated;
	private String eff_shamel;
	private Long affect_illness;
	//private String anualmarg_is_ratio; //DEFAULT 'F'
	private String eff_net;
	private Boolean secured ;//DEFAULT '0'
	
	@ManyToOne
	@JoinColumn (name="eff_apply_type")
	private HREffectApplyType eff_apply_type; // DEFAULT 'R'
	private Long eff_apply_rank ;
	private String is_temp;         // DEFAULT 'F'
	private Double default_value=new Double(0) ;
	//private Long both_pos;  //     DEFAULT 999,
	private Double round_value=new Double(0) ; // DEFAULT 0.01,
	
	@ManyToOne
	@JoinColumn (name="round_type")
	private HREffectRoundType round_type;// DEFAULT 1
	
	
	public String getEntityDisplayName() {
		return "Effect Addition";
	}
	@Override
	public String toString() {
		 return new ToStringBuilder(this)
		 .append("name", this.effname)
		 .append("ename", this.eng_name)
		 .toString();
	}
	@Override
	public boolean equals(Object o) {
		 if (o == this) {
		 return true;
		 }
		 if (!(o instanceof HREffect)) {
		 return false;
		 }
		 HREffect rhs = (HREffect) o;
		 return new EqualsBuilder()
		 .append(this.effname, rhs.getEffname())
		 .append(this.eng_name, rhs.getEng_name())
		 .isEquals();
	}
	@Override
	public int hashCode() {
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
	/*public Date getFromdate() {
		return fromdate;
	}
	public void setFromdate(Date fromdate) {
		this.fromdate = fromdate;
	}
	public Date getTodate() {
		return todate;
	}
	public void setTodate(Date todate) {
		this.todate = todate;
	}*/
	
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
	public String getScrpos() {
		return scrpos;
	}
	public void setScrpos(String scrpos) {
		this.scrpos = scrpos;
	}
	public String getScrbrif() {
		return scrbrif;
	}
	public void setScrbrif(String scrbrif) {
		this.scrbrif = scrbrif;
	}
	/*public Long getPrnpos() {
		return prnpos;
	}
	public void setPrnpos(Long prnpos) {
		this.prnpos = prnpos;
	}
	
	public String getPrnbrif() {
		return prnbrif;
	}
	public void setPrnbrif(String prnbrif) {
		this.prnbrif = prnbrif;
	}
	public String getAnualincom() {
		return anualincom;
	}
	public void setAnualincom(String anualincom) {
		this.anualincom = anualincom;
	}
	public Double getAnualmarg() {
		return anualmarg;
	}
	public void setAnualmarg(Double anualmarg) {
		this.anualmarg = anualmarg;
	}*/
	public String getEffdam() {
		return effdam;
	}
	public void setEffdam(String effdam) {
		this.effdam = effdam;
	}
	
	
	public String getGroup_code() {
		return group_code;
	}
	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}
	/*public String getStr_fromdate() {
		return str_fromdate;
	}
	public void setStr_fromdate(String str_fromdate) {
		this.str_fromdate = str_fromdate;
	}
	public String getStr_todate() {
		return str_todate;
	}
	public void setStr_todate(String str_todate) {
		this.str_todate = str_todate;
	}*/
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	
	public String getDisplayed_flag() {
		return displayed_flag;
	}
	public void setDisplayed_flag(String displayed_flag) {
		this.displayed_flag = displayed_flag;
	}
	/*
	public String getConsider_basic() {
		return consider_basic;
	}
	public void setConsider_basic(String consider_basic) {
		this.consider_basic = consider_basic;
	}*/
	public String getPay_mandated() {
		return pay_mandated;
	}
	public void setPay_mandated(String pay_mandated) {
		this.pay_mandated = pay_mandated;
	}
	public String getEff_shamel() {
		return eff_shamel;
	}
	public void setEff_shamel(String eff_shamel) {
		this.eff_shamel = eff_shamel;
	}
	public Long getAffect_illness() {
		return affect_illness;
	}
	public void setAffect_illness(Long affect_illness) {
		this.affect_illness = affect_illness;
	}
	/*public String getAnualmarg_is_ratio() {
		return anualmarg_is_ratio;
	}
	public void setAnualmarg_is_ratio(String anualmarg_is_ratio) {
		this.anualmarg_is_ratio = anualmarg_is_ratio;
	}*/
	public String getEff_net() {
		return eff_net;
	}
	public void setEff_net(String eff_net) {
		this.eff_net = eff_net;
	}
	
	public Boolean getSecured() {
		return secured;
	}
	public void setSecured(Boolean secured) {
		this.secured = secured;
	}
	
	public Long getEff_apply_rank() {
		return eff_apply_rank;
	}
	public void setEff_apply_rank(Long eff_apply_rank) {
		this.eff_apply_rank = eff_apply_rank;
	}
	public String getIs_temp() {
		return is_temp;
	}
	public void setIs_temp(String is_temp) {
		this.is_temp = is_temp;
	}
	public Double getDefault_value() {
		return default_value;
	}
	public void setDefault_value(Double default_value) {
		this.default_value = default_value;
	}
	/*public Long getBoth_pos() {
		return both_pos;
	}
	public void setBoth_pos(Long both_pos) {
		this.both_pos = both_pos;
	}*/
	public Double getRound_value() {
		return round_value;
	}
	public void setRound_value(Double round_value) {
		this.round_value = round_value;
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
	public HREffectDiscDays getEffdisc_days() {
		return effdisc_days;
	}
	public void setEffdisc_days(HREffectDiscDays effdisc_days) {
		this.effdisc_days = effdisc_days;
	}
	public HREffectApplyType getEff_apply_type() {
		return eff_apply_type;
	}
	public void setEff_apply_type(HREffectApplyType eff_apply_type) {
		this.eff_apply_type = eff_apply_type;
	}
	public HREffectRoundType getRound_type() {
		return round_type;
	}
	public void setRound_type(HREffectRoundType round_type) {
		this.round_type = round_type;
	}
	
	
	

}
