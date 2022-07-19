package com.todos.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.todos.dto.TodoDto;
import com.todos.dto.mapper.TodoDtoMapper;
import com.todos.models.Todo;
import com.todos.repositories.TodosRepository;
import com.todos.services.TodosService;

@Service
public class TodoServiceDatabaseImpl implements TodosService {
	
	@Autowired
	private TodosRepository todosRepository;

	@Override
	public List<TodoDto> getAllTodos() {
		// User the repository to get all the todos
		return TodoDtoMapper.convertListEntitytoDtos(todosRepository.findAll(Sort.by(Sort.Direction.ASC, "user")));
		
	}

	@Override
	public List<TodoDto> getTodosByUser(String user) {
		return TodoDtoMapper.convertListEntitytoDtos(todosRepository.findByUser(user));
	}

	@Override
	public TodoDto getTodoById(int id) {
		//use the default findbyId method to get by id the todos
		Optional<Todo> todo = todosRepository.findById(id);
		if(todo.isPresent())
			return TodoDtoMapper.convertEntityToDto(todo.get());
		return null;
	}

	@Override
	public TodoDto addTodo(TodoDto todoDto) {
		//Save the value to the db using repository save method
		Todo newCreateTodo = todosRepository.save(TodoDtoMapper.convertDtoToEntity(todoDto));
		return TodoDtoMapper.convertEntityToDto(newCreateTodo);
	}

	@Override
	public TodoDto updateTodo(String name, int id, TodoDto todo) {
		Optional<Todo> existingTodo = todosRepository.findById(id);
		if(existingTodo.isPresent() && existingTodo.get().getUser().equals(name)){
			Todo updatedTodo = existingTodo.get();
			updatedTodo.setDescription(todo.getDescription());
			updatedTodo.setTargetDate(todo.getTargetDate());
			//updatedTodo.setDone(todo.isDone());
			//save to db
			todosRepository.save(updatedTodo);
			return null;
		}
		return null;
	}

	@Override
	public boolean deleteTodo(int id) {
		Optional<Todo> existingTodo = todosRepository.findById(id);
		if(existingTodo.isPresent()) {
			todosRepository.deleteById(id);
			return true;
		}	
		return false;
	}

}
