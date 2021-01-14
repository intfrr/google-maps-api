
let map;

function initMap() {

	var input = document.getElementById('routeAndStreetNumber');
	var autocomplete = new google.maps.places.Autocomplete(input);
	google.maps.event.addListener(autocomplete, 'place_changed', function() {
		var place = autocomplete.getPlace();

		pl = place;
		console.log('The place is');
		console.log(place);

		console.log('The pl is');
		console.log(pl);
		

		document.getElementById('latitude_view').innerHTML = place.geometry.location.lat();
		document.getElementById('longitude_view').innerHTML = place.geometry.location.lng();
		
		//Tonalá 261, Roma Sur, Mexico City, CDMX, Mexico
		
		let street_number_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('street_number');
				
		console.log('street_number_index: ' + street_number_index);

		let route_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('route');
		console.log('route_index: ' + route_index);

		let locality_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('locality');

		console.log('locality_index: ' + locality_index);

		let administrative_area_level_2_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('administrative_area_level_2');

		console.log('administrative_area_level_2_index: ' + administrative_area_level_2_index);

		let administrative_area_level_1_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('administrative_area_level_1');

		console.log('administrative_area_level_1_index: ' + administrative_area_level_1_index);

		let country_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('country');
				
		console.log('country_index: ' + country_index);
		
		let postal_code_index = 
			place.address_components.map(
				function(e) { 
					return e.types[0]; 
				}).indexOf('postal_code');
		
		console.log('postal_code_index: ' + postal_code_index);

		
		if(	(street_number_index >= 0 && street_number_index != null &&
				route_index >= 0 && route_index != null) &&
				place.address_components[street_number_index].long_name != null &&
				place.address_components[route_index].long_name != null
			)
			document.getElementById('routeAndStreetNumber').value = 
				place.address_components[route_index].long_name + '+' +
				place.address_components[street_number_index].long_name;
		else
			document.getElementById('routeAndStreetNumber').value = '';


		if(	(locality_index >= 0 && locality_index != null) &&
				place.address_components[locality_index].long_name != null
			)
			document.getElementById('locality').value = place.address_components[locality_index].long_name;
		else
			document.getElementById('locality').value = '';

		
			
		if(	(administrative_area_level_2_index >= 0 && administrative_area_level_2_index != null) &&
				place.address_components[administrative_area_level_2_index].long_name != null
			)
			document.getElementById('administrative_area_level_2').value = place.address_components[administrative_area_level_2_index].long_name;
		else
			document.getElementById('administrative_area_level_2').value = '';


		if(	(administrative_area_level_1_index >= 0 && administrative_area_level_1_index != null) &&
				place.address_components[administrative_area_level_1_index].long_name != null
			)
			document.getElementById('administrative_area_level_1').value = place.address_components[administrative_area_level_1_index].long_name;
		else
			document.getElementById('administrative_area_level_1').value = '';
			

		if(	(country_index >= 0 && country_index != null) &&
				place.address_components[country_index].long_name != null)
			document.getElementById('country').value = place.address_components[country_index].long_name;
		else
			document.getElementById('country').value = '';

			
		if(	(postal_code_index >= 0 && postal_code_index != null) &&
				place.address_components[postal_code_index].long_name != null
			)
			document.getElementById('postal_code').value = place.address_components[postal_code_index].long_name;
		else
			document.getElementById('postal_code').value = '';
		
		
	});
	

	map = new google.maps.Map(document.getElementById('map'), {
		center: coords,
		zoom: 10,
	});

	let image = {
		url: "/building.png",
		scaledSize: new google.maps.Size(50, 50)
	};


	let marker = new google.maps.Marker({
		position: coords,
		map: map,
		icon: image,
		animation: google.maps.Animation.BOUNCE,
	});

	let contentString = '<h2>' + ct + ', ' + st + '</h2>';

	let infowindow = new google.maps.InfoWindow({
		content: contentString,
	});

	google.maps.event.addListener(marker, 'click', function() {
		infowindow.open(map, marker);
	});
}

//google.maps.event.addDomListener(window, 'load', initialize);