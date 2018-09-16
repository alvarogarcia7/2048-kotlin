package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(listOf(Tile(2)))
    }

}

data class Tile(val value: Int)
