package com._4s_.restServices.model;

public class AttendanceStatus {
	public Boolean signIn;
	public Long signInTime;
	public Boolean signOut;
	public Long signOutTime;
	public Boolean getSignIn() {
		return signIn;
	}
	public void setSignIn(Boolean signIn) {
		this.signIn = signIn;
	}
	public Long getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(Long signInTime) {
		this.signInTime = signInTime;
	}
	public Boolean getSignOut() {
		return signOut;
	}
	public void setSignOut(Boolean signOut) {
		this.signOut = signOut;
	}
	public Long getSignOutTime() {
		return signOutTime;
	}
	public void setSignOutTime(Long signOutTime) {
		this.signOutTime = signOutTime;
	}
}
