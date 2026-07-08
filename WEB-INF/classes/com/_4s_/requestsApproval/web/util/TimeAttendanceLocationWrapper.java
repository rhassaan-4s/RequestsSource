package com._4s_.requestsApproval.web.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class TimeAttendanceLocationWrapper {
	private Timestamp minDate;//date
	private Timestamp maxDate;//date
	private String emp;
	private String fName;//firstName
	private BigDecimal longitudeIn;
	private BigDecimal longitudeOut;
	private String latitudeIn;
	private BigDecimal latitudeOut;
	private BigDecimal addressIn;
	private String addressOut;
	
	
	public TimeAttendanceLocationWrapper(Object minDate, Object maxDate, Object emp,Object fName, Object longitudeIn, Object longitudeOut, Object latitudeIn, Object latitudeOut, Object addressIn, Object addressOut) {
		super();
		if (minDate.getClass().equals(String.class)) {
			DateFormat d= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			try {
				this.minDate = new Timestamp((d.parse((String)minDate)).getTime());
				this.maxDate = new Timestamp((d.parse((String)maxDate)).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			this.minDate = (Timestamp)minDate;
			this.maxDate = (Timestamp)maxDate;
		}
		
		this.emp = (String)emp;
		this.fName = (String) fName;
		if (longitudeIn!=null) {
			this.longitudeIn = (BigDecimal) longitudeIn;
		} else {
			this.longitudeIn = null;
		}
		if (longitudeOut != null) {
			this.longitudeOut = (BigDecimal) longitudeOut;
		} else {
			this.longitudeOut = null;
		}
		this.latitudeIn = (String) latitudeIn;
		if (latitudeOut != null) {
			this.latitudeOut = (BigDecimal) latitudeOut;
		} else {
			this.latitudeOut = null;
		}
		
		if (addressIn != null) {
			this.addressIn = (BigDecimal)addressIn;
		} else {
			this.addressIn = null;
		}
		this.addressOut = (String)addressOut;
	}

	public Timestamp getMinDate() {
		return minDate;
	}

	public void setMinDate(Timestamp minDate) {
		this.minDate = minDate;
	}

	public Timestamp getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Timestamp maxDate) {
		this.maxDate = maxDate;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public BigDecimal getLongitudeIn() {
		return longitudeIn;
	}

	public void setLongitudeIn(BigDecimal longitudeIn) {
		this.longitudeIn = longitudeIn;
	}

	public BigDecimal getLongitudeOut() {
		return longitudeOut;
	}

	public void setLongitudeOut(BigDecimal longitudeOut) {
		this.longitudeOut = longitudeOut;
	}

	public String getLatitudeIn() {
		return latitudeIn;
	}

	public void setLatitudeIn(String latitudeIn) {
		this.latitudeIn = latitudeIn;
	}

	public BigDecimal getLatitudeOut() {
		return latitudeOut;
	}

	public void setLatitudeOut(BigDecimal latitudeOut) {
		this.latitudeOut = latitudeOut;
	}

	public BigDecimal getAddressIn() {
		return addressIn;
	}

	public void setAddressIn(BigDecimal addressIn) {
		this.addressIn = addressIn;
	}

	public String getAddressOut() {
		return addressOut;
	}

	public void setAddressOut(String addressOut) {
		this.addressOut = addressOut;
	}
	
	
	
	
}
