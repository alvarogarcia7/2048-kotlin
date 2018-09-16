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

    fun right(): Board {
        var result = together.toMutableList()
        var i = result.size - 1
        while (i > 0) {
            if (result[i] == result[i - 1]) {
                result[i] = result[i].next()
                result.removeAt(i - 1)
            }
            i--
        }
        return Board(result)
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
