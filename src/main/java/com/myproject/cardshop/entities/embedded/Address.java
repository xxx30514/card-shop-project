package com.myproject.cardshop.entities.embedded;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address implements Serializable{

	private static final long serialVersionUID = 1L;

	private String streetName;
	
	private String houseNumber;
	
	private String zipCode;
	
}
