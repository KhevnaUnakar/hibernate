package com.jpa.jpaexample;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Company 
{
	@Id
	private int c_id;
	@Column
	private String c_name;
	
	@OneToMany(mappedBy="company")
	private List<Emp> emplist;

	public List<Emp> getEmplist() 
	{
		return emplist;
	}

	public void setEmplist(List<Emp> emplist) 
	{
		this.emplist = emplist;
	}

	public int getC_id() 
	{
		return c_id;
	}
	
	public void setC_id(int c_id) 
	{
		this.c_id = c_id;
	}
	
	public String getC_name() 
	{
		return c_name;
	}
	
	public void setC_name(String c_name) 
	{
		this.c_name = c_name;
	}
	
	
}