package com.maximicciullo.minesweeper.service;

import com.maximicciullo.minesweeper.model.Board;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.PlayRequest;

public interface MineSweeperService {

	/**
	 * Given a user name, count of rows, count of columns and count mines,
	 * will be create a new minesweeper game.
	 *
	 * @param boardRequest username, rows, columns, mines
	 * @return board
	 */
	Board createGame(BoardRequest boardRequest);

	/**
	 * Obtain a board for a username persisted.
	 *
	 * @param username
	 * @return
	 */
	Board getGame(String username);

	/**
	 * Execute the play of the user.
	 *
	 * @param username
	 * @param request row and column to be discovered
	 * @return board
	 */
	Board play(String username, PlayRequest request);

	/**
	 * Given a row and column mark a cell with a ref flag.
	 *
	 * @param username
	 * @param request row and column to identify the cell
	 * @return board
	 */
	Board redFlag(String username, PlayRequest request);

	/**
	 * Given a row and column mark a cell with a question symbol.
	 *
	 * @param name
	 * @param request
	 * @return
	 */
	Board questionMark(String name, PlayRequest request);
}
