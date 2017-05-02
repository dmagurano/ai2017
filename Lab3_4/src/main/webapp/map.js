
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
	
	function onMapClick(e){
		if (selectingSrc) {
			srcLat = e.latlng.lat;
			srcLon = e.latlng.lng;
			selectingSrc = false;
			$('#statusText').html(stringDst);
		} else {
			dstLat = e.latlng.lat;
			dstLon = e.latlng.lng;
			$('#statusText').html(stringDone);
			mymap.off('click', onMapClick);
		}
		
		var marker = L.marker([e.latlng.lat, e.latlng.lng]).addTo(mymap)
		markers.push(marker);
		mymap.addLayer(marker);
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
	
	function insertMaker(point){
		var curPoint = new L.LatLng(point.lat, point.lng);
		
		pointList.push(curPoint);
		
		var marker = L.marker([point.lat, point.lng]).addTo(mymap)
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
			$('#table').html('<tbody></tbody>');
		
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
							pointList= [];
							min_lat = 90; max_lat = -90; min_lng = 180; max_lng = -180;
							
							// inserting src & dst markers
							var edgesSize = data.length;
							var point = new Object();
							point.lat = data[0].latSrc;
							point.lng = data[0].lonSrc;
							insertMaker(point);
							point.lat = data[edgesSize-1].latDst;
							point.lng = data[edgesSize-1].lonDst;
							insertMaker(point);
							
							// inserting edges
							
							$.each(data, 
								function(k,edge) {
									insertEdge(edge);
									insertLine(edge);
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
				}
			});
	});
	