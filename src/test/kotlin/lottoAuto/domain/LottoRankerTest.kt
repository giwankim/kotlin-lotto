package lottoAuto.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LottoRankerTest {
    @Test
    fun `주어진 로또 리스트와 당첨 로또를 비교하여 당첨 등수를 반환한다`() {
        // given
        val lottoNumbers1 = LottoNumbers((1..6).map { LottoNumber.of(it) })
        val lottoNumbers2 = LottoNumbers((4..9).map { LottoNumber.of(it) })
        val lottoNumbers3 = LottoNumbers((10..15).map { LottoNumber.of(it) })
        val winningLottoNumbers = LottoNumbers((4..9).map { LottoNumber.of(it) })

        // when
        val ranks = LottoRanker.rank(
            listOf(
                Lotto(lottoNumbers1),
                Lotto(lottoNumbers2),
                Lotto(lottoNumbers3)
            ),
            Lotto(winningLottoNumbers)
        )

        // then
        assertEquals(LottoRank.FOURTH, ranks[0])
        assertEquals(LottoRank.FIRST, ranks[1])
        assertEquals(LottoRank.MISS, ranks[2])
    }
}