<#import "/parts/base.ftlh" as base>

<@base.content title="Log In - Learning Log">

	<h5 class="mb-4">Log into your account</h5>
	
    <form action="/login" method="post">
    	<div class="form-group row">
    		<label for="username" class="col-sm-2 col-form-label">User Name:</label>
    		<div class="col-sm-5">
	    		<input id="username" type="text" name="username" required class="form-control" />
    		</div>
    	</div>
    	<div class="form-group row">
    		<label for="password" class="col-sm-2 col-form-label">Password:</label>
    		<div class="col-sm-5">
	    		<input id="password" type="password" name="password" required class="form-control"/>
    		</div>
    	</div>
    	
    	<input type="hidden" name="_csrf" value="${_csrf.token}" />
    	<button name="submit" class="btn btn-primary">Sign In</button>
	</form>

    <p class="mt-3">Don't have an account? <a href="/registration">Sign Up</a>.</p>
    
</@base.content>
