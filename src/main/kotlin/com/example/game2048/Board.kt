package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(moveLeft(together.toMutableList()))
    }

    fun right(): Board {
        return Board(moveLeft(together.asReversed().toMutableList()).asReversed())
    }

    fun up(): Board {
        return Board(toUp(moveLeft(toLeft(together).toMutableList())))
    }

    private fun toUp(tiles: List<Tile>): List<Tile> {
        return toLeft(tiles)
    }

    private fun toLeft(tiles: List<Tile>): List<Tile> {
        var result = tiles.toMutableList()
        var i = 0
        val columns = Math.min(4, tiles.size)
        val rows = Math.min(4, tiles.size / 4)
        val single = fun(i: Int, j: Int) = i * columns + j
        while (i < columns) {
            var j = 0
            while (j < rows) {
                try {
                    val tmp = tiles[single(i, j)]
                    result[single(i, j)] = tiles[single(j, i)]
                    result[single(j, i)] = tmp
                } catch (e: IndexOutOfBoundsException) {
                }
                j++
            }
            i++
        }
        return result
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
