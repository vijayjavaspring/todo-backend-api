package com.todos.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.todos.dto.TodoDto;
import com.todos.models.Todo;

public class TodoDtoMapper {

	public static Todo convertDtoToEntity(TodoDto todoDto) {
		Todo todo = new Todo();
		todo.setDescription(todoDto.getDescription());
		todo.setUser(todoDto.getUser());
		todo.setTargetDate(todoDto.getTargetDate());
		if (todoDto.getStatus() != null && todoDto.getStatus().equals("Completed")) {
			todo.setDone(true);
		} else {
			todo.setDone(false);
		}
		return todo;
	}

	public static TodoDto convertEntityToDto(Todo todo) {
		TodoDto todoDto = new TodoDto();
		todoDto.setId(todo.getId());
		todoDto.setDescription(todo.getDescription());
		todoDto.setUser(todo.getUser());
		todoDto.setTargetDate(todo.getTargetDate());
		if(todo.isDone()) {
			todoDto.setStatus("Completed");
		}else
			todoDto.setStatus("Not Completed");
		return todoDto;
	}
	
	public static List<TodoDto> convertListEntitytoDtos(List<Todo> todos){
		List<TodoDto> todoDtos = new ArrayList<>();
		for(Todo todo:todos) {
			TodoDto todoDto = TodoDtoMapper.convertEntityToDto(todo);
			todoDtos.add(todoDto);
		}
		return todoDtos;
	}

}
