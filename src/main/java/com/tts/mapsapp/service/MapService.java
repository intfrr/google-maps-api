package com.tts.mapsapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tts.mapsapp.model.GeocodingResponse;
import com.tts.mapsapp.model.Location;

@Service
public class MapService {

	@Value("${api_key}")
	private String apiKey;

	public Location addCoordinates(Location location) {
		
		System.out.println("api_key: " + apiKey);
		
		
		
		String url =
			"https://maps.googleapis.com/maps/api/geocode/json?address=" + 
			((location.getStreet_number() == null || location.getStreet_number() == 0  ) 
				? "" : (location.getStreet_number() + "+") ) +
			((location.getRoute() == null || location.getRoute() == "") 
				? "" : (location.getRoute())+ "," ) + 
			((location.getLocality() == null || location.getLocality() == "")
				? "" : (location.getLocality() + ",") ) +
			((location.getAdministrative_area_level_2() == null || 
			location.getAdministrative_area_level_2() == "")
				? "" : (location.getAdministrative_area_level_2() + ",") ) +
			((location.getAdministrative_area_level_1() == null || 
			location.getAdministrative_area_level_1() == "")
				? "" : (location.getAdministrative_area_level_1() + ",") ) +
			((location.getCountry() == null || location.getCountry() == "")
				? "" : ("&components=" + "country:" + location.getCountry() + "|" ) ) +
			((location.getPostal_code() == null || location.getPostal_code() == 0 )
				? "" : ("postal_code:" + location.getPostal_code()))
			+"&key=" + apiKey;
		
		// Example https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
		// Example https://maps.googleapis.com/maps/api/geocode/json?address=Puebla,Mexico&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
		System.out.println("url: " + url);
		
		
		RestTemplate restTemplate = new RestTemplate();
		GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class); 
		Location coordinates = response.getResults().get(0).getGeometry().getLocation();
		
		location.setLat(coordinates.getLat());
		location.setLng(coordinates.getLng()); 
		
		System.out.println("lat result: " + coordinates.getLat());
		System.out.println("long result: " + coordinates.getLng());
		
		return location;
	}

}
