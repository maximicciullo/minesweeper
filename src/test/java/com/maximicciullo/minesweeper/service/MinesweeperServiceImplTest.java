package com.maximicciullo.minesweeper.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.maximicciullo.minesweeper.entity.Game;
import com.maximicciullo.minesweeper.exception.MinesweeperException;
import com.maximicciullo.minesweeper.model.BoardRequest;
import com.maximicciullo.minesweeper.model.Cell;
import com.maximicciullo.minesweeper.model.GameBean;
import com.maximicciullo.minesweeper.model.GameStates;
import com.maximicciullo.minesweeper.model.MarkType;
import com.maximicciullo.minesweeper.model.PlayRequest;
import com.maximicciullo.minesweeper.repository.GameRepository;
import com.maximicciullo.minesweeper.service.impl.MineSweeperServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MineSweeperServiceImpl.class})
public class MinesweeperServiceImplTest {

	@Autowired
	private MineSweeperServiceImpl minesweeperServiceImpl;

	@MockBean
	private GameRepository gameRepository;

	@MockBean
	private ModelMapper modelMapper;

	private Game game;
	private BoardRequest request;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		Cell[][] matrix = new Cell[2][2];
		game = Game.builder().id(321L).userName("MinesweeperTest").redFlag(false).mines(matrix).build();

		request = BoardRequest.builder().columns(5).rows(5).mines(5).name("MinesweeperTest").build();
	}

	@Test
	public void testCreateGameSuccessful() {
		BoardRequest request = BoardRequest.builder().columns(5).rows(5).mines(5).name("MinesweeperTest").build();
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.empty());
		when(gameRepository.save(any(Game.class))).thenReturn(game);
		minesweeperServiceImpl.createGame(request);

		verify(gameRepository, times(1)).save(any(Game.class));
	}

	@Test(expected = MinesweeperException.class)
	public void testCreateGameNameAlreadyExists() {
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE))
				.thenReturn(Optional.of(new Game()));

		minesweeperServiceImpl.createGame(request);
	}

	@Test
	public void testGetGame() {
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.of(game));
		when(modelMapper.map(game, GameBean.class)).thenReturn(GameBean.builder().build());
		assertThat(minesweeperServiceImpl.getGame(request.getName())).isNotNull();
	}

	@Test(expected = MinesweeperException.class)
	public void testPlayGameNotFound() {
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.empty());
		minesweeperServiceImpl.play(request.getName(), PlayRequest.builder().column(0).row(0).build());
	}

	@Test(expected = MinesweeperException.class)
	public void testGetGameNotFound() {
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.empty());
		minesweeperServiceImpl.getGame(request.getName());
	}

	@Test
	public void testMarkFlag() {
		game.getMines()[0][0] = new Cell();
		game.getMines()[0][0].setRevealed(false);
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.of(game));
		when(gameRepository.save(any(Game.class))).thenReturn(game);
		when(modelMapper.map(game, GameBean.class)).thenReturn(GameBean.builder().build());
		GameBean result = minesweeperServiceImpl.mark(request.getName(), PlayRequest.builder().column(0).row(0).build(), MarkType.REDFLAG);
		assertThat(result).isNotNull();
	}

	@Test(expected = MinesweeperException.class)
	public void testMarkFlagGameNotFound() {
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.empty());
		minesweeperServiceImpl.mark(request.getName(), PlayRequest.builder().column(0).row(0).build(), MarkType.QUESTION);
	}

	@Test(expected = MinesweeperException.class)
	public void testMarkFlagGameAlreadyRevealed() {
		game.getMines()[0][0] = new Cell();
		game.getMines()[0][0].setRevealed(true);
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.of(game));
		minesweeperServiceImpl.mark(request.getName(), PlayRequest.builder().column(0).row(0).build(), MarkType.QUESTION);
	}

	@Test
	public void testPlayWon() {
		game.getMines()[0][0] = new Cell(false);
		game.getMines()[0][1] = new Cell(true);
		game.getMines()[1][0] = new Cell(true);
		game.getMines()[1][1] = new Cell(true);
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.of(game));
		minesweeperServiceImpl.play(request.getName(), PlayRequest.builder().column(0).row(0).build());

		ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
		verify(gameRepository, times(1)).save(captor.capture());
		assertThat(captor.getValue().getState()).isEqualTo(GameStates.WON);
	}

	@Test
	public void testPlayBlowUp() {
		game.getMines()[0][0] = new Cell(true);
		when(gameRepository.findByUserNameAndState(request.getName(), GameStates.ACTIVE)).thenReturn(Optional.of(game));
		minesweeperServiceImpl.play(request.getName(), PlayRequest.builder().column(0).row(0).build());

		ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
		verify(gameRepository, times(1)).save(captor.capture());
		assertThat(captor.getValue().getState()).isEqualTo(GameStates.BLOWUP);
	}
}

