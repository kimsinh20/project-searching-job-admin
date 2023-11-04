const todoInput = document.getElementById("todo-input");
const addTodoButton = document.getElementById("add-todo");
const todoList = document.getElementById("todo-list");
let tags = document.getElementById("tags");
let todos = [];

if (addTodoButton !== null) {
	addTodoButton.addEventListener("click", () => {
		const todoText = todoInput.value;
		if (todoText !== "") {
			todos.push({
				text: todoText,
				checked: false,
				id: Date.now(),
			});
			todoInput.value = "";
			updateTodoList();
		}
	});
}
if (todoInput != null) {
	todoInput.addEventListener("keypress", function(event) {
		// If the user presses the "Enter" key on the keyboard
		if (event.key === "Enter") {
			// Cancel the default action, if needed
			event.preventDefault();
			const todoText = todoInput.value;
			if (todoText !== "") {
				todos.push({
					text: todoText,
					checked: false,
					id: Date.now(),
				});
				todoInput.value = "";
				updateTodoList();
			}
		}
	});
}
const toggleTodo = (todo) => {
	todo.checked = !todo.checked;
	if (todo.checked) {
		todo.text = todo.text + "(Compeleted)";
	} else {
		var start = todo.text.indexOf("(");
		var end = todo.text.indexOf(")");

		// Remove the `(completed)` text from the string.
		todo.text = todo.text.slice(0, start) + todo.text.slice(end + 1);
	}
	updateTodoList();
};

const deleteTodo = (todo) => {
	todos.splice(todos.indexOf(todo), 1);
	updateTodoList();
};
const updateTodoList = () => {
	if (todoList == null && tags == null) return;
	let arr = todoInput.value.split(",");
	if (arr.length > 0) {
		arr.map(e => {
			const todoText = e.trim();
			if (todoText !== "") {
				todos.push({
					text: todoText,
					checked: false,
					id: Date.now(),
				});
				todoInput.value = "";
			}
		});

	}
	todoList.innerHTML = "";
	todos.forEach((todo) => {
		const li = document.createElement("li");
		li.classList.add("aside-tags");
		li.innerHTML = todo.text;
		const deleteButton = document.createElement("div");
		deleteButton.innerHTML = "<i id=\"btnDel\" class=\"fas fa-times\"></i>";
		deleteButton.addEventListener("click", () => {
			todos.splice(todos.indexOf(todo), 1);
			updateTodoList();
		});
		// li.addEventListener("click", (e) => {
		//     toggleTodo(todo);
		// });
		if (todo.checked) {
			li.classList.toggle("complete");
		}
		li.appendChild(deleteButton);
		todoList.appendChild(li);
	});
	let todoTexts = todos.map(todo => todo.text);
	let joinedTodoTexts = todoTexts.join(',');
	tags.value = joinedTodoTexts;
	console.log(document.getElementById("tags").value);
};
updateTodoList();



