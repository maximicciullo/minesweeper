package com.maximicciullo.minesweeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximicciullo.minesweeper.model.MineSweeperWelcomeBean;

@RestController
@RequestMapping("/mineswepper/v1")
@Validated
public class MineSwepperController {

	@GetMapping(value="/welcome")
	public ResponseEntity welcome() {
		try {
			MineSweeperWelcomeBean welcome = new MineSweeperWelcomeBean();
			return ResponseEntity.ok(welcome.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
