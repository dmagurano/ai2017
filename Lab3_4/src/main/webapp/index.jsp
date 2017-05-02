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

<script type="text/javascript" src="map.js"></script>


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
					<button type="button" id="findBtn" class="btn btn-default col-xs-7 ">Calculate</button>
					<button type="button" id="cancelBtn"
						class="btn btn-default col-xs-4 pull-right">Restart</button>
				</div>

				<div id="path-info" class="row">
					<div style="margin-top: 20px" class="panel panel-default">
						<div class="panel-heading">
							<h3 style="text-align: center" class="panel-title">PATH INFO</h3>
						</div>
						<div class="panel-body">
							<table class="table table-hover table-striped" id="path">
							<tbody></tbody>
							</table>
						</div>
					</div>
				</div>	

			</div>
		</div>
	</div>
	<div class="modal-ajax">
		<!-- Place at bottom of page -->
	</div>
	<div id="errorModal" class="modal fade" tabindex="-1" role="dialog">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">Error</h4>
	      </div>
	      <div class="modal-body">
	        <p id="errorMsg">Path not found</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
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