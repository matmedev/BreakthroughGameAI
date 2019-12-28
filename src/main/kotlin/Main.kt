import aima.core.search.adversarial.MinimaxSearch
import org.meightsoft.BreakthroughGame
import org.meightsoft.GameState
import org.meightsoft.Player
import org.meightsoft.Step

fun getStartStateFromFile(path: String): String {
    return object {}.javaClass.getResource(path).readText().replace("\n", "")
}

fun getBoardFromString(str: String, size: Int): Array<Array<Player>> {
    val chars = str.toCharArray()
    val positions = Array(size) { Array(size) { Player.NONE }}
    for (y in 0 until size) {
        for (x in 0 until size) {
            positions[x][y] = when(chars[x * size + y]) {
                'W' -> Player.WHITE
                'B' -> Player.BLACK
                else -> Player.NONE
            }
        }
    }
    return positions
}

fun main() {
    val initBoard = getBoardFromString(getStartStateFromFile("init-8.txt"), 8)
    val board = getBoardFromString(
//        getStartStateFromFile("init-8.txt"),
//        getStartStateFromFile("white-to-win-8.txt"),
        getStartStateFromFile("black-to-win-8.txt"),
        8
    )
    val result = MinimaxSearch<GameState, Step, Player>(
        BreakthroughGame(initBoard, Player.WHITE)
    ).makeDecision(GameState(board, Player.BLACK))
    println(result)
}
