
	var mymap;
	var polylines = [];
	var markers = [];
	//var pointList = []
	var min_lat, max_lat, min_lng, max_lng;
	var srcLat, srcLon, dstLat, dstLon;
	var selectingSrc = true;
	var stringSrc = 'Click on the map to select start point';
	var stringDst = 'Click on the map to select destination point';
	var stringDone = 'Press Calculate to find the best path';
	var markerType;
	// https://github.com/pointhi/leaflet-color-markers
	var greenIcon = new L.Icon({
		  iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
		  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
		  iconSize: [25, 41],
		  iconAnchor: [12, 41],
		  popupAnchor: [1, -34],
		  shadowSize: [41, 41]
	});
	
	function onMapClick(e){
		//console.log("clicked on map");
		var marker;
		
		if (selectingSrc) {
			//console.log("selected source");
			srcLat = e.latlng.lat;
			srcLon = e.latlng.lng;
			selectingSrc = false;
			$('#statusText').html(stringDst);
			marker = L.marker([e.latlng.lat, e.latlng.lng]).addTo(mymap);
		} else {
			//console.log("selected destination");
			dstLat = e.latlng.lat;
			dstLon = e.latlng.lng;
			$('#statusText').html(stringDone);
			mymap.off('click', onMapClick);
			marker = L.marker([e.latlng.lat, e.latlng.lng], {icon: greenIcon}).addTo(mymap)
		}
		
		markers.push(marker);
		mymap.addLayer(marker);	
	}

	function insertMaker(point, type){
		//console.log("> inserting marker ...");
		var curPoint = new L.LatLng(point.lat, point.lng);
		var marker;
		
		pointList.push(curPoint);
		
		if (markerType === "src")
			marker = L.marker([point.lat, point.lng]).addTo(mymap)
		else
			marker = L.marker([point.lat, point.lng], {icon: greenIcon}).addTo(mymap)
		
		markers.push(marker);
		mymap.addLayer(marker);
		
		if (point.lat < min_lat)
			min_lat = point.lat;
		if (point.lat > max_lat)
			max_lat = point.lat;								
		if (point.lng < min_lng)
			min_lng = point.lng;
		if (point.lng > max_lng)
			max_lng = point.lng;
		
		//console.log("> marker inserted");
	}
	
	function insertEdge(edge) {
		//console.log(">> inserting edge ...");
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
		
		//console.log(">> edge inserted.");
	}
	
	function insertLine(edge){
		//Try to get tbody first with jquery children. works faster!
		var tbody = $('#path').children('tbody');

		//Then if no tbody just select your table 
		var table = tbody.length ? tbody : $('#path');
		
		var line = edge.mode ? 'walking' : edge.edgeLine;

		//Add row
		table.append('<tr><td>'+edge.cost+' m</td><td>'+line+'</td></tr>');
	}
	
	function insertLine(line, cost){
		//Try to get tbody first with jquery children. works faster!
		var tbody = $('#path').children('tbody');

		//Then if no tbody just select your table 
		var table = tbody.length ? tbody : $('#path');

		//Add row
		table.append('<tr><td>'+cost+' m</td><td>'+line+'</td></tr>');
	}
	
	function insertLines(data){
		// setting initial status
		var lastLine = data[0].mode ? 'walking' : data[0].edgeLine;
		var lastCost = data[0].cost;
		// number of bus stops on the same line
		// to be used if it is necessary
		var lastStopsCount = 0;
		
		var subData = data.slice(1, -1);
		$.each(subData, 
			function(k,edge) {
				var currentLine = edge.mode ? 'walking' : edge.edgeLine;
			
				if (currentLine === lastLine){
					// updating status
					lastCost += edge.cost;
					lastStopsCount++;
				}	
				else{
					// inserting row in table
					insertLine(lastLine, lastCost);
					
					// resetting status
					lastLine = currentLine;
					lastCost = edge.cost
					lastStopsCount = 0;
				}
			}
		);

		// inserting last row in table
		insertLine(lastLine, lastCost);
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

					
					var greenIcon = new L.Icon({
						  iconUrl: 'https://cdn.rawgit.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
						  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
						  iconSize: [25, 41],
						  iconAnchor: [12, 41],
						  popupAnchor: [1, -34],
						  shadowSize: [41, 41]
					});
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
		
	$(function() {
		
		$('#statusText').html(stringSrc);
		$('#path-info').hide();
		
		// initializing map
		mymap = L.map('mapid').setView([45.07, 7.69], 13);
		L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og').addTo(mymap);
		
		mymap.on('click', onMapClick);
		
		$('#cancelBtn').click(function() {
			$('#statusText').html(stringSrc);
			selectingSrc = true;
			// clearing map from markers
			for (i=0;i<markers.length;i++)
				mymap.removeLayer(markers[i]);
			// clearing map from lines
			for (i=0;i<polylines.length;i++)
				mymap.removeLayer(polylines[i]);
			//reactivate click event
			mymap.on('click', onMapClick);
			$('#path-info').hide();
			$('#path').html('<tbody></tbody>');
		
		} );
		
		$('#findBtn').click(function() {
			if(srcLat != undefined)
			{
				$.ajax({
					url : '/Lab3_4/CalculatePath',
					type : 'GET',
					data : 
					{
						src : srcLat + ',' + srcLon,
						dst : dstLat + ',' + dstLon
					},
					dataType : 'json',
	
					success : function(data, textStatus, jqXHR) {
						//our country code was correct so we have some information to display
						if (data.length > 0) {
							//console.log("success");
							$('#path-info').show();	
							// clearing map from markers
							for (i=0;i<markers.length;i++)
								mymap.removeLayer(markers[i]);
							// clearing map from lines
							for (i=0;i<polylines.length;i++){
							if (typeof polyline !== 'undefined')
								mymap.removeLayer(polylines[i]);
							}
							// clearing PATH INFO TABLE
							$('#path').html('<tbody></tbody>');
							
							pointList= [];
							min_lat = 90; max_lat = -90; min_lng = 180; max_lng = -180;
							
							// inserting src & dst markers
							var edgesSize = data.length;
							var point = new Object();
							point.lat = data[0].latSrc;
							point.lng = data[0].lonSrc;
							
							// inserting src
							markerType = "src";
							insertMaker(point);
							
							point.lat = data[edgesSize-1].latDst;
							point.lng = data[edgesSize-1].lonDst;
							
							// inserting dst
							markerType = "dst";
							insertMaker(point);
							
							// inserting edges
							$.each(data, 
								function(k,edge) {
									insertEdge(edge);
								}
							);
							
							// populating path info table
							insertLines(data);
							
							// centering map
							mymap.fitBounds([
									[max_lat, max_lng],
									[min_lat, min_lng]
							]);
						}
						//display error message
						else {
							$('#errorMsg').html('Path not found');
							$('#errorModal').modal('show');
						}
						
						// showing direction buttons
						//document.getElementById("direction-buttons").style.visibility = "visible";
					},
	
					//If there was no resonse from the server
					error : function(jqXHR, textStatus, errorThrown) {
						$('#errorMsg').html('Server Error');
						$('#errorModal').modal('show');
						console.log("Something really bad happened " + textStatus);
					}
				});
				
				
			}
		});
	});
	