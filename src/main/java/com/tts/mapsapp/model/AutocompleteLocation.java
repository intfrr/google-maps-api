package com.tts.mapsapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class AutocompleteLocation {
	
	private String lat;
	private String lng;
		
	// street
	private String routeAndStreetNumber;
	
	// city
	private String locality;
	
	// county , municipio, condado
	private String administrative_area_level_2;
	
	//state
	private String administrative_area_level_1;
	
	// country
	private String country;
	
	//CP
	private String postal_code;
	
	private String complete_address;
	
	public AutocompleteLocation() {
		
		this.routeAndStreetNumber = "";
		this.locality = "";
		this.administrative_area_level_2 = "";
		this.administrative_area_level_1 = "";
		this.country = "";
		this.postal_code = "";
	}
	

}
