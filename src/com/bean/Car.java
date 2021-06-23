package com.bean;
/**
 * 表列属性类
 * @author 林沐
 *
 */
public class Car {
	private int id;
	private String cname;
	private String cnumber;
	private String ccolor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public String getCcolor() {
		return ccolor;
	}
	public void setCcolor(String ccolor) {
		this.ccolor = ccolor;
	}
	public Car(int id, String cname, String cnumber, String ccolor) {
		this.id = id;
		this.cname = cname;
		this.cnumber = cnumber;
		this.ccolor = ccolor;
	}
	
}
