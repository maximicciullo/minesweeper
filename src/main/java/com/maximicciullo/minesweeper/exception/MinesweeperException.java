package com.maximicciullo.minesweeper.exception;

public class MinesweeperException extends RuntimeException {

	public MinesweeperException(String message) {
		super(message);
	}

	public MinesweeperException(String message, Throwable cause) {
		super(message, cause);
	}
}
