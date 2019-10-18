package com.maximicciullo.minesweeper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maximicciullo.minesweeper.entity.Game;
import com.maximicciullo.minesweeper.model.GameStates;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	Optional<Game> findByUserNameAndState(String useerName, GameStates State);

}
