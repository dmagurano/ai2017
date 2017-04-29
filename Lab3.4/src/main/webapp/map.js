
	var mymap;
	var polylines = [];
	var markers = [];
	//var pointList = []
	var min_lat, max_lat, min_lng, max_lng;
	var srcLat, srcLon, dstLat, dstLon;
	var selectingSrc = true;
	var stringSrc = 'Click on the map to select source point';
	var stringDst = 'Click on the map to select destination point';
	var stringDone = 'Press Find Path to continue or cancel to restart';
	
	function onMapClick(e){
		if (selectingSrc) {
			srcLat = e.latlng.lat;
			srcLon = e.latlng.lon;
			selectingSrc = false;
			$('#statusText').html(stringDst);
		} else {
			dstLat = e.latlng.lat;
			dstLon = e.latlng.lon;
			$('#statusText').html(stringDone);
		}
	}
	
	function insertEdge(edge) {
		var polyline;
		var srcPoint = new L.LatLng(edge.latSrc, edge.lonSrc);
		var dstPoint = new L.LatLng(edge.latDst, edge.lonDst);
		//console.log(requestedDirection);
		var pointList = [];
		pointList.push(srcPoint);
		pointList.push(dstPoint);
		
		if(edge.mode) {
			// a piedi
			polyline = new L.Polyline(pointList, {
							    color: 'blue',
							    weight: 5,
							    opacity: 0.5,
							    smoothFactor: 1,
								dashArray : "5, 10"
							});
		} else {
			polyline = new L.Polyline(pointList, {
							    color: 'orange',
							    weight: 5,
							    opacity: 0.5,
							    smoothFactor: 1
							});
			
		}
		
		polyline.addTo(mymap);
		mymap.addLayer(polyline);
		polylines.push(polyline);
		
	}
	
	function clickOnMarker(e, stopName, stopId){
		var popup = e.target.getPopup();		
		//console.log("clicked on bus stop: " + stopId);
		
		$.ajax({
			url : '/Lab2/StopRequest',
			type : 'GET',
			data : {
				'selected_stop' : stopId,
			},
			dataType : 'json',

			success : function(data, textStatus, jqXHR) {
				//our country code was correct so we have some information to display
				if (data.length > 0) {
					//console.log("success");
					
					console.log(data);
					var popupContent = "<b>" + stopId + " " + stopName + "<b><br>Other lines:<br>";
					$.each(data, function(k,busLine) {
						//console.log(busLine.line);
						popupContent = popupContent.concat(busLine.line + "<br>");
					});
					
					//console.log("content: " + popupContent);
					
					popup.setContent( popupContent );
                    popup.update();

				}
				//display error message
				else {
					console.log("error retrieving data from server");
				}
			},

			//If there was no resonse from the server
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("Something really bad happened " + textStatus);
			}
		});
	}
	
	function updateMap(busStop){
		var marker = L.marker([busStop.lat, busStop.lng]).addTo(mymap)
			.bindPopup() // in order to modify popup content
			.on("click", function(e){
				clickOnMarker(e, busStop.name, busStop.id)
		});
	
		markers.push(marker);
		mymap.addLayer(marker);
	}
	
	function insertMaker(busStop){
		var curPoint = new L.LatLng(busStop.lat, busStop.lng);
		//console.log(requestedDirection);
		
		pointList.push(curPoint);
		updateMap(busStop);
		
		if (busStop.lat < min_lat)
			min_lat = busStop.lat;
		if (busStop.lat > max_lat)
			max_lat = busStop.lat;								
		if (busStop.lng < min_lng)
			min_lng = busStop.lng;
		if (busStop.lng > max_lng)
			max_lng = busStop.lng;
	}
		
	$(function() {
		
		$('#statusText').html(stringSrc);
		
		// initializing map
		mymap = L.map('mapid').setView([45.07, 7.69], 13);
		L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og').addTo(mymap);
		
		mymap.on('click', onMapClick);
		
		$('#cancelBtn').click(function() {
			selectingSrc = true;
			// clearing map from markers
		for (i=0;i<markers.length;i++)
			mymap.removeLayer(markers[i]);
		// clearing map from lines
		if (typeof polyline !== 'undefined')
			mymap.removeLayer(polyline);
		} );
		
		$('#findBtn').click(function() {
			$.ajax({
					url : '/Lab3.4/RoutingRequest',
					type : 'GET',
					data : 
					{
						'src' : { 
								  'lat' : srcLat ,
								  'lon' : srcLon 
								},
						'dst' : { 
								  'lat' : dstLat,
								  'lon' : dstLon
								}		  
					},
					dataType : 'json',
	
					success : function(data, textStatus, jqXHR) {
						//our country code was correct so we have some information to display
						if (data.length > 0) {
							//console.log("success");

							// clearing map from markers
							for (i=0;i<markers.length;i++)
								mymap.removeLayer(markers[i]);
							// clearing map from lines
							for (i=0;i<polylines.length;i++){
							if (typeof polyline !== 'undefined')
								mymap.removeLayer(polylines[i]);
							}
							pointList= [];
							min_lat = 90; max_lat = -90; min_lng = 180; max_lng = -180;
							
							// inserting src & dst markers
							var edgesSize = data.length;
							var busStop = new Object();
							busStop.lat = data[0].latSrc;
							busStop.lng = data[0].lonSrc;
							insertMaker(busStop);
							busStop.lat = data[edgesSize-1].latDst;
							busStop.lng = data[edgesSize-1].lonDst;
							insertMaker(busStop);
							
							// inserting edges
							
							$.each(data, 
								function(k,edge) {
									insertEdge(edge);
								}
							);
							
							// centering map
							mymap.fitBounds([
									[max_lat, max_lng],
									[min_lat, min_lng]
							]);
							
							

							
							
						}
						//display error message
						else {
							console.log("error");
						}
						
						// showing direction buttons
						//document.getElementById("direction-buttons").style.visibility = "visible";
					},
	
					//If there was no resonse from the server
					error : function(jqXHR, textStatus, errorThrown) {
						console.log("Something really bad happened " + textStatus);
					}
				});
			});
	});
	