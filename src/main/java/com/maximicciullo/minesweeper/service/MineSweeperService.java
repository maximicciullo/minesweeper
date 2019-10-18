package com.maximicciullo.minesweeper.service;

import com.maximicciullo.minesweeper.model.GameBean;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.MarkType;
import com.maximicciullo.minesweeper.model.PlayRequest;

public interface MineSweeperService {

	/**
	 * Given a user name, count of rows, count of columns and count mines,
	 * will be create a new minesweeper game.
	 *
	 * @param boardRequest username, rows, columns, mines
	 * @return board
	 */
	GameBean createGame(BoardRequest boardRequest);

	/**
	 * Obtain a board for a username persisted.
	 *
	 * @param username
	 * @return
	 */
	GameBean getGame(String username);

	/**
	 * Execute the play of the user.
	 *
	 * @param username
	 * @param request row and column to be discovered
	 * @return board
	 */
	GameBean play(String username, PlayRequest request);

	/**
	 * Given a row and column mark a cell with a question symbol or a red flag.
	 *
	 * @param userName
	 * @param request
	 * @return
	 */
	GameBean mark(String userName, PlayRequest request, MarkType markType);
}
