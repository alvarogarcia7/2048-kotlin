package com.example.game2048

data class Board(val together: List<Tile>) {
    fun left(): Board {
        return Board(moveLeft(together.toMutableList()))
    }

    fun right(): Board {
        return Board(moveLeft(together.asReversed().toMutableList()).asReversed())
    }

    fun up(): Board {
        return Board(together)
    }

    private fun moveLeft(mutableList: MutableList<Tile>): MutableList<Tile> {
        var result = mutableList
        var i = 1
        while (i < result.size) {
            if (result[i] == result[i - 1]) {
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
