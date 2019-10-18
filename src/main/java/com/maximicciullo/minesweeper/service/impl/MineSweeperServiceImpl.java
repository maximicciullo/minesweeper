package com.maximicciullo.minesweeper.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximicciullo.minesweeper.entity.Game;
import com.maximicciullo.minesweeper.exception.MinesweeperException;
import com.maximicciullo.minesweeper.helper.MinesweeperHelper;
import com.maximicciullo.minesweeper.model.Board;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.Cell;
import com.maximicciullo.minesweeper.model.GameStates;
import com.maximicciullo.minesweeper.model.PlayRequest;
import com.maximicciullo.minesweeper.repository.GameRepository;
import com.maximicciullo.minesweeper.service.MineSweeperService;

@Slf4j
@Service
public class MineSweeperServiceImpl implements MineSweeperService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MinesweeperHelper minesweeperHelper;

	private Cell matrix[][];

	@Override
	public Board createGame(BoardRequest boardRequest) {
		try {
			// Check if the user already have a persisted board game.
			if (gameRepository.findByUserNameAndState(boardRequest.getName(), GameStates.ACTIVE).isPresent()) {
				throw new MinesweeperException(String.format("[Minesweeper Service] - Already exists a game for username=%s", boardRequest.getName()));
			}

			log.info("[Minesweeper Service] Setting mines randomly for game of username={}", boardRequest.getName());
			// Create board of the game
			matrix = minesweeperHelper.initializeBoardGame(boardRequest);
			// Install all mines randomly in the boar
			minesweeperHelper.randomlyLocalelMines(boardRequest, matrix);
			minesweeperHelper.localeMinesArround(boardRequest, matrix);

			// Persisted board game
			Game game = new Game(matrix, boardRequest.getName());
			game = gameRepository.save(game);
			log.info("[Minesweeper Service] - Board Game already created for username={}", boardRequest.getName());

			return Board.builder().name(game.getUserName()).state(game.getState()).mines(game.getMines()).build();

		} catch(Exception ex) {
			throw new MinesweeperException(String.format("[Minesweeper Service] - Error trying to create a new game for username=%s",
					boardRequest.getName()), ex);
		}
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
