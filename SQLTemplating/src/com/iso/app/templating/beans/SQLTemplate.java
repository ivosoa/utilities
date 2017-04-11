package com.iso.app.templating.beans;

/**
 * 
 * @author ivo
 */
public class SQLTemplate {
	private String name;
	private String value;
	private String testValue;
	
	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTestValue() {
		return testValue;
	}
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}
}
