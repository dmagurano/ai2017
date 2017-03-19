<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="it.polito.madd.model.TicketType"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Home Lab1</title>
<style type="text/css">
<%@ include file="css/HomeStyle.css"%>
</style>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="">Lab1</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-right">
						<form id="signin" action='' class="navbar-form navbar-right"
							role="form" method="post">
							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-user"></i></span> <input id="email"
									type="email" class="form-control" name="email" value=""
									placeholder="Email Address" required>
							</div>

							<div class="input-group">
								<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock"></i></span> <input id="password"
									type="password" class="form-control" name="password" value=""
									placeholder="Password" required>
							</div>

							<button type="submit" class="btn btn-primary">
								<span><i class="glyphicon glyphicon-log-in"></i></span>&nbsp;Login
							</button>
						</form>

					</ul>

				</div>
			</div>
		</nav>
	</div>
	<div class="container">
		<div class="row centered-form">
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-sm-offset-1 col-md-offset-1">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Select your tickets</h3>
					</div>
					<div class="panel-body">
						<form role="form" id="addCart" name="addCart" action="/Lab1/cart"
							method="POST">
							<% for (TicketType type: TicketType.values()){
			    						String ticketName = type.toString().replace("_", " ") ;
			    					 %>
			    			<div class="row">		 
								<div class="form-inline">
									<label class="control-label col-sm-2" for="<%= ticketName %>"> <%= ticketName %>
									</label> 
									<input type="number" name="<%= ticketName %>"
										id="<%= ticketName %>" class="quantity form-control input-sm" min="0"
										max="59" placeholder="">
								</div>
							</div>	
							<%} %>
							<input type="hidden" name="METHOD" id="method" value="add">
							<button type="submit" class="btn btn-info btn-block butSub"
								onClick="">Add to cart</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!--centered-form-->

	</div>
	<!-- /container -->



</body>
</html>