package com.example.game2048

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameRulesFeature {

    @Test
    fun `collapse two pieces that are together, reducing from two tiles to one`() {
        setMove(Board::left, Board::right) {
            assertThat(Board(together(1, 1)).move(it)).isEqualTo(Board(single(2)))
        }
    }

    @Test
    fun `collapse two pieces that are together in the same column, when moving columns, reducing from two tiles to one`() {
        setMove(Board::up) {
            assertThat(Board(together(
                    1, 0, 0, 0,
                    1)).move(it)).isEqualTo(Board(together(2, 0, 0, 0)))
        }
    }

    @Test
    fun `do not collapse two pieces that are together in the same row when moving in columns`() {
        setMove(Board::up) {
            assertThat(Board(together(1, 1)).move(it)).isEqualTo(Board(together(1, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, increasing the tile value`() {
        setMove(Board::left, Board::right) {
            assertThat(Board(together(2, 2)).move(it)).isEqualTo(Board(single(3)))
        }
    }

    @Test
    fun `collapse two pieces that are together, keeps the rest of the tiles`() {
        setMove(Board::left, Board::right) {
            assertThat(Board(together(2, 2, 1)).move(it)).isEqualTo(Board(together(3, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses only in pairs`() {
        setMove(Board::left) {
            assertThat(Board(together(2, 2, 2, 1)).move(it)).isEqualTo(Board(together(3, 2, 1)))
        }
        setMove(Board::right) {
            assertThat(Board(together(2, 2, 2, 1)).move(it)).isEqualTo(Board(together(2, 3, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses multiple pairs at once`() {
        setMove(Board::left, Board::right) {
            assertThat(Board(together(2, 2, 2, 2)).move(it)).isEqualTo(Board(together(3, 3)))
            assertThat(Board(together(2, 2, 3, 3)).move(it)).isEqualTo(Board(together(3, 4)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses only in pairs, the first pieces to collapse are the ones on the direction of the move`() {
        setMove(Board::left, Board::right) {
            assertThat(Board(together(2, 2, 2, 2, 1)).move(it)).isEqualTo(Board(together(3, 3, 1)))
            assertThat(Board(together(2, 2, 2, 2, 1, 3, 3)).move(it)).isEqualTo(Board(together(3, 3, 1, 4)))
        }
    }

    private fun single(value: Int): List<Tile> {
        return arrayListOf(Tile(value))
    }

    private fun together(vararg values: Int): List<Tile> {
        return values.map { Tile(it) }
    }

    private fun setMove(vararg moves: (Board) -> Board, function: ((Board) -> Board) -> Unit) {
        moves.map {
            function.invoke(it)
        }
    }
}

private fun Board.move(f: (Board) -> Board): Board {
    return f.invoke(this)
}
