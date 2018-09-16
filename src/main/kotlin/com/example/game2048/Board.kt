package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {

        var result = together.toMutableList()
        var i = 1
        while (i < result.size) {
            if (result[i] == result[i - 1]) {
                result[i - 1] = result[i - 1].next()
                result.removeAt(i)
            }
            i++
        }
        return Board(result)
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
