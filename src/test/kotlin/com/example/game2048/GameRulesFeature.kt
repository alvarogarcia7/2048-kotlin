package com.example.game2048

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GameRulesFeature {
    @Test
    fun `collapse two pieces that are together`() {
        assertThat(Board(together(1, 1)).left()).isEqualTo(Board(single(2)))
    }

    private fun single(value: Int): List<Tile> {
        return arrayListOf(Tile(value))
    }

    private fun together(value1: Int, value2: Int): List<Tile> {
        return arrayListOf(Tile(value1), Tile(value2))
    }
}