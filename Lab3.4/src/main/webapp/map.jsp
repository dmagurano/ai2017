<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lab2 Page2</title>

<link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>

<style type="text/css">
#mapid {
	height: 500px;
	width: 500px;
}
</style>

</head>
<body>

	<div id="mapid"></div>

	<script>
			var busStops;
	
			var mymap = L.map('mapid').setView([45.07, 7.69], 12);
			
			L.tileLayer('https://api.mapbox.com/styles/v1/mapbox/streets-v10/tiles/256/{z}/{x}/{y}?access_token=pk.eyJ1IjoiZGF2cjA5MTAiLCJhIjoiY2owemk4N2FmMDJ1ZzMzbno3YjZxZDN3YyJ9.eJdGDM0goIVXcFmMrQX8og').addTo(mymap);
			
			//var marker = L.marker([45.07, 7.69]).addTo(mymap);
			//marker.bindPopup("<b>You are hacked!</b><br>I am a popup.").openPopup();
			
			
			
			var pointA = new L.LatLng(45.07, 7.69);
			var pointB = new L.LatLng(45.08, 7.70);
			var pointC = new L.LatLng(45.08,7.694)
			var pointList = [pointA, pointB, pointC];

			var firstpolyline = new L.Polyline(pointList, {
			    color: 'red',
			    weight: 3,
			    opacity: 0.5,
			    smoothFactor: 1
			});
			firstpolyline.addTo(mymap);
			
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
			
			
	</script>

</body>
</html>