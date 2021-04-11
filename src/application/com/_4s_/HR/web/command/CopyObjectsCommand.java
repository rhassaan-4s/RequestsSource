package com._4s_.HR.web.command;

import com._4s_.HR.model.HRGeographicalDivision;
import com._4s_.HR.model.HRInternalDivision;
import com._4s_.HR.model.HRQualificationDivision;
import com._4s_.HR.model.HRSpecialtyDivision;

public class CopyObjectsCommand {
	
	private HRInternalDivision internalDivision;
	private HRGeographicalDivision geographicalDivision;
	private HRQualificationDivision qualificationDivision;
	private HRSpecialtyDivision specialtyDivision;
	
	
	public HRInternalDivision getInternalDivision() {
		return internalDivision;
	}
	public void setInternalDivision(HRInternalDivision internalDivision) {
		this.internalDivision = internalDivision;
	}
	public HRGeographicalDivision getGeographicalDivision() {
		return geographicalDivision;
	}
	public void setGeographicalDivision(HRGeographicalDivision geographicalDivision) {
		this.geographicalDivision = geographicalDivision;
	}
	public HRQualificationDivision getQualificationDivision() {
		return qualificationDivision;
	}
	public void setQualificationDivision(
			HRQualificationDivision qualificationDivision) {
		this.qualificationDivision = qualificationDivision;
	}
	public HRSpecialtyDivision getSpecialtyDivision() {
		return specialtyDivision;
	}
	public void setSpecialtyDivision(HRSpecialtyDivision specialtyDivision) {
		this.specialtyDivision = specialtyDivision;
	}

}
