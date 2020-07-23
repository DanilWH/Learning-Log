<#import "/parts/base.ftlh" as base>

<@base.content title="Change user | Learning Log">
	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>Username</th>
				<th>Password</th>
				<th>Role</th>
			</tr>
		</thead>
		<tbody>
		<#list users as user>
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.password}</td>
				<td><#list user.roles as role>${role}<#sep>,<br /></#list></td>
				<td><a href="/admin/users/${user.id}/change">Change</a></td>
			</tr>
		</#list>
		</tbody>
	</table>
</@base.content>