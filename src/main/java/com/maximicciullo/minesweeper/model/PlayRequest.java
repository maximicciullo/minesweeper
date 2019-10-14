package com.maximicciullo.minesweeper.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayRequest {
	@NotNull
	private int column;

	@NotNull
	private int row;
}
