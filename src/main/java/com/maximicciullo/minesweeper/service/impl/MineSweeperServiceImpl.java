package com.maximicciullo.minesweeper.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximicciullo.minesweeper.entity.Game;
import com.maximicciullo.minesweeper.exception.MinesweeperException;
import com.maximicciullo.minesweeper.helper.MinesweeperHelper;
import com.maximicciullo.minesweeper.model.GameBean;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.Cell;
import com.maximicciullo.minesweeper.model.GameStates;
import com.maximicciullo.minesweeper.model.MarkType;
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

	private Cell matrixBoard[][];

	@Override
	public GameBean createGame(BoardRequest boardRequest) {
		try {
			// Check if the user already have a persisted board game.
			if (gameRepository.findByUserNameAndState(boardRequest.getName(), GameStates.ACTIVE).isPresent()) {
				throw new MinesweeperException(String.format("[Minesweeper Service] - Already exists a game for username=%s", boardRequest.getName()));
			}

			log.info("[Minesweeper Service] Setting mines randomly for game of username={}", boardRequest.getName());
			// Create board of the game
			matrixBoard = minesweeperHelper.initializeBoardGame(boardRequest);
			// Install all mines randomly in the boar
			minesweeperHelper.randomlyLocalelMines(boardRequest, matrixBoard);
			minesweeperHelper.localeMinesArround(boardRequest, matrixBoard);

			// Persisted board game
			Game game = new Game(matrixBoard, boardRequest.getName());
			game = gameRepository.save(game);
			log.info("[Minesweeper Service] - GameBean Game already created for username={}", boardRequest.getName());

			return GameBean.builder().name(game.getUserName()).state(game.getState()).mines(game.getMines()).build();

		} catch(Exception ex) {
			throw new MinesweeperException(String.format("[Minesweeper Service] - Error trying to create a new game for username=%s",
					boardRequest.getName()), ex);
		}
	}

	@Override
	public GameBean getGame(String username) {
		log.info("[Minesweeper Service] - Finding a board game for username={}", username);
		return gameRepository.findByUserNameAndState(username, GameStates.ACTIVE)
				.map(game -> modelMapper.map(game, GameBean.class))
				.orElseThrow(() -> new MinesweeperException(String.format("[Minesweeper Service] - There's no active game for username=%s", username)));
	}

	@Override
	public GameBean play(String username, PlayRequest request) {
		// Getting the play of the user
		int row = request.getRow();
		int column = request.getColumn();

		// Getting the persisted board game if it was saved.
		Optional<Game> game = gameRepository.findByUserNameAndState(username, GameStates.ACTIVE);

		if (!game.isPresent()) {
			throw new MinesweeperException(String.format("[Minesweeper Service] - There's no active game for username=%s", username));
		}
		matrixBoard = game.get().getMines();

		// Logic of the game
		log.info("[Minesweeper Service] - Executing the play of username={} in row={} and column={}", username, row, column);
		if (minesweeperHelper.mineFound(matrixBoard, row, column)) {
			log.info("[Minesweeper Service] - Blows up in row={} and column={} !!!", row, column);
			game.get().setState(GameStates.BLOWUP);
		} else {
			minesweeperHelper.clearEmptySpots(matrixBoard, request.getRow(), request.getColumn(), game.get().getMines().length - 1, game.get().getMines()[0].length - 1);

			if (!minesweeperHelper.mineFound(matrixBoard, row, column)) {
				log.info("[Minesweeper Service] - Mine found in row={} and column={} !!!", row, column);
				matrixBoard[row][column].setRevealed(true);
			}

			game.get().setMines(matrixBoard);

			if (minesweeperHelper.alreadyWon(matrixBoard)) {
				log.info("[Minesweeper Service] - Game Won!!!");
				game.get().setState(GameStates.WON);
			}
		}

		return modelMapper.map(gameRepository.save(game.get()), GameBean.class);
	}

	@Override
	public GameBean mark(String username, PlayRequest request, MarkType markType) {
		Optional<Game> game = gameRepository.findByUserNameAndState(username, GameStates.ACTIVE);

		if (!game.isPresent()) {
			throw new MinesweeperException(String.format("[Minesweeper Service] - There's no active game for username=%s", username));
		}

		if (game.get().getMines()[request.getRow()][request.getColumn()].isRevealed()) {
			throw new MinesweeperException("[Minesweeper Service] - Cell already revealed");
		}

		// Check which kind of mark sent the user
		if (markType.equals(MarkType.REDFLAG)) {
			game.get().setRedFlag(true);
		} else {
			game.get().setQuestionMark(true);
		}

		return modelMapper.map(gameRepository.save(game.get()), GameBean.class);
	}
}
