<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Todo List</title>
</head>
<style type="text/css">
.strike {
	text-decoration: line-through;
}

.alert {
	border: 1px solid;
}

.alert-error {
	background-color: #c60f13;
	border-color: #970b0e;
	color: white;
}

.alert-success {
	background-color: #5da423;
	border-color: #457a1a;
	color: white;
}

.text-error {
	color: #c60f13;
}

.pagination li {
	display: inline;
}

.pagination li>a {
	margin-left: 10px;
}
</style>
<body>
	<h1>Todo List</h1>
	<div id="todoList">
		<c:choose>
			<c:when test="${page != null && page.totalPages != 0}">

				<table class="maintable">
					<thead>
						<tr>
							<th class="no">No</th>
							<th class="articleClass">Title</th>
						</tr>
					</thead>

					<%-- (3) --%>
					<c:forEach var="todo" items="${page.content}" varStatus="rowStatus">
						<tr>
							<td class="no">${(page.number * page.size) + rowStatus.count}
							</td>
							<td class="articleClass">${f:h(todo.todoTitle)}</td>
						</tr>
					</c:forEach>

				</table>

				<div class="paginationPart">

					<t:pagination page="${page}" outerElementClass="pagination" />

				</div>
			</c:when>
		</c:choose>
	</div>

</body>
</html>