package lotto.ui

import lotto.domain.Lotto
import lotto.domain.RANKING
import lotto.domain.WinningResult

class ResultView {
    fun printPurchaseCount(manualCount: Int, autoCount: Int) {
        println("수동으로 ${manualCount}장, 자동으로 ${autoCount}개를 구매했습니다.")
    }

    fun printPurchaseLotteNumbers(lottoList: List<Lotto>) {
        lottoList.forEach { lotto ->
            println("$lotto")
        }
    }

    fun printWinningStatisticsStart() {
        println()
        println("당첨 통계")
        println("---------")
    }

    fun printWinningStatistics(winningResult: WinningResult) {
        for (rank in RANKING.values()) {
            if (rank != RANKING.MISS) {
                printWinningStatistics(rank, winningResult.getWinningResult(rank), rank.bonusMatched)
            }
        }
    }

    private fun printWinningStatistics(rank: RANKING, winningCount: Int, bonusMatched: Boolean) {
        println("${ rank.winningCount }개 일치${if (bonusMatched) " 보너스 볼 일치" else ""} (${ rank.winningPrice }원) - ${ winningCount }개")
    }

    fun printWinningStatisticsRate(rate: Float) {
        println("총 수익률은 ${ rate }입니다.(기준이 1이기 때문에 결과적으로 ${ if (rate < 1) "손해" else "이익" }라는 의미임)")
    }
}