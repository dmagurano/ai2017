<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="it.polito.madd.model.Ticket,java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<link rel="stylesheet" href="css/test_home_style.css">

<title>Test Home</title>
</head>
<body>
	<%@include file="navbar.jsp"%>

	<div
		class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Select your tickets</h3>
			</div>
			<div class="panel-body">

				<form role="form" id="addCart" name="addCart" action="/Lab1/cart"
					method="POST">
					<% HttpSession s = request.getSession();
				ArrayList<Ticket> tickets = (ArrayList<Ticket>)s.getServletContext().getAttribute("TicketList");
				
				for ( Ticket t : tickets ){
					String ticketName = t.getType().toString();
						%>
					<div class="input-group">
						<span class="input-group-addon"><%= ticketName.replace("_"," ") %></span>
						<input type="number" class="form-control" name="<%= ticketName %>"
							id="<%= ticketName %>" min="0" max="59" value="0">
					</div>
					<br>
					<%} %>
					<input type="hidden" name="METHOD" id="method" value="add">
					<button type="submit" class="btn btn-default btn-block" onClick="">Add
						to cart</button>
				</form>

			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>