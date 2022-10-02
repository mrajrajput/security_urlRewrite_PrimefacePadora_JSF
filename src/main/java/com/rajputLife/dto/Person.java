package com.rajputLife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {

    @NotNull
    @Length(min = 1, message = "Name should have length of at-least 1")
    public String firstName;

    @NotNull
    @Length(min = 0)
    public String lastName;

    public String dob; //Put calendar here

    public String timeOfBirth; //Put Codes here

    public String placeOfBirth;

    public boolean isManglik;

    public String height; //Put Codes here

    public String weight; //Put Codes here

    public String complexion; //Put Codes here.

    public String highestEducation;

    public int whatYearDegreeReceived;










}
