<#macro topic_form url checked_access>

<form action="${url}" method="POST">
	<input type="hidden" name="_csrf" value="${_csrf.token}" />
	
	Title <br />
	<input type="text" name="title" value="${(topic.title)?ifExists}" required /><br />
	${message?ifExists} <br />
	
	Description <br />
	<textarea name="description" cols="80" rows="10">${(topic.description)?ifExists}</textarea><br />
	
	Accessibility:
	<#list accesses as access>
		<input id="${access}" type="radio" name="access" value="${access}" ${access?contains(checked_access)?string("checked", "")}/>
		<label for="${access}">${access?capitalize}</label>
	</#list>
	<br />
	
	<button type="submit">Save</button>
</form>

</#macro>