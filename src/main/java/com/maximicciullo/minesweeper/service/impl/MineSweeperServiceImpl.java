package com.maximicciullo.minesweeper.service.impl;

import org.springframework.stereotype.Service;

import com.maximicciullo.minesweeper.model.Board;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.GameStates;
import com.maximicciullo.minesweeper.model.PlayRequest;
import com.maximicciullo.minesweeper.service.MineSweeperService;

@Service
public class MineSweeperServiceImpl implements MineSweeperService {

	@Override
	public Board createGame(BoardRequest boardRequest) {
		//TODO: Only for test. Should call the logic and then return the Board.
		return Board.builder().name("MaxiGame").state(GameStates.ACTIVE).build();
	}

	@Override
	public Board getGame(String username) {
		//TODO: Only for test. Should call the logic and then return the Board.
		return Board.builder().name("MaxiGame").state(GameStates.BLOWUP).build();
	}

	@Override
	public Board play(String username, PlayRequest request) {
		//TODO: Only for test. Should call the logic and then return the Board.
		return Board.builder().name("MaxiGame").state(GameStates.WON).build();
	}

	@Override
	public Board redFlag(String username, PlayRequest request) {
		//TODO: Only for test. Should call the logic and then return the Board.
		return Board.builder().name("MaxiGame").redFlag(true).questionMark(false).state(GameStates.ACTIVE).build();
	}

	@Override
	public Board questionMark(String name, PlayRequest request) {
		//TODO: Only for test. Should call the logic and then return the Board.
		return Board.builder().name("MaxiGame").questionMark(true).redFlag(false).state(GameStates.ACTIVE).build();
	}
}
