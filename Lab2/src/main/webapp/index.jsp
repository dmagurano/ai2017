<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="it.polito.ai.Util.LineManager"
	import="java.util.List, it.polito.ai.Lab2.Entities.BusLine"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lab2 Page1</title>

<!-- For bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/index_style.css">

<!-- These tags are for leaflet. If I don't do this, the javascript execution in div map_container doesn't work -->
<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>

<style type="text/css">
#mapid {
	height: 500px;
	width: 500px;
}
</style>

<script type="text/javascript">
	var mymap;
	var polyline;
	
	$(function() {
		var bid, trid;
		$('#lines td')
			.click(function() {
				trid = $(this).attr('id'); // table row ID

				//console.log(trid);
				//alert(trid);
				//$.get("/Lab2/LineRequest");
				//window.location.replace("/Lab2/LineRequest");
				$.ajax({
					url : '/Lab2/LineRequest',
					type : 'GET',
					data : {
						'selected_line' : trid,
					//'parameter2' : 'another value'
					},
					dataType : 'json',

					success : function(data, textStatus, jqXHR) {
						//our country code was correct so we have some information to display
						if (data.length > 0) {
							console.log("success");
							
							if (typeof mymap == 'undefined'){
								// initializing map
								mymap = L.map('mapid').setView([45.07, 7.69], 12);
							
								L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og').addTo(mymap);
							}
							else{
								// clearing map
							    for(i in mymap._layers) {
							        if(mymap._layers[i]._path != undefined) {
							            try {
							            	mymap.removeLayer(mymap._layers[i]);
							            }
							            catch(e) {
							                console.log("problem with " + e + mymap._layers[i]);
							            }
							        }
							    }
							}
							
							//var marker = L.marker([45.07, 7.69]).addTo(mymap);
							// marker.bindPopup("<b>You are hacked!</b><br>I am a popup.").openPopup();
							
							var pointList = [];
							$.each(data, function(k,busStop) {
								//console.log(v);
								/*
								console.log(busStop.name)
								console.log(busStop.lat)
								console.log(busStop.lng)
								*/
								var curPoint = new L.LatLng(busStop.lat, busStop.lng);
								pointList.push(curPoint);
							});
							
							var polyline = new L.Polyline(pointList, {
							    color: 'blue',
							    weight: 5,
							    opacity: 0.5,
							    smoothFactor: 1
							});
							
							polyline.addTo(mymap);
							
							/*circle.bindPopup("I am a circle.");
							polygon.bindPopup("I am a polygon.");
							
							var circle = L.circle([45.07, 7.69], {
							    color: 'red',
							    fillColor: '#f03',
							    fillOpacity: 0.5,
							    radius: 500
							}).addTo(mymap);
							
							var polygon = L.polygon([
							    [45.05, 7.31],
							    [45.044, 7.29],
							    [45.06, 7.16]
							]).addTo(mymap);
							*/
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
				/*
				 var a = $.ajax({
				 //The URL to process the request
				 'url' : '/Lab2/LineRequest',
				 //The type of request, also known as the "method" in HTML forms
				 //Can be 'GET' or 'POST'
				 'type' : 'GET',
				 //Any post-data/get-data parameters
				 //This is optional
				 'data' : {
				 'selected_line' : trid,
				 //'parameter2' : 'another value'
				 }
				 });
				 a.done(function(msg) {
				 $("#map_container").load("map.jsp");

				 //				$("#map_container").html(msg);
				 //				$("#mapid").find("script").each(function(i) {				
				 //					console.log("entered");
				 //					eval($(this).text());
				 //				});				
				 });

				 // TODO a.fail() 

				 //window.location.replace("/Lab2/LineRequest");
				 */
			});
	});
</script>


</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-6">
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
					<table class="table table-hover" id="lines">
						<thead>
							<tr>
								<th>Line Name</th>
							</tr>
						</thead>
						<tbody>
							<% 
							   LineManager lm = new LineManager(); 
							   List<BusLine> busLines = lm.getAllLines();
							   if(busLines.size() == 0){
							%>
							<tr>
								<td>Lines not found</td>
							</tr>
							<%}else{
								
								for(BusLine busLine: busLines){%>
							<tr>
								<td id=<%= busLine.getLine() %>>Line <%=busLine.getLine() %></td>
							</tr>
							<%		
								}
							  } 
							%>

						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-6" id="map_container"></div>
			<div class="col-md-6" id="mapid"></div>
		</div>
	</div>
</body>

</html>