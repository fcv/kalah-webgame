package br.fcv.kalah_webgame.core;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;
import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

/**
 * Represents one player's board "sections" (pits and house) in the
 * whole Game's board.
 *
 * @author veronez
 */
@Entity
@Table(name = "player_board")
public class PlayerBoard implements Serializable {

	private static final long serialVersionUID = -4892742656533366244L;

	@Id
	@GeneratedValue(strategy = AUTO)
	private Long id;

	/**
	 * The player to whon this board part belongs to
	 */
	@Column(name = "owner", nullable = false)
	public Player owner;

	/**
	 * PlayerBoard's "regular" pits within game's board
	 */
	@ElementCollection
	@CollectionTable(name = "pit", joinColumns = @JoinColumn(name = "player_board_id"))
	@OrderColumn(name = "index")
	private List<Pit> pits;

	/**
	 * PlayerBoard's house (kalah) pit
	 */
	@Embedded
	@AttributeOverride(name = "numberOfStones", column = @Column(name = "house_number_of_stones", nullable = false))
	@OrderColumn(name = "index")
	private Pit house;

	public PlayerBoard(Player owner) {

		this.owner = owner;
		// player is initialized with 6 pits
		pits = IntStream.range(0, Game.NUMBER_OF_PITS)
			.mapToObj(i -> new Pit())
			.collect(toCollection(ArrayList::new));

		// PlayerBoard's house is initialized with no stone
		house = new Pit(0);
	}

	public List<Pit> getPits() {
		return pits;
	}

	/**
	 * Modifies number of stones on this player's pits.
	 *
	 * It is expected to be used only by test classes. Thus its visibility level
	 * is set to package-private (default)
	 */
	void setNumberOfStones(int... numberOfStones) {
		checkNotNull(numberOfStones, "numberOfStones cannot be null");
		List<Pit> pits = getPits();
		checkState(
				numberOfStones.length == pits.size(),
				"numberOfStones's length (%s) should be equals to pits's size (%s)",
				numberOfStones.length, pits.size());

		for (int i = 0; i < numberOfStones.length; i++) {
			int n = numberOfStones[i];
			pits.get(i).setNumberOfStones(n);
		}
	}

	public Pit getHouse() {
		return house;
	}

	/**
	 * Return <code>true</code> whether all this player's pits, besides its house,
	 * are empty.
	 * 
	 * @return
	 */
	public boolean isOutOfStones() {
		return getPits().stream().noneMatch(p -> p.getNumberOfStones() > 0);
	}

	public String toString() {
		return format("{id: %s, pits: %s, house: %s}", id, pits, house);
	}
}
