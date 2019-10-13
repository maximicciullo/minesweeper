package com.maximicciullo.minesweeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mineswepper/v1")
@Validated
public class MineSwepperController {

	@PostMapping(value="/game")
	public ResponseEntity newGame() {
		try {
			// Get number of Mines, number of Columns and number of Rows.
			// Validate if quantity of Mines are valid in the matrix.

			// Call the service in order to create the initial game.
			return ResponseEntity.status(HttpStatus.CREATED).body("Game Successfully create .. time to play!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PostMapping(value = "/game/{userName}")
	public ResponseEntity playGame(@PathVariable String userName) {
		try {
			// Get user name in the url path
			// Call the service in order to execute the movement with row, column to that game.
			return ResponseEntity.ok("Played with row and column sent them by request");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping(value = "/game/{userName}/flag")
	public ResponseEntity redFlag() {
		try {
			// Get user name in the url path
			// Call the service in order to set the flag in row and column in the user's game.
			return ResponseEntity.ok("Settled flag in row and column sent them by request");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping(value = "/game/{userName}/question")
	public ResponseEntity questionMark() {
		try {
			// Get user name in the url path
			// Call the service in order to set the question symbol in row and column in the user's game.
			return ResponseEntity.ok("Settled question symbol in row and column sent them by request");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
