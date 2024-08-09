package org.example;

import jakarta.persistence.*;

@Entity
public class Library {
	
	@Id
	private int b_id;
	@Column
	private String b_name;
	
	@OneToOne (cascade=CascadeType.PERSIST)
	@JoinColumn(name = "stid", referencedColumnName = "id")
	private Student student;
	
	public int getB_id() {
		return b_id;
	}
	public void setB_id(int b_id) {
		this.b_id = b_id;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

}
