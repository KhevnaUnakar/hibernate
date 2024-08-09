package com.jpa.jpaexample;

import java.util.*;

import jakarta.persistence.*;

@Entity
public class Trainer {

	@Id
	private int trainid;
	@Column
	private String trainname;
	
	@OneToMany(mappedBy = "trainer", cascade = CascadeType.PERSIST)
	private List<Trainee> traineeList;

	

	public int getTrainid() {
		return trainid;
	}

	public void setTrainid(int trainid) {
		this.trainid = trainid;
	}

	public String getTrainname() {
		return trainname;
	}

	public void setTrainname(String trainname) {
		this.trainname = trainname;
	}

	public List<Trainee> getTrList() {
		return traineeList;
	}

	public List<Trainee> getTraineeList() {
		return traineeList;
	}

	public void setTraineeList(List<Trainee> traineeList) {
		this.traineeList = traineeList;
	}

	
	
	
}
