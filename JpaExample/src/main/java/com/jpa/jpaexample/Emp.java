package com.jpa.jpaexample;

import jakarta.persistence.*;

@Entity
public class Emp 
{
	@Id
	private int e_id;
	@Column
	private String e_name;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "compid", referencedColumnName="c_id")
	private Company company;
	
	public Company getCompany() 
	{
		return company;
	}

	public void setCompany(Company company) 
	{
		this.company = company;
	}

	public int getE_id() 
	{
		return e_id;
	}
	
	public void setE_id(int e_id) 
	{
		this.e_id = e_id;
	}
	
	public String getE_name() 
	{
		return e_name;
	}
	
	public void setE_name(String e_name) 
	{
		this.e_name = e_name;
	}
	
	
}