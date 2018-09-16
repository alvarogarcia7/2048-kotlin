package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(if (together.size == 3 && together[0] == together[1]) {
            listOf(together.first().next(), together.last())
        } else {
            listOf(together.first().next())
        })
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
