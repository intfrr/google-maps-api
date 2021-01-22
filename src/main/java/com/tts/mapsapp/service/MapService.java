package com.tts.mapsapp.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.PlacesSearchResult;
import com.tts.mapsapp.model.AutocompleteLocation;
import com.tts.mapsapp.model.GeocodingResponse;
import com.tts.mapsapp.model.Location;

@Service
public class MapService {

	@Value("${api_key}")
	private String apiKey;

	public Location addCoordinates(Location location) {

		System.out.println("api_key: " + apiKey);

		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
				+ ((location.getStreet_number() == null || location.getStreet_number() == 0) ? ""
						: (location.getStreet_number() + "+"))
				+ ((location.getRoute() == null || location.getRoute() == "") ? "" : (location.getRoute()) + ",")
				+ ((location.getLocality() == null || location.getLocality() == "") ? "" : (location.getLocality() + ","))
				+ ((location.getAdministrative_area_level_2() == null || location.getAdministrative_area_level_2() == "") ? ""
						: (location.getAdministrative_area_level_2() + ","))
				+ ((location.getAdministrative_area_level_1() == null || location.getAdministrative_area_level_1() == "") ? ""
						: (location.getAdministrative_area_level_1() + ","))
				+ ((location.getCountry() == null || location.getCountry() == "") ? ""
						: ("&components=" + "country:" + location.getCountry() + "|"))
				+ ((location.getPostal_code() == null || location.getPostal_code() == 0) ? ""
						: ("postal_code:" + location.getPostal_code()))
				+ "&key=" + apiKey;

		// Example
		// https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
		// Example
		// https://maps.googleapis.com/maps/api/geocode/json?address=Puebla,Mexico&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
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

	public AutocompleteLocation addAutocompleteCoordinates(AutocompleteLocation autocompleteLocation) {
		
//		System.out.println("api_key: " + apiKey);
		
		String routeAndStreetNumber = 
			((autocompleteLocation.getRouteAndStreetNumber() == null || autocompleteLocation.getRouteAndStreetNumber() == "") 
				? "" : (autocompleteLocation.getRouteAndStreetNumber())+ "," ) ;
		
		String locality = 			
			((autocompleteLocation.getLocality() == null || autocompleteLocation.getLocality() == "")
				? "" : (autocompleteLocation.getLocality() + ",") );
		
		String administrativeAreaLevel2 = 			
			((autocompleteLocation.getAdministrative_area_level_2() == null || 
				autocompleteLocation.getAdministrative_area_level_2() == "")
			? "" : (autocompleteLocation.getAdministrative_area_level_2() + ",") ); 
		
		String administrativeAreaLevel1 = 
			((autocompleteLocation.getAdministrative_area_level_1() == null || 
				autocompleteLocation.getAdministrative_area_level_1() == "")
			? "" : (autocompleteLocation.getAdministrative_area_level_1() + ",") );
		
		String country = 
			((autocompleteLocation.getCountry() == null || autocompleteLocation.getCountry() == "")
						? "" : ("&components=" + "country:" + autocompleteLocation.getCountry() + "|" ) );
		
		String postalCode = 
				(
						(autocompleteLocation.getPostal_code() == null || autocompleteLocation.getPostal_code() == "" )
						? "" : 
							(autocompleteLocation.getCountry() == null || autocompleteLocation.getCountry() == "") 
							? ("&components=" + "postal_code:" + autocompleteLocation.getPostal_code())
									: ("postal_code:" + autocompleteLocation.getPostal_code())
					);

		
		String urlComplete =
			"https://maps.googleapis.com/maps/api/geocode/json?address=" + 
			routeAndStreetNumber +
			locality +
			administrativeAreaLevel2 +
			administrativeAreaLevel1 +
			country +
			postalCode +
			"&key=" + apiKey;
		
		// Example https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&sensor=false&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
		// Example https://maps.googleapis.com/maps/api/geocode/json?address=Puebla,Mexico&key=AIzaSyCTWAOF_H060Hi9GKYHvRApqrWPrOCjGI0
		System.out.println("urlComplete: " + urlComplete);
		
		
		RestTemplate restTemplate = new RestTemplate();
		GeocodingResponse response = restTemplate.getForObject(urlComplete, GeocodingResponse.class);
		
		
		GeoApiContext context = new GeoApiContext.Builder()
		    .apiKey(apiKey)
		    .build();
		
		try {
			GeocodingResult[] results  = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			System.out.println("Gson: " + gson.toJson(results[0].addressComponents));

		} catch (ApiException | InterruptedException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		try {
			FindPlaceFromText responseCandidates = PlacesApi.findPlaceFromText(context, "Tonala 261 Roma", FindPlaceFromTextRequest.InputType.TEXT_QUERY)
			    .fields(
			        FindPlaceFromTextRequest.FieldMask.BUSINESS_STATUS,
			        FindPlaceFromTextRequest.FieldMask.PHOTOS,
			        FindPlaceFromTextRequest.FieldMask.FORMATTED_ADDRESS,
			        FindPlaceFromTextRequest.FieldMask.NAME,
			        FindPlaceFromTextRequest.FieldMask.RATING,
			        FindPlaceFromTextRequest.FieldMask.OPENING_HOURS,
			        FindPlaceFromTextRequest.FieldMask.GEOMETRY)
			    .await();
			
			PlacesSearchResult candidate = responseCandidates.candidates[0];
			
			System.out.println("responseCandidates: " + responseCandidates);	
			System.out.println("candidate: " + candidate);
			
		} catch (ApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		
		
		Location coordinates = new Location();
		
		try {
			coordinates = response.getResults().get(0).getGeometry().getLocation();

		} catch (Exception e){
			
			coordinates.setLat("37.4215301");
			coordinates.setLng("-122.0892895");
			
			System.out.println("No results complete url" + response.getResults());
			
			System.out.println("Error on complete url: " + e);
			
			try {
				
				String urlIncomplete =
						"https://maps.googleapis.com/maps/api/geocode/json?address=" + 
						routeAndStreetNumber +
						locality +
						administrativeAreaLevel1 +
						country +
						postalCode +
						"&key=" + apiKey;
				
				System.out.println("urlInComplete: " + urlIncomplete);
				
				restTemplate = new RestTemplate();
				response = restTemplate.getForObject(urlComplete, GeocodingResponse.class);
				
				
				coordinates = response.getResults().get(0).getGeometry().getLocation();
				
			} catch (Exception ex) {
				coordinates.setLat("37.4215301");
				coordinates.setLng("-122.0892895");
				
				System.out.println("No results incomplete url" + response.getResults());
				
				System.out.println("Error on incomplete url: " + ex);
			}			
			

		} finally {
			autocompleteLocation.setLat(coordinates.getLat());
			autocompleteLocation.setLng(coordinates.getLng());
		}
				
		return autocompleteLocation;
	}


}
