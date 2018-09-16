package com.example.game2048

data class Board(val together: List<List<Tile>>) {
    fun left(): Board {
        return Board(moveLeft(together).map { result ->
            val x = result.filterNot { it == Tile(0) }.toMutableList()
            for (i in x.size until 4) {
                x.add(Tile(0))
            }
            x
        })
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
        var result: MutableList<MutableList<Tile>> = makeMutable(together)
        for (i in 1 until result.size) {
            for (j in 0 until result[i].size) {
                if (result[i][j] == result[i - 1][j] && result[i][j] != Tile(0)) {
                    result[i - 1][j] = result[i - 1][j].next()
                    for (_i in i + 1 until result.size) {
                        result[_i - 1][j] = result[_i][j]
                    }
                    result[3][j] = Tile(0)
                }
            }
        }
        (0 until 4).map { j ->
            val column =
                    (0 until 4)
                            .map { i ->
                                result[i][j]
                            }
                            .filterNot { it == Tile(0) }
                            .toMutableList()
            for (_i in column.size until 4) {
                column.add(Tile(0))
            }

            (0 until 4).map { ii ->
                result[ii][j] = column[ii]
            }
        }
        return Board(result)
    }

    fun down(): Board {
        var result: MutableList<MutableList<Tile>> = makeMutable(together)
        var i = result.size - 1
        while (i >= 1) {
            for (j in 0 until result[i].size) {
                if (result[i][j] == result[i - 1][j] && result[i][j] != Tile(0)) {
                    result[i][j] = result[i][j].next()
                    (1 until i).reversed().map {
                        if (result[it][j] == Tile(0)) {
                            result[it][j] = result[it - 1][j]
                        }
                    }
                    result[0][j] = Tile(0)
                    (i until 4).map {
                        if (result[it][j] == Tile(0)) {
                            result[it][j] = result[it - 1][j]
                            result[it - 1][j] = Tile(0)
                        }
                    }
                }

            }
            i--
        }
        (0 until 4).map { j ->
            val column =
                    (0 until 4)
                            .map { i ->
                                result[i][j]
                            }
                            .filterNot { it == Tile(0) }
                            .toMutableList()
            for (_i in column.size until 4) {
                column.add(0, Tile(0))
            }

            (0 until 4).map { ii ->
                result[ii][j] = column[ii]
            }
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

    override fun toString(): String {
        return "Board($together)"
    }


}

data class Tile(val value: Int) {
    fun next(): Tile {
        return Tile(value + 1)
    }

    override fun toString(): String {
        return value.toString()
    }


}
