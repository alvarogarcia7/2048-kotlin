package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(listOf(together.first().next()))
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
