package com.maximicciullo.minesweeper.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class MineSweeperWelcomeBean {
	private String message = "Welcome to Minesweeper game ...";

	public String getMessage() {
		return message;
	}
}
