<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" 
	import="java.util.List"%>
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

<script src="map.js"></script>

</head>
<body>
	<div class="container">
		<div class="row">		
			<h3 id='statusText'></h3>
			
			<div class="col-lg-6 col-md-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="map" id="mapid"></div>
					</div>
				</div>
			</div>

			<div class ="col-lg-6 col-md-6">
			  <div class="btn-group btn-group-justified" role="group" >
				  <div class="btn-group" role="group">
				    <button type="button" class="btn" id="findBtn" value="">Find Path</button>
				  </div>
				  <div class="btn-group" role="group">
				    <button type="button" class="btn" id="cancelBtn" value="">Cancel</button>
				  </div>
			  </div>
			</div>
		</div>
	</div>
</body>

</html>