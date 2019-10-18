package com.maximicciullo.minesweeper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameBean {
	private String name;
	private GameStates state;
	private Cell[][] mines;
	private boolean redFlag;
	private boolean questionMark;
}
