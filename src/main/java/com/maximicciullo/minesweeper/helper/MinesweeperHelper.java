package com.maximicciullo.minesweeper.helper;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.Cell;

@Slf4j
@Component
public class MinesweeperHelper {

	/**
	 * Create Board for minesweeper game
	 *
	 * @param boardRequest
	 * @return
	 */
	public Cell[][] initializeBoardGame(BoardRequest boardRequest) {
		Cell matrix[][];
		matrix = new Cell[boardRequest.getRows()][];
		for (int i=0 ; i < boardRequest.getRows(); i++) {
			matrix[i] = new Cell[boardRequest.getColumns()];
			for (int j = 0; j < boardRequest.getColumns(); j++) {
				matrix[i][j] = new Cell();
			}
		}
		log.info("[MinesweeperHelper] - A new game was initialized with rows={}, columns={}, mines={} for usaername={}",
				boardRequest.getRows(), boardRequest.getColumns(), boardRequest.getMines(), boardRequest.getName());
		return matrix;
	}

	/**
	 * Randomly install all mines in the board of the minesweeper game
	 *
	 * @param boardRequest
	 * @param matrix
	 */
	public void randomlyLocalelMines(BoardRequest boardRequest, Cell[][] matrix) {
		int minesPlaced = 0;
		Random random = new Random();
		while(minesPlaced < boardRequest.getMines()) {
			int x = random.nextInt(boardRequest.getRows());
			int y = random.nextInt(boardRequest.getColumns());
			if(!matrix[y][x].isMine()) {
				matrix[y][x].setMine(true);
				minesPlaced ++;
			}
		}
		log.info("[MinesweeperHelper] - Already installed mines for game of username={}", boardRequest.getName());
	}

	/**
	 * Locate positions for all mines around
	 *
	 * @param boardRequest
	 * @param matrix
	 */
	public void localeMinesArround(BoardRequest boardRequest, Cell[][] matrix) {
		for (int x=0; x < boardRequest.getRows(); x ++) {
			for (int y= 0; y < boardRequest.getColumns(); y++) {
				matrix[x][y].setMinesAround(minesNear(matrix, x, y));
			}
		}
	}

	private int minesNear(Cell[][] matrix, int x, int y) {
		int mines = 0;
		mines += mineAt(matrix, y - 1, x - 1);
		mines += mineAt(matrix, y - 1, x);
		mines += mineAt(matrix,y - 1, x + 1);
		mines += mineAt(matrix, y,x - 1);
		mines += mineAt(matrix, y, x + 1);
		mines += mineAt(matrix, y + 1, x - 1);
		mines += mineAt(matrix, y + 1, x);
		mines += mineAt(matrix,y + 1, x + 1);
		return mines;
	}

	private int mineAt(Cell[][] matrix, int y, int x) {
		if(y >= 0 && y < matrix[0].length && x >= 0 && x < matrix.length && matrix[y][x].isMine()) {
			return 1;
		} else {
			return 0;
		}
	}
}
