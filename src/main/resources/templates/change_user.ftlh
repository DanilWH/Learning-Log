<#import "/parts/base.ftlh" as base>

<@base.content title="Change user | Learning Log">

	<h5 class="mb-4">Change user:</h5>
	
	<form action="/admin/users/${user.id}/change" method="POST">
		<div class="form-group row">
			<label for="username" class="col-sm-1 col-form-label">Username:</label>
			<div class="col-sm-5">
				<input id="username" type="text" name="username"
					   value="${user.username}" class="form-control" />
			</div>
		</div>
		
		<div class="form-group row">
			<label for="password" class="col-sm-1 col-form-label">Password:</label>
			<div class="col-sm-5">
				<input id="password" type="text" name="password" value="${user.password}"
					   class="form-control"/>
			</div>
		</div>
		
		<div class="form-group">
			<#list roles as role>
				<div class="custom-control custom-checkbox custom-control-inline">
					<input id="${role}" type="checkbox" name="roles" value="${role}"
						${user.roles?seq_contains(role)?string("checked", "")} class="custom-control-input"/>
					<label for="${role}" class="custom-control-label">${role?capitalize}</label>
				</div>
			</#list>
		</div>
		
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<button type="submit" class="btn btn-primary">Save</button>
	</form>

</@base.content>