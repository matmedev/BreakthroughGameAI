package org.meightsoft

import aima.core.search.adversarial.Game

data class BreakthroughGame(
    val board: Array<Array<Player>>,
    val player: Player
): Game<GameState, Step, Player> {

    override fun getInitialState(): GameState {
        return GameState(board, player)
    }

    override fun getResult(state: GameState?, action: Step?): GameState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPlayer(state: GameState?): Player {
        return player
    }

    override fun getPlayers(): Array<Player> {
        return arrayOf(
            Player.WHITE,
            Player.BLACK
        )
    }

    override fun getActions(state: GameState?): MutableList<Step> {
    }

    override fun getUtility(state: GameState?, player: Player?): Double {
        // RETURN the
        // - distance between the furthermost position and the opponent's home row
    }

    override fun isTerminal(state: GameState?): Boolean {
        val board = state!!.board
        // if one of the players has a position in their opponent's home row
        if(board[0].contains(Player.WHITE) || board[board.size - 1].contains(Player.BLACK)) {
            return true
        }
        var hasWhite = false
        var hasBlack = false
        for(row in board) {
            for(position in row) {
                if(position === Player.BLACK) {
                    hasBlack = true
                } else if(position === Player.WHITE) {
                    hasWhite = true
                }

                if(hasWhite && hasBlack) {
                    return false
                }
            }
        }
        // one of the players has no positions in their color
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BreakthroughGame

        if (!board.contentEquals(other.board)) return false
        if (player != other.player) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentHashCode()
        result = 31 * result + player.hashCode()
        return result
    }

}
