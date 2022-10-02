package com.rajputLife.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "Person")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pkPersonId", nullable = false)
	private int pkPersonId;

//	@OneToOne(mappedBy = "person")//, cascade = CascadeType.ALL)
//	public Profile profile; // 1 person has 1 profile.

//	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
//	public Set<FamilyMember> familyMembers; // 1 person to Many FamilyMember


	public Person(){
		familyMembers = new ArrayList<>();
	}

	@Column(name="firstName")
	public String firstName;
	@Column(name="lastName")
	public String lastName;

	@Column(name="dob", nullable = true)
	public String dob; //Put calendar here
	@Column(name="timeOfBirth", nullable = true)
	public String timeOfBirth; //Put Codes here
	@Column(name="placeOfBirth", nullable = true)
	public String placeOfBirth;

	@Column(name="isManglik", nullable = true) //take from dropdown
	public boolean isManglik;

	@Column(name="height", nullable = true)
	public String height; //Put Codes here
	@Column(name="weight", nullable = true)
	public String weight; //Put Codes here
	@Column(name="complexion", nullable = true)
	public String complexion; //Put Codes here.

	@Column(name="highestEducation", nullable = true)
	public String highestEducation;
	@Column(name="whatYearDegreeReceived", nullable = true)
	public int whatYearDegreeReceived;

	@Column(name="languagesKnown", nullable = true)
	public String languagesKnown; //how to make it many?

	@Column(name="employmentType", nullable = true)
	public String employmentType; //Service, Business, Agriculture.
	@Column(name="salaryOrApproxIncome", nullable = true)
	public String salaryOrApproxIncome;
	@Column(name="workExperience", nullable = true)
	public String workExperience;



	@OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	private Profile profile;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FamilyMember> familyMembers = new ArrayList<>();


}
