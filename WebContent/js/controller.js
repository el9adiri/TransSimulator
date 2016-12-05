var map, stations, directionsService, directionsDisplay;
$(document).ready(
		function() {
			var resultLine = null;
			$("#btn").click(
					function() {
						var msg = $("#msg");
						var s = document.getElementById('s').value;
						var d = document.getElementById('d').value;
						if (!document.getElementsByName('choix')[0].checked) {
							d = document.getElementById('s').value;
							s = document.getElementById('d').value;
						}
						var sendAjax = $.ajax({
							type : "POST",
							url : 'SimulatorController',
							data : 's=' + s + '&d=' + d,
							dataType : 'json',
							success : function(data) {
								console.log(data);
								var coordinates = [];
								if (resultLine != null) {
									resultLine.setMap(null);
									$("#myResult").css("visibility","hidden");
								}
								if(data==null){
									msg.html("'"+s+"' vers '"+d+"'<br><small>impossible</small>");
									$('#notice').modal('show');
								}else{
									var table = [],i=data.length,tbody="",j=0,ligne=data[0].ligne;
									$.each(data, function(index, station) {
										i--;
										table.push('<tr><td>'+i+'</td><td>'+station.predecesseur+'<br><small>'+ station.ligne +'</small></td><td>'+station.id+'<br><small>'+ (ligne==station.ligne?'':'Changement') +'</small></td><td>'+station.arrived+'</td></tr>');
										coordinates.push(new google.maps.LatLng(
												station.designe.latitude,
												station.designe.longitude));
										ligne=station.ligne;
									});
									for(j=table.length-2;j>=0;j--)
										tbody += table[j];
									$("#result caption").text("Le plus court chemin à partir de "+data[data.length-1].arrived);
									$("#myResult").css("visibility","visible");
									$("#chemin").html(tbody);
									resultLine = newPolyline(map, coordinates,
											'#ff0000', 10, 1, .9);
									displayRoute(directionsService, directionsDisplay, coordinates[0],coordinates[coordinates.length-1]);
								}
								return;
							},
							error : function(request, status, error){
								alert(error);
							}
						
						});
					});
		});
function displayRoute(directionsService, directionsDisplay, to, from) {
	  directionsService.route({
	    origin: from,
	    destination: to,
	    travelMode: google.maps.TravelMode.DRIVING
	  }, function(response, status) {
	    if (status === google.maps.DirectionsStatus.OK) {
	      directionsDisplay.setDirections(response);
	    } else {
	      window.alert('Directions request failed due to ' + status);
	    }
	  });
}
function initialize() {
	map = new google.maps.Map(document.getElementById('map'), {
		center : new google.maps.LatLng(51.523129639184, -0.15688978273689),
		zoom : 12,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	});
	directionsService = new google.maps.DirectionsService;
	directionsDisplay = new google.maps.DirectionsRenderer;
	directionsDisplay.setMap(map);
	$.ajax({ // TEST
		type : "GET",
		url : 'SimulatorController',
		dataType : 'json',
		success : function(data) {
			var typeSet = [];
			console.log(data);
			$.each(data, function(index, ligne) {
				var successeurs;
				$.each(ligne.stations, function(index2, station) {
					typeSet.push(station.id);
					newMarker(map, station.id,
							new google.maps.LatLng(station.designe.latitude,
									station.designe.longitude), ligne.icon);
					var i;
					successeurs = station.successeurs;
					for (i = 0; i < successeurs.length; i++) {
						coordinates = [
								new google.maps.LatLng(
										station.designe.latitude,
										station.designe.longitude),
								new google.maps.LatLng(
										successeurs[i].designe.latitude,
										successeurs[i].designe.longitude) ];
						var polyline = newPolyline(map, coordinates, ligne.color, 5, ligne.color.length, .5);
						var mode = ligne.icon=='car'?'Privé':ligne.icon=='bus'?'Surface':ligne.icon=='train'?'Ferré':'Tramway';
						var info = 'lien : '+station.id+ ' - ' + successeurs[i].stationTo +
						'<br>ligne : '+ligne.id + 
						'<br>vitesse : ' + ligne.vitesse + 
						'<br>mode : ' + mode;
						createInfoWindow(polyline, info);
					}
				});
			});
			$("#s").data("source",typeSet);
			$("#d").data("source",typeSet);
		}
	});
}
function createInfoWindow(polyline,content) {
    google.maps.event.addListener(polyline, 'click', function(event) {
    	var infowindow = new google.maps.InfoWindow({
    	    content: content,
    	    position: event.latLng
    	  });
        infowindow.open(map);
    });
}
function newPolyline(map, coordinates, color, weight, zindex, opacity) {
	var polyline = new google.maps.Polyline({
		path : coordinates,
		strokeColor : color,
		strokeOpacity : opacity,
		strokeWeight : weight,
		zIndex : zindex,
	});
	polyline.setMap(map);
	return polyline;
}
function newMarker(map, name, latlng, icon) {
	var marker = new google.maps.Marker({
		title : name,
		name : name,
		position : latlng,
		map : map,
		icon : {
			url : 'images/' + icon + '.png',
			size : new google.maps.Size(15, 15),
			scaledSize : new google.maps.Size(15, 15)
		}
	});
	google.maps.event.addListener(marker, 'click', function(event) {
		// latLng: Object, pixelOffset: Object, featureData: Object
		console.log(marker);
		if (document.getElementsByName('choix')[0].checked) {
			document.getElementById('s').value = marker.name;
			document.getElementsByName('choix')[1].checked=true;
		} else {
			document.getElementById('d').value = marker.name;
			document.getElementsByName('choix')[0].checked=true;
		}
	});
}
google.maps.event.addDomListener(window, 'load', initialize);