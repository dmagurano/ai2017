<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="it.polito.madd.model.Ticket,java.util.ArrayList,it.polito.madd.LoginManager"%>
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
<link rel="stylesheet" href="css/HomeStyle.css">
</head>
<body>
	<% HttpSession s = request.getSession(); %>
	<div class="container">
		<jsp:include page="navbar.jsp" />
	</div>
	<div class="container">
		<div class="row centered-form">
			<div
				class="col-xs-12 col-sm-10 col-md-10 col-sm-offset-1 col-md-offset-1">
				<div class="panel panel-default"
					style="max-width: 400px; margin: auto;">
					<div class="panel-heading">
						<h3 class="panel-title">Select your tickets</h3>
					</div>
					<div class="panel-body">
						<form role="form" id="addCart" name="addCart" action="/Lab1/cart"
							method="POST">
							<% 
								ArrayList<Ticket> tickets = (ArrayList<Ticket>)s.getServletContext().getAttribute("TicketList");
								
								for ( Ticket t : tickets ){
									String ticketName = t.getType().toString();
			    			%>
							<div class="form-group">
								<div class="form-inline">
									<label class="control-label col-md-7"
										style="margin: 5px; text-align: right" for="<%= ticketName %>">
										<%= ticketName.replace("_"," ") %>
									</label> <input type="number" name="<%= ticketName %>"
										id="<%= ticketName %>" class="form-control input-sm" min="0"
										max="59" value="0">
								</div>
							</div>
							<% } %>
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