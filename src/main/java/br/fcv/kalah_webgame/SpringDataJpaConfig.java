package br.fcv.kalah_webgame;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.fcv.kalah_webgame.core.support.spring_data.FindExpectedRepositoryImpl;

/**
 * Register {@link FindExpectedRepositoryImpl} custom Spring Data's Repository
 * implementatin
 * 
 * @author veronez
 *
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = FindExpectedRepositoryImpl.class)
public class SpringDataJpaConfig {

}
