<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lab3.4</title>

<!-- For bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- These tags are for leaflet. If I don't do this, the javascript execution in div map_container doesn't work -->
<link rel="stylesheet"
	href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>

<link rel="stylesheet" href="css/index_style.css">

<script src="map.js"></script>


</head>
<body>
	<div class="container">
		<div class="row">
			<h3 style="text-align: center; margin-bottom: 20px" id='statusText'></h3>

			<div class="col-lg-6 col-md-6">
				<div class="panel panel-default">
					<div class="panel-body">
						<div class="map" id="mapid"></div>
					</div>
				</div>
			</div>


			<div class="second col-lg-6 col-md-6">
				<div class="row">
					<button type="button" id="findBtn" class="btn btn-default col-xs-7 ">Find
						Path</button>
					<button type="button" id="cancelBtn"
						class="btn btn-default col-xs-4 pull-right">Restart</button>
				</div>

				<div class="row">
					<div style="margin-top: 20px" class="panel panel-default">
						<div class="panel-heading">
							<h3 style="text-align: center" class="panel-title">Path info</h3>
						</div>
						<div class="panel-body">
							<table class="table table-hover table-striped" id="path">
								<thead>
									<tr>
										<th>Line</th>
										<th>Description</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>No lines found</td>
										<td>pollo</td>
									</tr>
									<tr>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>	

			</div>
		</div>
	</div>
	<div class="modal">
		<!-- Place at bottom of page -->
	</div>
	<script>
		$body = $("body");

		$(document).on({
			ajaxStart : function() {
				$body.addClass("loading");
			},
			ajaxStop : function() {
				$body.removeClass("loading");
			}
		});
	</script>
</body>

</html>