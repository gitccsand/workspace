package com.http.testmemcache;

import java.io.Serializable;

public class UserInfo implements Serializable{
	private String userid;
	private String buyitem;
//	private static final long serialVersionUID = 217251451801586161L;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBuyitem() {
		return buyitem;
	}
	public void setBuyitem(String buyitem) {
		this.buyitem = buyitem;
	}
	
}
