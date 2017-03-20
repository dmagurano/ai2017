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
			<!--			<ul class="nav navbar-nav navbar-right"> -->

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

			<!--			</ul> -->

		</div>
	</div>
</nav>