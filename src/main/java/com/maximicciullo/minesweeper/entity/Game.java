package com.maximicciullo.minesweeper.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.maximicciullo.minesweeper.model.Cell;
import com.maximicciullo.minesweeper.model.GameStates;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

@Data
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game {

	@Id
	@GeneratedValue(generator = "id_generator")
	@SequenceGenerator(
			name = "id_generator",
			sequenceName = "id_generator",
			initialValue = 1000
	)
	private long id;

	@Column
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private GameStates state;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	private Cell[][] mines;

	@Column
	private Boolean redFlag;

	public Game(Cell[][] mines, String name) {
		this.mines = mines;
		this.name = name;
		this.state = GameStates.ACTIVE;
		this.redFlag = false;
	}
}
