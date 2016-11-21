$(function($) {

	var $board = $('.board'),
		$status = $('.status');

	function renderGameState(game) {

		var activePlayer = game.activePlayer;

		$board.data('game-id', game.id);

		// shows game id
		$('.game-id-label', $status).text(game.id);

		// shows active player's label
		var $activePlayerSection = $('.active-player-section', $status)
			.toggleClass('hide', game.finished);
		$('span', $activePlayerSection).each(function() {
			var $this = $(this);
			$this.toggleClass('hide', $this.data('player') != activePlayer);
		});

		renderPlayerBoard(game.player1Board, activePlayer, game.finished);
		renderPlayerBoard(game.player2Board, activePlayer, game.finished);

		// shows winner section if game is finished
		var $winnerSection = $('.winner-player-section', $status);
		$winnerSection.toggleClass('hide', !game.finished);
		if (game.finished) {
			$('span', $winnerSection).each(function() {
				var $this = $(this);
				$this.toggleClass('hide', $this.data('player') != game.winner);
			});
		}
	}

	function renderPlayerBoard(playerBoard, activePlayer, isGameFinished) {

		var ownerPlayer = playerBoard.owner;

		var isPlayerBoardElement = function() {
			return $(this).data('player') == ownerPlayer;
		};

		$('.center-section .pit', $board)
			.filter(isPlayerBoardElement)
			.each(function() {

				var $this = $(this),
					pitIndex = $this.data('pit-index');
				var numberOfStones = playerBoard.pits[pitIndex].numberOfStones;

				$('.btn', $this)
					.text(numberOfStones)
					.toggleClass('disabled',
							isGameFinished || numberOfStones < 1 || ownerPlayer != activePlayer);
			});

		$('.pit.house', $board)
			.filter(isPlayerBoardElement)
			.find('.btn')
			.each(function() {
				$(this).text(playerBoard.house.numberOfStones);
			});
	}

	function sow() {

		var $trigger = $(this),
			$pit = $trigger.parent(),
			pitIndex = $pit.data('pit-index'),
			player = $pit.data('player')
			gameId = $board.data('game-id'),
			sowEndpointUrlTemplate = $board.data('sow-endpoint-url-template');
			;

		// disables all button while request is in progress
		// avoids double click problem
		$('.center-section .pit .btn', $board).addClass('disabled');

		var url = sowEndpointUrlTemplate
			.replace('{gameId}', gameId)
			.replace('{playerId}', player)
			.replace('{pitIndex}', pitIndex)
			;

		$.ajax({method: 'PUT', url: url}).done(renderGameState);
	}

	function startNewGame() {

		var $trigger = $(this),
			$boardAndStatus = $board.add($status),
			url = $trigger.data('url');

		$boardAndStatus.addClass('hide');

		$.post(url)
			.done(renderGameState)
			.done(function() {
				$boardAndStatus.removeClass('hide');
			});
	};

	function init() {

		$('.new-game-trigger').click(startNewGame);
		$('.center-section', $board).on('click', '.pit .btn:not(.disabled)', sow);

		$(document).ajaxError(function(event, jqxhr, settings, thrownError) {
			alert('Error ' + jqxhr.status + ' requesting URL "' + settings.url + '"');
		});
	}

	init();
});
