package com.maximicciullo.minesweeper.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cell implements Serializable {

	private boolean revealed;
	private int minesAround;
	private boolean mine;
	private boolean redFlag;
	private boolean questionMark;

	public Cell(boolean mine) {
		this.mine = mine;
	}
}
