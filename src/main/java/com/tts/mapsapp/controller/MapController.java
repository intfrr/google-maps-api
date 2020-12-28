package com.tts.mapsapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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
		
//		model.addAttribute(new Location());
		return "index.html";
	}
	
	@GetMapping("/home")
	public ModelAndView getDefaultMap(Map<String, Object> model) {
		Location location = new Location(); 
		location.setCity("Puebla");
		location.setState("Mexico");

		System.out.println("City: " + location.getCity()); 
		System.out.println("State: " + location.getState());

		mapService.addCoordinates(location);
		
		System.out.println("Lat: " + location.getLat()); 
		System.out.println("Long: " + location.getLng());

		model.put("location", location);
		
//		model.addAttribute(new Location());
		return new ModelAndView("index.html");
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
	

}
