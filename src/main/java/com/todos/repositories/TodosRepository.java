package com.todos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todos.models.Todo;

public interface TodosRepository extends JpaRepository<Todo, Integer>{
	List<Todo> findByUser(String user);
	

}
