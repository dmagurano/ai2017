<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="it.polito.madd.*"
	import="it.polito.madd.model.*" import="java.util.Map"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="css/home_style.css">
<title>Checkout</title>
</head>
<body>
	<!-- MASTINUX a che la classe serve container? -->
	<div class="container">
		<jsp:include page="navbar.jsp" />
	</div>

	<div class="container">
		<div class="row">
			<div class="col-lg-10 col-lg-offset-1 col-md-12 col-sm-12">

				<h3>Review your order.</h3>

<!-- 				<form action="/Lab1/cart" method="POST"> -->
					<div class="col-sm-12 col-md-10 col-md-offset-1">
						<% CartManager cm = (CartManager) request.getSession().getAttribute("CartService"); 
		        		Map<Ticket,Integer> tickets = cm.getItems();
		        		if (tickets.size() == 0)
		        		{
		        			%>
		        			<div class="alert alert-info" role="alert">
			 					Sorry, the cart is <strong>empty</strong>, you can add some tickets to your cart from the homepage.
			 				</div>
			 				<p>
			 				<a href="/Lab1"><button type="submit" style="width:200px" class="btn btn-default btn-block" onClick="">Return to home.</button> </a>
							
		        			<% 
		        		}
		        		else {
		        		Float total = new Float(0);
		        		total = cm.getTotal(); %>
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Tickets</th>
									<th>Quantity</th>
									<th class="text-center">Price</th>
									<th class="text-center">Total</th>
									<!-- th tag for remove button - temporarily removed  
									<th> </th>
									-->
								</tr>
							</thead>
							<tbody>
								<%
								
				        		 for(Map.Entry<Ticket,Integer> ticket : tickets.entrySet()){
				        			 /*	total += ticket.getKey().getPrice()*ticket.getValue(); */
				        	 	%>
								<tr>
									<td class="col-sm-8 col-md-6">
										<div class="media">
											<!-- <a class="thumbnail pull-left" href="#"> <img
												class="media-object"
												src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/128/ticket-icon.png"
												style="width: 72px; height: 72px;">
											</a> -->
											<div class="media-body">
												<h5 class="media-heading">
													<%= ticket.getKey().getType().toString().replace("_", " ") %>
												</h5>
												<!--
												<span>Status: </span><span class="text-success"><strong>In Stock</strong></span>
												-->
											</div>
										</div>
									</td>
									<td class="col-sm-1 col-md-1" style="text-align: center">
										<%-- <input type="number" class="form-control" name=<%= ticket.getKey().getType().toString() %>
											value=<%=ticket.getValue() %>> --%>
											<%=ticket.getValue() %>
									</td>
									<td class="col-sm-1 col-md-1 text-center"><strong><%= ticket.getKey().getPrice() %></strong></td>
									<td class="col-sm-1 col-md-1 text-center"><strong><%= ticket.getKey().getPrice()*ticket.getValue() %></strong></td>
									<!-- temporarily removed
									
									<td class="col-sm-1 col-md-1">
										<button type="button" class="btn btn-danger">
											<span class="glyphicon glyphicon-remove"></span> Remove
										</button>
									</td>
									
									 -->
								</tr>
								<% }%>
							</tbody>
							<tfoot>
								<tr>
									<td> </td>
									<td> </td>
									<!-- 
									<td> </td>
									 -->
									<td><h3>Total</h3></td>
									<td class="text-right"><h3><%= total %></h3></td>
								</tr>
								
								<tr>
									<td> </td>
									<td> </td>
									<!-- 
									<td> </td>
									 -->
									<!-- <td>
										<button type="submit" class="btn btn-default">
											<span class="glyphicon glyphicon-shopping-cart"></span>
											Update your cart
										</button>
									</td>
									<td><a href="/Lab1/private/payment">
										<button type="button" class="btn btn-success">
											Checkout <span class="glyphicon glyphicon-play"></span>
										</button>
									</a></td> -->
								</tr>
							</tfoot>
						</table>

					</div>
					<!-- <input type="hidden" name="METHOD" id="method" value="modify"> -->
					<div class="col-sm-10 col-md-10 col-md-offset-1">
						<hr>
						<h4>Insert your payment information here:</h4>
						<p>
						<div class="col-sm-3 col-md-3 ">
							<% 
								PaymentManager pm = (PaymentManager) request.getSession().getAttribute("PaymentService");
								CreditCard lastcc = pm.getMostRecentCard();
								String n, h, c;
								if (lastcc == null)
								{
									n = new String("");
									h = new String("");
									c = new String("");
								}
								else
								{
									n = lastcc.getNumber();
									h = lastcc.getHolder();
									c = lastcc.getCvv();
								}
							
							%>
							<form action="/Lab1/private/payment" method="POST">
								<label for="number">Credit card number </label><input type="number" class="form-control" name="number" value="<%= n %>">
								<label for="holder">Card holder </label><input type="text" class="form-control" name="holder" value="<%= h %>">
								<label for="cvv">CVV </label><input type="number" class="form-control" max="999" name="cvv" value="<%= c %>">
								<hr>
								<button type="button" class="btn btn-success">
									Submit order <span class="glyphicon glyphicon-play"></span>
								</button>
							</form>
						</div>
					</div>
					<% } %>
				<!-- </form> -->
				
			</div>
		</div>
	</div>

</body>
</html>