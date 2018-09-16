package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {

        return Board(together.zipWithNext().flatMap { tile ->
            if (tile.first == tile.second) {
                listOf(tile.first.next())
            } else {
                listOf(tile.second)
            }
        })
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
