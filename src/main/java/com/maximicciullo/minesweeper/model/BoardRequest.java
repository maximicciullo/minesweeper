package com.maximicciullo.minesweeper.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoardRequest {
	@NotEmpty
	private String name;

	@NotNull
	@Min(2)
	@Max(50)
	private int columns;

	@NotNull
	@Min(2)
	@Max(50)
	private int rows;

	@NotNull
	@Min(1)
	@Max(2401) // (x-1)(y-1) where x is max columns and y is max rows. (https://en.wikipedia.org/wiki/Microsoft_Minesweeper)
	private int mines;
}
