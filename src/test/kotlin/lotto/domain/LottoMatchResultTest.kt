package lotto.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LottoMatchResultTest {

    @Test
    fun getMatchResult() {
        val lottoMatchMap = mutableMapOf<Int, LottoMatch>()
        val matchNumber = 3
        val matchCount: Long = 2
        val reward: Long = 100
        lottoMatchMap[matchNumber] = LottoMatch(matchNumber, reward, matchCount)

        val lottoMatchList =
            LottoMatchResult(lottoMatchMap).getMatchResult()

        lottoMatchList.forEach { lottoMatch ->
            assertThat(lottoMatch.matchNumber).isEqualTo(matchNumber)
            assertThat(lottoMatch.matchCount).isEqualTo(matchCount)
            assertThat(lottoMatch.reward).isEqualTo(reward)
        }
    }

    @Test
    fun `setMatchResult should matchCount + 1`() {
        val lottoMatchMap = mutableMapOf<Int, LottoMatch>()
        val matchNumber = 3
        lottoMatchMap[matchNumber] = LottoMatch(matchNumber, 2, 0)

        val lottoMatchResult = LottoMatchResult(lottoMatchMap)
        lottoMatchResult.setMatchResult(matchNumber)

        lottoMatchResult.getMatchResult().forEach { lottoMatch ->
            assertThat(lottoMatch.matchNumber).isEqualTo(matchNumber)
            assertThat(lottoMatch.matchCount).isEqualTo(1)
        }
    }
}