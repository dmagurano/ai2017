<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="it.polito.ai.Util.LineManager" import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	
	<!-- For bootstrap -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/index_style.css">
	
	<!-- These tags are for leaflet. If I don't do this, the javascript execution in div map_container doesn't work -->
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.0.3/dist/leaflet.css" />
	<script src="https://unpkg.com/leaflet@1.0.3/dist/leaflet.js"></script>
	
	
	<script type="text/javascript">
	$(function() {
		  var bid, trid;
		  $('#lines td').click(function() {
		       trid = $(this).attr('id'); // table row ID 
		       //alert(trid);
		       //$.get("/Lab2/LineRequest");
		       //window.location.replace("/Lab2/LineRequest");
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
		       a.done(function(msg){
		    	   //$("#map_container").load("map.jsp");
		    	   $("#map_container").html(msg);
			       $("#mapid").find("script").each(function(i) {
	                   eval($(this).text());
	               });
		       });
		       
		       // TODO a.fail() 

		       //window.location.replace("/Lab2/LineRequest");
		  });
	});

	/*
		$.ajax({
  //The URL to process the request
    'url' : 'page.php',
  //The type of request, also known as the "method" in HTML forms
  //Can be 'GET' or 'POST'
    'type' : 'GET',
  //Any post-data/get-data parameters
  //This is optional
    'data' : {
      'paramater1' : 'value',
      'parameter2' : 'another value'
    },
  //The response from the server
    'success' : function(data) {
    //You can use any jQuery/JavaScript here!!!
      if (data == "success") {
        //alert('request sent!');
      }
    }
  });	
	*/

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
					<div class="panel-body">
						<input type="text" class="form-control" id="dev-table-filter" data-action="filter" data-filters="#dev-table" placeholder="search line" />
					</div>
					<table class="table table-hover" id="lines">
						<thead>
							<tr>
								<th>Line Name</th>
							</tr>
						</thead>
						<tbody>
							<% LineManager lm = new LineManager(); 
							   List<String> lines = lm.getAllLines();
							   if(lines.size() == 0){
							%>
							<tr>
								<td> Lines not found </td>
							</tr>
							<%}else{
								
								for(String line: lines){%>
									<tr>
										<td  id=<%= line %>> Line <%=line %></td>
									</tr>
							<%		
								}
								
							} %>
							
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-6" id="map_container"></div>
		</div>
	</div>
</body>
</html>