package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameServiceBean implements GameService {

	private static final Logger logger = getLogger(GameServiceBean.class);

	private final GameRepository repository;

	@Inject
	public GameServiceBean(GameRepository repository) {
		this.repository = checkNotNull(repository, "repository cannot be null");
	}

	@Override
	public Game newGame() {
		logger.debug("newGame()");
		Game game = new Game();
		game = repository.save(game);
		return game;
	}

	@Override
	@Transactional(readOnly = true)
	public Game get(Long id) {
		logger.debug("get(id: {})", id);
		return repository.findExpected(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Game> tryGet(Long id) {
		logger.debug("tryGet(id: {})", id);
		return repository.tryFind(id);
	}

}
