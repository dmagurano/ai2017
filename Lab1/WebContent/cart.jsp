<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="it.polito.madd.*"
	import="it.polito.madd.model.*" import="java.util.Map"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>The cart</title>
</head>
<body>
	<div class="container">
		<%@include file="navbar.jsp"%>
	</div>

	<div class="container">
		<div class="row">
			<div class="page-header">
				<h3>Cart</h3>
			</div>
			<form action="/Lab1/cart">
				<div class="col-sm-12 col-md-10 col-md-offset-1">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>Tickets</th>
								<th>Quantity</th>
								<th class="text-center">Price</th>
								<th class="text-center">Total</th>
								<th> </th>
							</tr>
						</thead>
						<tbody>
							<% CartManager cm = (CartManager) request.getSession().getAttribute("CartService"); 
	                	   Map<Ticket,Integer> tickets = cm.getItems();
	                	   Float total = new Float(0);
	                	   for(Map.Entry<Ticket,Integer> ticket : tickets.entrySet()){
	                		   total += ticket.getKey().getPrice()*ticket.getValue();
	                	 %>
							<tr>
								<td class="col-sm-8 col-md-6">
									<div class="media">
										<a class="thumbnail pull-left" href="#"> <img
											class="media-object"
											src="http://icons.iconarchive.com/icons/custom-icon-design/flatastic-2/128/ticket-icon.png"
											style="width: 72px; height: 72px;">
										</a>
										<div class="media-body">
											<h4 class="media-heading">
												<a href="#"><%= ticket.getKey().getType().toString().replace("_", " ") %></a>
											</h4>
											<span>Status: </span><span class="text-success"><strong>In
													Stock</strong></span>
										</div>
									</div>
								</td>
								<td class="col-sm-1 col-md-1" style="text-align: center"><input
									type="email" class="form-control" id="exampleInputEmail1"
									value=<%=ticket.getValue() %>></td>
								<td class="col-sm-1 col-md-1 text-center"><strong><%= ticket.getKey().getPrice() %></strong></td>
								<td class="col-sm-1 col-md-1 text-center"><strong><%= ticket.getKey().getPrice()*ticket.getValue() %></strong></td>
								<td class="col-sm-1 col-md-1">
									<button type="button" class="btn btn-danger">
										<span class="glyphicon glyphicon-remove"></span> Remove
									</button>
								</td>
							</tr>
							<% }%>
						</tbody>
						<tfoot>
							<tr>
								<td> </td>
								<td> </td>
								<td> </td>
								<td><h3>Total</h3></td>
								<td class="text-right"><h3><%= total %></h3></td>
							</tr>
							<tr>
								<td> </td>
								<td> </td>
								<td> </td>
								<td>
									<button type="submit" class="btn btn-default">
										<span class="glyphicon glyphicon-shopping-cart"></span> Update
										your cart
									</button>
								</td>
								<td>
									<button type="submit" class="btn btn-success">
										Checkout <span class="glyphicon glyphicon-play"></span>
									</button>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
				<input type="hidden" name="METHOD" id="method" value="modify">
			</form>
		</div>
	</div>
</body>
</html>