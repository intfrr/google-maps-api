package com.tts.mapsapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tts.mapsapp.model.AutocompleteLocation;
import com.tts.mapsapp.model.Location;
import com.tts.mapsapp.service.MapService;

@Controller
public class MapController {
	
	@Autowired
	MapService mapService; 
	
	@GetMapping("/home-old")
	public String getDefaultMapOld(Model model) {
		Location location = new Location(); 
		location.setCity("Puebla");
		location.setState("Mexico");

		System.out.println("City: " + location.getCity()); 
		System.out.println("State: " + location.getState());

		mapService.addCoordinates(location);
		
		System.out.println("Lat: " + location.getLat()); 
		System.out.println("Long: " + location.getLng());

		
		model.addAttribute(location);
		
		return "index.html";
	}
	
	@GetMapping("/home")
	public ModelAndView getDefaultMap(Map<String, Object> model) {
		Location location = new Location(); 
		location.setCity("Puebla");
		location.setState("Mexico");

		location.setStreet_number(1600);
		location.setRoute("Amphitheatre+Parkway");
		location.setLocality("Mountain+View");
		location.setAdministrative_area_level_2("Santa Clara County");
		location.setAdministrative_area_level_1("California");
		location.setCountry("United States");
		location.setPostal_code(94043);
		
		System.out.println("City: " + location.getCity()); 
		System.out.println("State: " + location.getState());

		
		
		mapService.addCoordinates(location);
		
		System.out.println("Lat: " + location.getLat()); 
		System.out.println("Long: " + location.getLng());

		model.put("location", location);
		
		return new ModelAndView("index.html");
	}	

	@GetMapping("/home-autocomplete")
	public ModelAndView getAutocompleteMap(Map<String, Object> model) {
		AutocompleteLocation autocompleteLocation = new AutocompleteLocation(); 

		autocompleteLocation.setRouteAndStreetNumber("Amphitheatre+Parkway+1600");
		autocompleteLocation.setLocality("Mountain+View");
		autocompleteLocation.setAdministrative_area_level_2("Santa Clara County");
		autocompleteLocation.setAdministrative_area_level_1("California");
		autocompleteLocation.setCountry("United States");
		autocompleteLocation.setPostal_code("94043");
		
		
		autocompleteLocation = mapService.addAutocompleteCoordinates(autocompleteLocation);
		
		System.out.println("Lat Get: " + autocompleteLocation.getLat()); 
		System.out.println("Long Get: " + autocompleteLocation.getLng());

		model.put("autocompletelocation", autocompleteLocation);
		

		return new ModelAndView("indexAutocomplete.html");
	}
	
	@PostMapping("/home-old")
	public String getMapForLocationOld(Location location, Model model) {
		mapService.addCoordinates(location);
		model.addAttribute(location);
		return "index.html"; 
	}	
	
	@PostMapping("/home")
	public String getMapForLocation(Location location, Model model) {
		mapService.addCoordinates(location);
		model.addAttribute(location);
		return "index.html"; 
	}

	@PostMapping("/home-autocomplete")
	public String getMapForLocation(AutocompleteLocation autocompleteLocation, Model model) {
		autocompleteLocation = mapService.addAutocompleteCoordinates(autocompleteLocation);
		
		System.out.println("Lat Post: " + autocompleteLocation.getLat()); 
		System.out.println("Long Post: " + autocompleteLocation.getLng());
		
		model.addAttribute("autocompletelocation", autocompleteLocation);
		
		return "indexAutocomplete.html"; 
	}

	

}
