package com.jpa.jpaexample;

import jakarta.persistence.*;

@Entity
@Table (name="trainee")
public class Trainee {
	
	@Id
	private int eid;
	@Column
	private String ename;

	@ManyToOne
	@JoinColumn(name="trainerId", referencedColumnName = "trainid")
	private Trainer trainer;

	public int getEid() {
		return eid;
	}

	public void setEid(int eid) {
		this.eid = eid;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

}
