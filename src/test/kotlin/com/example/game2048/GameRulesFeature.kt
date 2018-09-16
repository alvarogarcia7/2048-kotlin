package com.example.game2048

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameRulesFeature {

    @Test
    fun `collapse two pieces that are together, reducing from two tiles to one`() {
        setMove(Board::left) {
            assertThat(Board(singleRow(1, 1)).move(it)).isEqualTo(Board(single(2)))
        }
        setMove(Board::right) {
            assertThat(Board(singleRow(1, 1)).move(it)).isEqualTo(Board(singleRow(0, 0, 0, 2)))
        }
    }

    @Test
    fun `collapse two pieces that are together in the same column, when moving columns, reducing from two tiles to one`() {
        setMove(Board::up) {
            assertBoardAfterMoving(
                    single(
                            row(1, 0, 0, 0),
                            row(1)), it,
                    singleRow(2, 0, 0, 0))
        }
        setMove(Board::down) {
            assertBoardAfterMoving(
                    single(
                            row(1, 0, 0, 0),
                            row(1)),
                    it,
                    listOf(
                            row(0, 0, 0, 0),
                            row(0, 0, 0, 0),
                            row(0, 0, 0, 0),
                            row(2, 0, 0, 0)
                    ))
        }
    }

    @Test
    fun `when moving columns, move even if they don't collapse`() {
        setMove(Board::down) {
            assertBoardAfterMoving(
                    single(
                            row(2, 0, 0, 0),
                            row(1)),
                    it,
                    listOf(
                            row(0, 0, 0, 0),
                            row(0, 0, 0, 0),
                            row(2, 0, 0, 0),
                            row(1, 0, 0, 0)
                    ))

        }
        setMove(Board::up) {
            assertBoardAfterMoving(
                    listOf(
                            row(),
                            row(2),
                            row(1),
                            row()),
                    it,
                    single(
                            row(2),
                            row(1)
                    ))

        }
    }

    private fun assertBoardAfterMoving(initial: List<List<Tile>>, it: (Board) -> Board, final: List<List<Tile>>) {
        assertThat(Board(initial).move(it)).isEqualTo(Board(final))
    }

    @Test
    fun `do not collapse two pieces that are together in the same singleRow when moving in columns`() {
        setMove(Board::up) {
            assertThat(Board(singleRow(1, 1)).move(it)).isEqualTo(Board(singleRow(1, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, increasing the tile value`() {
        setMove(Board::left) {
            assertThat(Board(singleRow(2, 2)).move(it)).isEqualTo(Board(single(3)))
        }
        setMove(Board::right) {
            assertThat(Board(singleRow(2, 2)).move(it)).isEqualTo(Board(singleRow(0, 0, 0, 3)))
        }
    }

    @Test
    fun `collapse two pieces that are together, keeps the rest of the tiles`() {
        setMove(Board::left) {
            assertThat(Board(singleRow(2, 2, 1)).move(it)).isEqualTo(Board(singleRow(3, 1)))
        }
        setMove(Board::right) {
            assertThat(Board(singleRow(2, 2, 1)).move(it)).isEqualTo(Board(singleRow(0, 0, 3, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses only in pairs`() {
        setMove(Board::left) {
            assertThat(Board(singleRow(2, 2, 2, 1)).move(it)).isEqualTo(Board(singleRow(3, 2, 1)))
        }
        setMove(Board::right) {
            assertThat(Board(singleRow(2, 2, 2, 1)).move(it)).isEqualTo(Board(singleRow(0, 2, 3, 1)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses multiple pairs at once`() {
//        setMove(Board::left) {
//            assertThat(Board(singleRow(2, 2, 2, 2)).move(it)).isEqualTo(Board(singleRow(3, 3)))
//            assertThat(Board(singleRow(2, 2, 3, 3)).move(it)).isEqualTo(Board(singleRow(3, 4)))
//        }
        setMove(Board::right) {
            assertThat(Board(singleRow(2, 2, 2, 2)).move(it)).isEqualTo(Board(singleRow(0, 0, 3, 3)))
            assertThat(Board(singleRow(2, 2, 3, 3)).move(it)).isEqualTo(Board(singleRow(0, 0, 3, 4)))
        }
    }

    @Test
    fun `collapse two pieces that are together, collapses only in pairs, the first pieces to collapse are the ones on the direction of the move`() {
        setMove(Board::left) {
            assertThat(Board(single(row(2, 2, 2, 2), row(1))).move(it)).isEqualTo(Board(single(row(3, 3, 0, 0), row(1))))
            assertThat(Board(single(row(2, 2, 2, 2), row(1, 3, 3))).move(it)).isEqualTo(Board(single(row(3, 3, 0, 0), row(1, 4))))
        }
        setMove(Board::right) {
            assertThat(Board(single(row(2, 2, 2, 2), row(1))).move(it)).isEqualTo(Board(single(row(0, 0, 3, 3), row(0, 0, 0, 1))))
            assertThat(Board(single(row(2, 2, 2, 2), row(1, 3, 3))).move(it)).isEqualTo(Board(single(row(0, 0, 3, 3), row(0, 0, 1, 4))))
        }
    }

    private fun row(vararg values: Int): List<Tile> {
        return padWithEmptyTiles(values.map { Tile(it) }, 4)
    }

    private fun single(elements: List<Tile>): List<List<Tile>> {
        return listOf(
                elements,
                emptyRow(),
                emptyRow(),
                emptyRow()
        )
    }

    private fun single(elements: List<Tile>, elements2: List<Tile>): List<List<Tile>> {
        return listOf(
                elements,
                elements2,
                emptyRow(),
                emptyRow()
        )
    }

    private fun single(value: Int): List<List<Tile>> {
        val elements = padWithEmptyTiles(arrayListOf(Tile(value)), 4)
        return single(elements)
    }

    private fun emptyRow(): List<Tile> {
        return padWithEmptyTiles(emptyList(), 4)
    }

    private fun singleRow(vararg values: Int): List<List<Tile>> {
        assertThat(values.size).isLessThanOrEqualTo(4)
        val interestingPart = values.map { Tile(it) }
        return single(padWithEmptyTiles(interestingPart, 4))
    }

    private fun padWithEmptyTiles(interestingPart: List<Tile>, desiredSize: Int): List<Tile> {
        val result = interestingPart.toMutableList()
        for (i in interestingPart.size until desiredSize) {
            result.add(Tile(0))
        }
        assertThat(result).hasSize(desiredSize)
        return result
    }

    private fun setMove(move: (Board) -> Board, function: ((Board) -> Board) -> Unit) {
        function.invoke(move)
    }
}

private fun Board.move(f: (Board) -> Board): Board {
    return f.invoke(this)
}
