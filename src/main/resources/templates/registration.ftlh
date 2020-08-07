<#import "/parts/base.ftlh" as base>

<@base.content title="Registration - Learning Log">

	<form action="/registration" method="post">
	    <div class="form-group">
	    	<label for="username">Username</label>
	    	<input id="username" type="text" name="username" required maxlength="150"
	    		   class="form-control mb-2" aria-describedby="message" placeholder="Username" />
		   	<small id="message" class="form-text text-danger">${username_message?ifExists}</small>
		    <div class="text-muted">
		    	Required. 150 characters or fewer. Letters, digits and @/./+/-/_ only.
		    </div>
	    </div>
	    
	    <div class="form-group">
	    	<label for="password">Password</label>
	    	<input id="password" type="password" name="password" required
	    		   class="form-control mb-2" placeholder="Password" />
		    <div class="text-muted">
				<ul>
					<li>Your password can't be too similar to your other personal information.</li>
					<li>Your password must contain at least 8 characters.</li>
					<li>Your password can't be a commonly used password.</li>
					<li>Your password can't be entirely numeric.</li>
				</ul>
		    </div>
	    
	    	<label for="password_confirm">Password confirmation</label>
	    	<input id="password_confirm" type="password" name="password_confirm" required
	    		   class="form-control" aria-describedby="password_message"
	    		   placeholder="Password cofrimation"/>
			<small id="password_message" class="form-text text-danger">${password_message?ifExists}</small>
	    </div>
	    
		
	    <input type="hidden" name="_csrf" value="${_csrf.token}" />
	    <button name="submit" class="btn btn-primary">Sign Up</button>
	</form>
    
</@base.content>