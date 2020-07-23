<#import "/parts/base.ftlh" as base>

<@base.content title="Change user | Learning Log">

	<p>Change user:</p>
	
	<form action="/admin/users/${user.id}/change" method="POST">
		<div>
			<label>Username: <input type="text" name="username" value="${user.username}" /></label><br />
			<label>Password: <input type="text" name="password" value="${user.password}" /></label>
		</div>
		<#list roles as role>
			<label><input type="checkbox" name="roles" value="${role}" ${user.roles?seq_contains(role)?string("checked", "")} />${role}</label>
		</#list>
		<input type="hidden" name="_csrf" value="${_csrf.token}" />
		<div><button type="submit">Save</button></div>
	</form>

</@base.content>