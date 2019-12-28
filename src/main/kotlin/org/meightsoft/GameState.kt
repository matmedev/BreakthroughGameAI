package org.meightsoft

data class GameState(
    val board: Array<Array<Player>>,
    val nextPlayer: Player
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (!board.contentEquals(other.board)) return false
        if (nextPlayer != other.nextPlayer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = board.contentHashCode()
        result = 31 * result + nextPlayer.hashCode()
        return result
    }
}
