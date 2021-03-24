<#include "user_security.ftlh" />

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand" href="/">Learning Log</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active">
				<a class="nav-link" href="/">Home</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="/public_topics">Public topics</a>
			</li>
			<li class="nav-item">
				<a class="nav-link" href="/user_topics/${currentUserId}">Own topics</a>
			</li>
		</ul>

		<ul class="navbar-nav navbar-right">
		    <#if user??>
		        <li class="nav-item mx-1">
		        	<span class="navbar-text">Hello, ${user.getUsername()}.</span>
		        </li>
			    <#if user.isAdmin()>
			    	<li class="nav-item active mx-1">
			    		<a href="/admin/users" class="btn btn-light">Users</a>
			     	</li>
				</#if>
			    <form name="logoutForm" action="/logout" method="post">
					<input type="hidden" name="_csrf" value="${_csrf.token}" />
					<button name="submit" class="btn btn-outline-success my-2 my-sm-0 mx-1">Log out</button>
				</form>
			<#else>
				<li class="nav-item"><a href="/registration" class="nav-link">Register</a></li>
			    <li class="nav-item"><a href="/login" class="nav-link">Log in</a></li>
			</#if>
			<li class="nav-item ml-1">
				<a href="/new_topic" class="btn btn-primary my-2 my-sm-0">Add a new topic</a>
			</li>
		</ul>
	</div>
</nav>