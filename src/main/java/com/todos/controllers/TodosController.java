package com.todos.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.todos.dto.TodoDto;
import com.todos.exceptions.ResourceNotFoundException;
import com.todos.models.Todo;
import com.todos.services.TodosService;

@RestController
@RequestMapping("/api/v2/todos")
public class TodosController {

	//7103cf0f-d445-45f5-9df2-35724f550c6b
	
	@Autowired
	private TodosService todoService;

	// GET Method to get all todos
	// http://localhost:8080/todos

	@GetMapping()
	public List<TodoDto> getAllTodos() {
		List<TodoDto> allTodos = todoService.getAllTodos();

		for (TodoDto todo : allTodos) {
			int todoId = todo.getId();
			Link selfLink = WebMvcLinkBuilder.linkTo(TodosController.class).slash(todoId).withSelfRel();
			todo.add(selfLink);
		}

		return allTodos;
	}

	// GET Method to get all todos related to a particular user
	// http://localhost:8080/todos/user/{name}
	// if the user doesn't exist then it should return HTTP STATUS 404

	@GetMapping("/user/{name}")
	public List<TodoDto> getTodosByUser(@PathVariable String name) {
		List<TodoDto> userTodos = todoService.getTodosByUser(name);
		if (userTodos.isEmpty()) {
			throw new ResourceNotFoundException("Resource Not Found");
		}
		return userTodos;
	}

	// GET Method to get todo based on id
	// http://localhost:8080/todos/{id}
	// if the user doesn't exist then it should return HTTP STATUS 404

	@GetMapping("/{id}")
	public TodoDto getTodoById(@PathVariable int id) {
		TodoDto todo = todoService.getTodoById(id);
		if (todo == null) {
			throw new ResourceNotFoundException("Resource Not Found");
			// throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found")
		}
		return todo;
	}

	// POST Method to Create new Todo
	// http://localhost:8080/todos/
	// if successful creation should return 201 HTTP Created
	// location should be shared in the response header
	@PostMapping()
	public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todo) {
		TodoDto newTodo = todoService.addTodo(todo);
		if (newTodo == null) {
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<TodoDto>(newTodo, HttpStatus.CREATED);

		/*
		 * URI location =
		 * ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand
		 * (newTodo.getId()) .toUri(); return ResponseEntity.created(location).build();
		 */

	}

	// PUT - updates the existing todos based on the user name shared in the path
	// variable
	// localhost:8080/todos/user/{name}/{id}
	// if the name and the todo is not realted it should throw 404 http status code

	@PutMapping("/user/{name}/{id}")
	public ResponseEntity<TodoDto> updateTodo(@PathVariable String name, @PathVariable int id, @Valid @RequestBody TodoDto todo) {
		TodoDto updatedTodo = todoService.updateTodo(name, id, todo);
		if (updatedTodo == null) {
			throw new ResourceNotFoundException("No todo for giving Name and id");
		}
		return new ResponseEntity<TodoDto>(updatedTodo, HttpStatus.OK);
	}

	// DELETE - delete the resource based on id
	// localhost:8080/todos/{id}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTodo(@PathVariable int id) {
		boolean result = todoService.deleteTodo(id);
		if (!result) {
			throw new ResourceNotFoundException("Resource not found for given id" + id);
		}
		return new ResponseEntity<String>("Successfully Deleted Todo", HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

}
