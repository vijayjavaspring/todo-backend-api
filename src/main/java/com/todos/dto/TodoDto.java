package com.todos.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import com.todos.models.Todo;

import lombok.Data;

@Data
public class TodoDto extends RepresentationModel<TodoDto> {

	private int id;

	@NotBlank(message = "user is required")

	private String user;

	@Size(min = 9, max = 20, message = "length should be more than 9 character and less than 20")

	private String description;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

	private Date targetDate;

	private String status;

}
