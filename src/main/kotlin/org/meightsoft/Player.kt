package org.meightsoft

enum class Player {
    BLACK,
    WHITE,
    NONE;

    override fun toString(): String {
        return this.name
    }
}
