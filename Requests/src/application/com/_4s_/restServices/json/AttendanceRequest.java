package com._4s_.restServices.json;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "Student")
public class AttendanceRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6480805488676444857L;

	private Long attendanceType; //1:Sign in 2: Sign out

    private Long employeeId;

    private String attendanceTime;
    
    private Double latitude;
    
    private Double longitude;

    
	public AttendanceRequest() {
		super();
	}

	public Long getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(Long attendanceType) {
		this.attendanceType = attendanceType;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getAttendanceTime() {
		return attendanceTime;
	}

	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    
}
