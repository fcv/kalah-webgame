package br.fcv.kalah_webgame.web.api.rest;

import static br.fcv.kalah_webgame.core.Player.PLAYER_1;
import static java.lang.Math.random;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import br.fcv.kalah_webgame.core.Game;
import br.fcv.kalah_webgame.core.GameRepository;

import com.google.common.collect.ImmutableList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameResourceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private GameRepository gameRepository;

	@Autowired
	private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

	@Before
	public void mockRepository() throws Exception {

		ImmutableList<Game> games = ImmutableList.of(new Game(1L));

		when(gameRepository.tryFind(any(Long.class))).thenAnswer(
				invocation -> {
					Long id = invocation.getArgumentAt(0, Long.class);
					Optional<Game> result = games.stream()
							.filter(g -> id.equals(g.getId()))
							.findAny();
					return result;
				});

		when(gameRepository.findExpected(any(Long.class))).thenAnswer(
				invocation -> {
					Long id = invocation.getArgumentAt(0, Long.class);
					Game result = games.stream()
							.filter(g -> id.equals(g.getId()))
							.findAny()
							.orElseThrow(EntityNotFoundException::new);
					return result;
				});

		when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
			Game game = invocation.getArgumentAt(0, Game.class);
			Long id = (long) (random() * 1000);
			game.setId(id);
			return game;
		});
	}

	@Test
	public void testGetOne() throws Exception {

		mvc.perform(get("/api/rest/v1/games/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(
						content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.activePlayer", equalTo("PLAYER_1")))
				.andExpect(jsonPath("$.winner", nullValue()))
				.andExpect(jsonPath("$.finished", is(false)))
				.andExpect(
						jsonPath("$.player1Board.owner", equalTo("PLAYER_1")))
				.andExpect(
						jsonPath("$.player1Board.house.numberOfStones", is(0)))
				.andExpect(
						jsonPath("$.player1Board.pits[*].numberOfStones",
								contains(6, 6, 6, 6, 6, 6)))
				.andExpect(
						jsonPath("$.player2Board.owner", equalTo("PLAYER_2")))
				.andExpect(
						jsonPath("$.player2Board.pits[*].numberOfStones",
								contains(6, 6, 6, 6, 6, 6)))
				.andExpect(
						jsonPath("$.player2Board.house.numberOfStones", is(0)));

		mvc.perform(get("/api/rest/v1/games/{id}", 671)).andExpect(
				status().isNotFound());
	}

	@Test
	public void testPostOne() throws Exception {

		mvc.perform(post("/api/rest/v1/games"))
				.andExpect(status().isCreated())
				.andExpect(
						content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", isA(Number.class)))
				.andExpect(jsonPath("$.activePlayer", equalTo("PLAYER_1")))
				.andExpect(jsonPath("$.winner", nullValue()))
				.andExpect(jsonPath("$.finished", is(false)))
				.andExpect(
						jsonPath("$.player1Board.owner", equalTo("PLAYER_1")))
				.andExpect(
						jsonPath("$.player1Board.house.numberOfStones", is(0)))
				.andExpect(
						jsonPath("$.player1Board.pits[*].numberOfStones",
								contains(6, 6, 6, 6, 6, 6)))
				.andExpect(
						jsonPath("$.player2Board.owner", equalTo("PLAYER_2")))
				.andExpect(
						jsonPath("$.player2Board.pits[*].numberOfStones",
								contains(6, 6, 6, 6, 6, 6)))
				.andExpect(
						jsonPath("$.player2Board.house.numberOfStones", is(0)));
	}

	@Test
	public void testSow() throws Exception {

		RequestBuilder put = put("/api/rest/v1/games/{id}/sow", 1L)
				.param("player", PLAYER_1.name())
				.param("pitIndex", "1");

		mvc.perform(put)
				.andExpect(status().isOk())
				.andExpect(
						content().contentTypeCompatibleWith(APPLICATION_JSON))
				.andExpect(jsonPath("$.id", isA(Number.class)))
				.andExpect(jsonPath("$.activePlayer", equalTo("PLAYER_2")))
				.andExpect(jsonPath("$.winner", nullValue()))
				.andExpect(jsonPath("$.finished", is(false)))
				.andExpect(
						jsonPath("$.player1Board.owner", equalTo("PLAYER_1")))
				.andExpect(
						jsonPath("$.player1Board.house.numberOfStones", is(1)))
				.andExpect(
						jsonPath("$.player1Board.pits[*].numberOfStones",
								contains(6, 0, 7, 7, 7, 7)))
				.andExpect(
						jsonPath("$.player2Board.owner", equalTo("PLAYER_2")))
				.andExpect(
						jsonPath("$.player2Board.pits[*].numberOfStones",
								contains(7, 6, 6, 6, 6, 6)))
				.andExpect(
						jsonPath("$.player2Board.house.numberOfStones", is(0)));
	}
}
