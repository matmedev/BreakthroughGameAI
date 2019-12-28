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
        if (state !== null && action !== null) {
            state.board[action.from.y][action.from.x] = Player.NONE
            state.board[action.to.y][action.to.x] = state.nextPlayer
            state.nextPlayer = when(state.nextPlayer) {
                Player.BLACK -> Player.WHITE
                Player.WHITE -> Player.BLACK
                else -> Player.WHITE // cannot really happen
            }
        }
        return initialState
    }

    override fun getPlayer(state: GameState?): Player {
        return state!!.nextPlayer
    }

    override fun getPlayers(): Array<Player> {
        return arrayOf(
            Player.WHITE,
            Player.BLACK
        )
    }

    override fun getActions(state: GameState?): MutableList<Step> {
        val steps = mutableListOf<Step>()

        val board = state!!.board
        val next = state.nextPlayer
        for((y, row) in board.withIndex()) {
            for((x, pos) in row.withIndex()) {
                if (pos === next) {
                    val nextY = when(next) {
                        Player.WHITE -> y - 1
                        Player.BLACK -> y + 1
                        else -> -1
                    }
                    if (x - 1 >= 0 && board[nextY][x - 1] !== next) {
                        steps.add(Step(x, y, x - 1, nextY))
                    }
                    if (board[nextY][x] === Player.NONE) {
                        steps.add(Step(x, y, x, nextY))
                    }
                    if (x + 1 < board[y].size && board[nextY][x + 1] !== next) {
                        steps.add(Step(x, y, x + 1, nextY))
                    }
                }
            }
        }

        return steps
    }

    override fun getUtility(state: GameState?, player: Player?): Double {
        // returns the distance between the furthermost position and the opponent's home row
        val board = state!!.board
        if(player === Player.BLACK) {
            for(i in board.size - 1 downTo 0) {
                if(board[i].contains(Player.BLACK)) {
                    return i * 1.0
                }
            }
        } else if (player === Player.WHITE) {
            for(i in 0..board.size) {
                if(board[i].contains(Player.WHITE)) {
                    return (board.size - i) * 1.0
                }
            }

        }
        return -1.0
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
