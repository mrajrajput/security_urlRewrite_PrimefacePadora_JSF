package com.rajputLife.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "Profile")
@Getter
@Setter
//@ToString
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pkProfileId", nullable = false)
	public int pkProfileId;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="fkPersonId")
//	private Person person; // 1 person to 1 Profile.

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="fkAddressId")
//	private Address address; // 1 Person has 1 Address

	@Column(name = "isBride", nullable = false)
	public boolean isBride;

	@Column(name = "preference")
	public String preference;

	@Column(name = "mobileNumber", nullable = false)
	public String mobileNumber;

	@Column(name = "bloodGroup")
	public String bloodGroup;

	@Column(name = "historyOfDisease")
	public String historyOfDisease;

	/*  if Divorced - start  */
	@Column(name="isDivorcee")
	public boolean isDivorcee;

	@Column(name="divorcedDate")
	public Calendar divorcedDate;

	@Column(name="marriageDate")
	public Calendar marriageDate;
	/*  if Divorced - end  */



	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fkAddressId")
	private Address address;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fkPersonId")
	private Person person;

}
