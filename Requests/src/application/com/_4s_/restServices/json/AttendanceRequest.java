package com._4s_.restServices.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "Student")
public class AttendanceRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6480805488676444857L;

	//1:Sign in(10) //2:Sign out(11) //3:Permission Start (3)//4:Permission End (3)//5:Full Day Persmission Start (1-999)//6:Full Day Permission End (1-999)
	//7:Special Vacation-e3teyadi(1-001) Special Vacation-3arda(1-002) Special Vacation-marady(1-003) Special Vacation-ra7et wardeya(1-888) 
	//8:Periodic Vacation(2)
	private Long attendanceType; 

	private String vacation;
	
    private String attendanceTime;
    
    //to date for vacations
    private String attendanceTime2;
    
    private Double latitude;
    
    private Double longitude;

    private String notes;
    
//    private String permissionType;//0: Special 1:Errand 2:Normal
    
    private String permissionEffect;//0: Without Effect(depricated) 1: Delay 2: Early Dismissal
    
    
	public String getVacation() {
		return vacation;
	}

	public void setVacation(String vacation) {
		this.vacation = vacation;
	}

	public String getAttendanceTime2() {
		return attendanceTime2;
	}

	public void setAttendanceTime2(String attendanceTime2) {
		this.attendanceTime2 = attendanceTime2;
	}

//	public String getPermissionType() {
//		return permissionType;
//	}
//
//	public void setPermissionType(String permissionType) {
//		this.permissionType = permissionType;
//	}

	public String getPermissionEffect() {
		return permissionEffect;
	}

	public void setPermissionEffect(String permissionEffect) {
		this.permissionEffect = permissionEffect;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public AttendanceRequest() {
		super();
	}

	public Long getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(Long attendanceType) {
		this.attendanceType = attendanceType;
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
