<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/Lab1">Lab1</a>
		</div>

		<div class="navbar-collapse collapse">
						
			<% if( ! request.getParameter("username").equals("null") ){ %>
			<form class="navbar-form navbar-right" action="/Lab1/logout" method="post">
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span> Log out
				</button>
			</form>
			<p class="navbar-text navbar-right">Logged in as <%= request.getParameter("username") %></p>
			<% } else { %>
			<form class="navbar-form navbar-right" action="/Lab1/login" method="get">
				
				<button type="submit" class="btn btn-default">
					<span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> Log in
				</button>
			</form>			
			<% } %>
						
			<!--
			<form id="signin" action='/login' class="navbar-form navbar-right" role="form" method="post">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					<input id="email" type="email" class="form-control" name="email" value="" placeholder="Email Address" required>
				</div>

				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<input id="password" type="password" class="form-control" name="password" value="" placeholder="Password" required>
				</div>

				<button type="submit" class="btn btn-primary">
					<span><i class="glyphicon glyphicon-log-in"></i></span>&nbsp;Login
				</button>
			</form>
-->

		</div>
	</div>
</nav>