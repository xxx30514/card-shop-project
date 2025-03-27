package com.myproject.cardshop.handler;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 使null與空集合等不會被序列化為JSON 提升效能
public class ExceptionResponse {

	private Integer errorCode;

	private String errorDescription;
	
	private String error;
	
	private Set<String> validationErrors;
	
	private Map<String, String> errors;
}
