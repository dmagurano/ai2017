<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="it.polito.ai.Util.LineManager"
	import="java.util.List, it.polito.ai.Lab2.Entities.BusLine"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lab2 Page1</title>

<!-- For bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- These tags are for leaflet. If I don't do this, the javascript execution in div map_container doesn't work -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>

<link rel="stylesheet" href="css/index_style.css">

<script type="text/javascript">
	var mymap;
	var polyline;
	var markers = [];
	
	window.onload = function(){
		// initializing map
		mymap = L.map('mapid').setView([45.07, 7.69], 13);
		L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og').addTo(mymap);
	}
	
	$(function() {
		var bid, trid;
		
		$('#lines tr')
			.click(function() {
				trid = $(this).attr('id'); // table row ID
				
				$.ajax({
					url : '/Lab2/LineRequest',
					type : 'GET',
					data : {
						'selected_line' : trid,
					},
					dataType : 'json',

					success : function(data, textStatus, jqXHR) {
						//our country code was correct so we have some information to display
						if (data.length > 0) {
							console.log("success");
							
							// clearing map
							for (i=0;i<markers.length;i++)
								mymap.removeLayer(markers[i]);
							if (typeof polyline !== 'undefined')
								mymap.removeLayer(polyline);
							
							var pointList = [];
							var marker
							var min_lat = 90, max_lat = -90, min_lng = 180, max_lng = -180;
							
							$.each(data, function(k,busStop) {
								var curPoint = new L.LatLng(busStop.lat, busStop.lng);
								pointList.push(curPoint);
								
								var marker = L.marker([busStop.lat, busStop.lng]).addTo(mymap)
									.bindPopup() // in order to modify popup content
									.on("click", function(e){
										clickOnMarker(e, busStop.name, busStop.id)
									});
								
								markers.push(marker);
								mymap.addLayer(marker);

								if (busStop.lat < min_lat)
									min_lat = busStop.lat;
								if (busStop.lat > max_lat)
									max_lat = busStop.lat;								
								if (busStop.lng < min_lng)
									min_lng = busStop.lng;
								if (busStop.lng > max_lng)
									max_lng = busStop.lng;
							});
							
							// centering map
							mymap.fitBounds([
									[max_lat, max_lng],
									[min_lat, min_lng]
							]);
							
							polyline = new L.Polyline(pointList, {
							    color: 'blue',
							    weight: 5,
							    opacity: 0.5,
							    smoothFactor: 1
							});
							
							polyline.addTo(mymap);
							mymap.addLayer(polyline);
						}
						//display error message
						else {
							console.log("error");
						}
					},

					//If there was no resonse from the server
					error : function(jqXHR, textStatus, errorThrown) {
						console.log("Something really bad happened " + textStatus);
					}
				});
			});
	});
</script>

<script type="text/javascript">
	function clickOnMarker(e, stopName, stopId){
		var popup = e.target.getPopup();		
		console.log("clicked on bus stop: " + stopId);
		
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
					console.log("success");
					
					//console.log(data);
					var popupContent = "<b>" + stopName + "<b><br>Other lines:<br>";
					$.each(data, function(k,busLine) {
						//console.log(busLine.line);
						popupContent = popupContent.concat(busLine.line + "<br>");
					});
					
					console.log("content: " + popupContent);
					
					popup.setContent( popupContent );
                    popup.update();

				}
				//display error message
				else {
					console.log("error");
				}
			},

			//If there was no resonse from the server
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("Something really bad happened " + textStatus);
			}
		});
	}
</script>

</head>
<body>
	<div class="container">
		<div class="row">		
			<div class="col-lg-6 col-md-6">
			
				<div class="panel panel-primary">
					<div class="panel-heading">
						
						<h3 class="panel-title">Lines</h3>
						
						<div class="pull-right">
							<a class="thumbnail pull-left" href="#"> <img
								class="media-object"
								src="http://icons.iconarchive.com/icons/fasticon/happy-bus/128/bus-blue-icon.png"
								style="width: 32px; height: 32px;">
							</a>
						</div>
						
					</div>
					<!-- 
					<div class="panel-body">
						<input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="search line" />
					</div>
					 -->
					 
					<div class="lines-table-collapse col-lg-12">
						<table class="table table-hover table-collapse" id="lines">
						<thead>
							<tr>
								<th>Line</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<% 
							   LineManager lm = new LineManager(); 
							   List<BusLine> busLines = lm.getAllLines();
							   if(busLines.size() == 0){
							%>
							<tr>
								<td>No lines found</td>
							</tr>
							<%}else{
								
								for(BusLine busLine: busLines){%>
							<tr id=<%= busLine.getLine() %>>
								<td ><%=busLine.getLine() %></td>
								<td><%=busLine.getDescription() %></td>
							</tr>
							<%		
								}
							  } 
							%>

						</tbody>
					</table>
				  	</div>
				  	
				</div>
			</div>
			<!--
			<div class="col-md-6" id="map_container"></div>
			-->
			<div class="col-lg-6 col-md-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="map" id="mapid"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>