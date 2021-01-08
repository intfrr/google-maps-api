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
public class Location {
	
	private String city;
	private String state;
	private String lat;
	private String lng;
	
	// number
	private Integer street_number;
	
	// street
	private String route;
	
	// city
	private String locality;
	
	// county , municipio, condado
	private String administrative_area_level_2;
	
	//state
	private String administrative_area_level_1;
	
	// country
	private String country;
	
	//CP
	private Integer postal_code;
	
	public Location() {
		this.street_number = 0;
		this.route = "";
		this.locality = "";
		this.administrative_area_level_2 = "";
		this.administrative_area_level_1 = "";
		this.country = "";
		this.postal_code = 0;
	}
	
//	public String getCity() {
//		return city;
//	}
//	public void setCity(String city) {
//		this.city = city;
//	}
//	public String getState() {
//		return state;
//	}
//	public void setState(String state) {
//		this.state = state;
//	}
//	public String getLat() {
//		return lat;
//	}
//	public void setLat(String lat) {
//		this.lat = lat;
//	}
//	public String getLng() {
//		return lng;
//	}
//	public void setLng(String lng) {
//		this.lng = lng;
//	}
//	
//	
//	@Override
//	public String toString() {
//		return "Location [city=" + city + ", state=" + state + ", lat=" + lat + ", lng=" + lng + "]";
//	} 
	
	
	
	

}
