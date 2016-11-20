package br.fcv.kalah_webgame.core;

import org.springframework.data.repository.CrudRepository;

import br.fcv.kalah_webgame.core.support.spring_data.FindExpectedRepositoryCustom;

public interface GameRepository extends CrudRepository<Game, Long>, FindExpectedRepositoryCustom<Game, Long> {
}
