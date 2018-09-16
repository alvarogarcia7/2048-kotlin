package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(moveLeft(together.toMutableList()))
    }

    fun right(): Board {
        return Board(moveLeft(together.asReversed().toMutableList()).asReversed())
    }

    fun up(): Board {
        var result = (together).toMutableList()
        val columns = Math.min(4, result.size)
        val rows = Math.min(4, result.size / 4)
        val single = fun(i: Int, j: Int) = i * columns + j
        for (i in 0..columns) {
            for (j in 0..rows) {
                try {
                    if (result[single(i, j)] == result[single(i - 1, j)] && result[single(i, j)] != Tile(0)) {
                        result[single(i - 1, j)] = result[single(i - 1, j)].next()
                        result.removeAt(single(i, j))
                    }
                } catch (e: IndexOutOfBoundsException) {
                }
            }
        }
        val newBoard = result
        return Board(newBoard)
    }

    private fun moveLeft(tiles: MutableList<Tile>): List<Tile> {
        var result = tiles
        var i = 1
        while (i < result.size) {
            if (result[i] == result[i - 1] && result[i] != Tile(0)) {
                result[i - 1] = result[i - 1].next()
                result.removeAt(i)
            }
            i++
        }
        return result
    }

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
