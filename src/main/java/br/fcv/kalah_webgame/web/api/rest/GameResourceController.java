package br.fcv.kalah_webgame.web.api.rest;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.fcv.kalah_webgame.core.Game;
import br.fcv.kalah_webgame.core.GameService;
import br.fcv.kalah_webgame.core.Player;

@RestController
@RequestMapping("/api/rest/v1/games")
public class GameResourceController {

	private static final Logger logger = getLogger(GameResourceController.class);

	private GameService service;

	public GameResourceController(GameService service) {
		this.service = checkNotNull(service, "service cannot be null");
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Game create() {
		logger.debug("create()");
		return service.newGame();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Game> get(@PathVariable("id") Long id) {
		logger.debug("get(id: {})", id);
		return service.tryGet(id)
			.map(game -> ResponseEntity.ok(game))
			.orElse(new ResponseEntity<Game>(NOT_FOUND));
	}

	@PutMapping("/{id}/sow")
	@ResponseStatus(OK)
	public Game sow(@PathVariable("id") Long id,
			@RequestParam("player") Player player,
			@RequestParam("pitIndex") int sourcePitIndex) {

		logger.debug("sow(id: {}, player: {}, sourcePitIndex: {})", id, player, sourcePitIndex);
		Game game = service.sow(id, player, sourcePitIndex);
		return game;
	}
}
