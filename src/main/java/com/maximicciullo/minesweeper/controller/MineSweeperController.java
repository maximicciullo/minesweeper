package com.maximicciullo.minesweeper.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.PlayRequest;
import com.maximicciullo.minesweeper.service.MineSweeperService;

@RestController
@RequestMapping("/minesweeper/v1")
@Validated
public class MineSweeperController {

	@Autowired
	private MineSweeperService mineSweeperService;

	@PostMapping(value="/game", consumes = "application/json")
	public ResponseEntity newGame(@Valid @RequestBody BoardRequest request) {
		try {
			// Get number of Mines, number of Columns and number of Rows.
			// Validate if quantity of Mines are valid in the matrix.
			if (request.getMines() > request.getColumns() * request.getRows()) {
				return ResponseEntity.badRequest().body("to many mines");
			}

			// Call the service in order to create the initial game.
			return ResponseEntity.status(HttpStatus.CREATED).body(mineSweeperService.createGame(request));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}

	@PostMapping(value = "/game/{userName}/play",  consumes = "application/json")
	public ResponseEntity playGame(@Valid @RequestBody PlayRequest request, @PathVariable String userName) {
		try {
			// Get user name in the url path
			// Call the service in order to execute the movement with row, column to that game.
			return ResponseEntity.ok(mineSweeperService.play(userName, request));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping(value="/game/{userName}")
	public ResponseEntity getGames(@PathVariable String userName) {
		try {
			// Get a stores board game for an user.
			return ResponseEntity.ok(mineSweeperService.getGame(userName));
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping(value = "/game/{userName}/flag", consumes = "application/json")
	public ResponseEntity redFlag(@Valid @RequestBody PlayRequest request, @PathVariable String userName) {
		try {
			// Get user name in the url path
			// Call the service in order to set the flag in row and column in the user's game.
			return ResponseEntity.ok(mineSweeperService.redFlag(userName, request));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping(value = "/game/{userName}/question",  consumes = "application/json")
	public ResponseEntity questionMark(@Valid @RequestBody PlayRequest request, @PathVariable String userName) {
		try {
			// Get user name in the url path
			// Call the service in order to set the question symbol in row and column in the user's game.
			return ResponseEntity.ok(mineSweeperService.questionMark(userName, request));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
