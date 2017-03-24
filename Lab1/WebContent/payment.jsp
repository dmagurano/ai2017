<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="it.polito.madd.*"
	import="it.polito.madd.model.*" import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- <meta charset="utf-8"> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

<link rel="stylesheet" href="css/home_style.css">

<title>Payment</title>
</head>
<body>
	<div class="container">
		<jsp:include page="navbar.jsp" />
	</div>
	<div class="container">
		<% 
			CartManager cm = (CartManager) request.getSession().getAttribute("CartService"); 
			PaymentManager pm = (PaymentManager) request.getSession().getAttribute("PaymentService");
			Boolean payment_ok = false;
			String check_result = (String) request.getAttribute("TRANSACTION_RESULT");
			float amount = 0;
			if (check_result != null)
			{
				payment_ok = pm.makePayment();
				amount = cm.getTotal();
				if (payment_ok)
					cm.clear();
			}
		%>
		<div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Payment result</h3>
				</div>
				<div class="panel-body">
					<% if(payment_ok) { %>
						<h4>Your order has been completed. The total amount of <%= amount %> has been charged on your card. Thank you. </h4>
					<% } else { %>
						<h4>We are facing some problem with your payment information. Please check it. </h4>
					<% } %>
					
					
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>