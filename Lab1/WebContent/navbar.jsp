
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
			 <ul class="nav navbar-nav navbar-right">
		<% 
			LoginManager lm = (LoginManager) session.getAttribute("LoginService"); 
			
			if ( !lm.isLogged() ) {
		
		%>
			<form id="signin" action='/Lab1/login' class="navbar-form navbar-right" role="form" method="post">
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
		<% } else { %>
			<p class="navbar-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Benvenuto <%= lm.getUsername() %></p>
                    <li>
                        <form id='signout' action="/Lab1/logout">
                            <button class="btn btn-primary navbar-btn"><span><i class="glyphicon glyphicon-log-out"></i></span>&nbsp;Logout</button>
                        </form>
                    </li>
		
		<% } %>
				<li>
                        <form id='cart' action="/Lab1/cart">
                            <button class="btn btn-default navbar-btn"><span><i class="glyphicon glyphicon-shopping-cart"></i></span>&nbsp;Cart</button>
                        </form>
                 </li>
			</ul>
		</div>
	</div>
</nav>