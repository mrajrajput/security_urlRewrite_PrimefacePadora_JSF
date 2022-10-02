package com.rajputLife.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {
    @NotNull
    @Length(min = 2, message = "House Address should have length of at-least 2 character.")
    public String address1;
    public String address2;
    public String district;
    @NotNull
    @Length(min = 2, message = "Please enter State.")
    public String state;
    public String pinOrZipCode;
    @NotNull
    @Length(min = 2, message = "Please enter Country.")
    public String country;
}
