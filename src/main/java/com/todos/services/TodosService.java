package com.todos.services;

import java.util.List;

import com.todos.dto.TodoDto;
import com.todos.models.Todo;

public interface TodosService {
	
	List<TodoDto> getAllTodos();
	
	List<TodoDto> getTodosByUser(String user);
	
	TodoDto getTodoById(int id);
	
	TodoDto addTodo(TodoDto todo);
	
	TodoDto updateTodo(String name, int id, TodoDto todo);
	
	boolean deleteTodo(int id);

}
