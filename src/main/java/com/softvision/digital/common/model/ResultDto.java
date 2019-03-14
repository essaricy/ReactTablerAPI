package com.softvision.digital.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {

	public static final String SUCCESS = "SUCCESS";

	public static final String FAILURE = "FAILURE";

	@NotNull(message = "Result Code cannot be null")
	private String code;

	private String message;

	private Object content;

}