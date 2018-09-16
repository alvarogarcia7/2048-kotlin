package com.example.game2048

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameRulesFeature {

    @Test
    fun `collapse two pieces that are together, reducing from two tiles to one`() {
        setMove(Board::left)
        assertThat(Board(together(1, 1)).move()).isEqualTo(Board(single(2)))
    }


    @Test
    fun `collapse two pieces that are together, increasing the tile value`() {
        setMove(Board::left)
        assertThat(Board(together(2, 2)).move()).isEqualTo(Board(single(3)))
    }


    @Test
    fun `collapse two pieces that are together, keeps the rest of the tiles`() {
        setMove(Board::left)
        assertThat(Board(together(2, 2, 1)).move()).isEqualTo(Board(together(3, 1)))
    }

    @Test
    fun `collapse two pieces that are together, collapses only in pairs`() {
        setMove(Board::left)
        assertThat(Board(together(2, 2, 2, 1)).move()).isEqualTo(Board(together(3, 2, 1)))
    }

    @Test
    fun `collapse two pieces that are together, collapses multiple pairs at once`() {
        setMove(Board::left)
        assertThat(Board(together(2, 2, 2, 2)).move()).isEqualTo(Board(together(3, 3)))
        assertThat(Board(together(2, 2, 3, 3)).move()).isEqualTo(Board(together(3, 4)))
    }

    @Test
    fun `collapse two pieces that are together to the right, collapses multiple pairs at once`() {
        setMove(Board::right)
        assertThat(Board(together(2, 2, 2, 2)).move()).isEqualTo(Board(together(3, 3)))
        assertThat(Board(together(2, 2, 3, 3)).move()).isEqualTo(Board(together(3, 4)))
    }

    @Test
    fun `collapse two pieces that are together to the right, collapses only in pairs, the first pieces to collapse are the ones on the right`() {
        setMove(Board::right)
        assertThat(Board(together(2, 2, 2, 1)).move()).isEqualTo(Board(together(2, 3, 1)))
        assertThat(Board(together(2, 2, 2, 2, 1)).move()).isEqualTo(Board(together(3, 3, 1)))
        assertThat(Board(together(2, 2, 2, 2, 1, 3, 3)).move()).isEqualTo(Board(together(3, 3, 1, 4)))
    }

    private fun single(value: Int): List<Tile> {
        return arrayListOf(Tile(value))
    }

    private fun together(vararg values: Int): List<Tile> {
        return values.map { Tile(it) }
    }

    companion object Config {
        lateinit var _move: (Board) -> Board

        private fun setMove(move: (Board) -> Board) {
            Config._move = move
        }
    }
}

private fun Board.move(): Board {
    return GameRulesFeature.Config._move.invoke(this)
}
