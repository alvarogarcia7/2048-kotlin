package com.example.game2048

data class Board(val together: List<List<Tile>>) {
    fun left(): Board {
        return Board(moveLeft(together))
    }

    private fun padWithEmptyTiles(values: List<Tile>, desiredSize: Int): MutableList<Tile> {
        val result = values.toMutableList()
        for (i in values.size until desiredSize) {
            result.add(Tile(0))
        }
        return result
    }

    fun right(): Board {
        var total: MutableList<MutableList<Tile>> = makeMutable(together)
        for (i in 0 until 4) {
            var result: MutableList<Tile> = total[i]
            run {
                var j = result.size - 1
                while (j >= 1) {
                    if (result[j] == result[j - 1] && result[j] != Tile(0)) {
                        result[j] = result[j].next()
                        result.removeAt(j - 1)
                        result.add(0, Tile(0)) // need to keep the index
                    }
                    j--
                }
            }
            result = result.filter { it != Tile(0) }.toMutableList()
            for (_i in result.size until 4) {
                result.add(0, Tile(0))
            }
            total[i] = result
        }
        return Board(total)
    }

    fun up(): Board {
        var result = makeMutable(together)
        val columns = Math.min(4, result.size)
        val rows = Math.min(4, result.size / 4)
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                try {
                    if (result[i][j] == result[i - 1][j] && result[i][j] != Tile(0)) {
                        result[i - 1][j] = result[i - 1][j].next()
                    }
                } catch (e: IndexOutOfBoundsException) {
                }
            }
            result[i] = padWithEmptyTiles(result[i], together[i].size)
        }
        return Board(result)
    }

    private fun moveLeft(tiles: List<List<Tile>>): List<List<Tile>> {
        var total: MutableList<MutableList<Tile>> = makeMutable(tiles)
        for (i in 0 until total.size) {
            var result: MutableList<Tile> = total[i]
            var j = 1
            while (j < result.size) {
                if (result[j] == result[j - 1] && result[j] != Tile(0)) {
                    result[j - 1] = result[j - 1].next()
                    result.removeAt(j)
                }
                j++
            }
            total[i] = padWithEmptyTiles(result, 4)
        }
        return total
    }

    private fun makeMutable(tiles: List<List<Tile>>) =
            tiles.map { it.toMutableList() }.toMutableList()

}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }
}
