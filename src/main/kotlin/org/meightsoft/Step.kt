package org.meightsoft

data class Step(
    val from: Position,
    val to: Position
) {
    constructor(fromX: Int, fromY: Int, toX: Int, toY: Int): this(Position(fromX, fromY), Position(toX, toY))
}
