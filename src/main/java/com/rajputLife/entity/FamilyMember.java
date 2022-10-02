package com.rajputLife.entity;

import lombok.Getter;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "FamilyMember")
public class FamilyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pkFamilyMemberId", nullable = false)
    private int pkFamilyMemberId;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "fkPersonId")
//    public Person person;

    @Column(name = "type", nullable = true)
    public String type; //Father, mother, sister, brother.

    @Column(name="fullName", nullable = true)
    public String fullName;

    @Column(name="gotra", nullable = true)
    public String gotra;

    @Column(name="village", nullable = true)
    public String village;

    @Column(name="isMarried", nullable = true)
    public boolean isMarried;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fkPersonId")
	private Person person;

//we will below fields nullable even though they are coming from dropdown.

//    @Column(name="marriageDate", nullable = true)
//    public Calendar marriageDate;
//
//    @Column(name="isDivorced", nullable = true)
//    public boolean isDivorce;
//
//    @Column(name="divorcedDate", nullable = true)
//    public Calendar divorcedDate;



}
