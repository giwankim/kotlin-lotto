package lotto.ui

import lotto.domain.Lotto
import lotto.domain.LottoLine
import lotto.domain.LottoPayment
import lotto.domain.LottoResult
import lotto.domain.LottoResult2
import lotto.domain.Rank
import lotto.domain.Rank2

object ResultView {
    private const val COMMA_SEPARATOR = ", "
    private val NEWLINE = System.lineSeparator()

    fun printLotto(
        lotto: Lotto,
        numberOfManual: Int,
    ) {
        val output =
            buildString {
                appendLine()
                appendLine("수동으로 ${numberOfManual}장, 자동으로 ${lotto.numberOfLines - numberOfManual}개를 구매했습니다.")
                appendLine(formatLotto(lotto))
            }
        println(output)
    }

    fun printResult(
        result: LottoResult,
        payment: LottoPayment,
    ) {
        val roi = result.returnOnInvestment(payment)
        val output =
            buildString {
                appendLine()
                appendLine("당첨 통계")
                appendLine("---------")

                // 당첨 통계
                appendLine(formatResult(result))

                // 수익률
                append(formatRoi(roi))
            }
        println(output)
    }

    fun printResult2(
        result: LottoResult2,
        payment: LottoPayment,
    ) {
        val roi = result.returnOnInvestment(payment)
        val output =
            buildString {
                appendLine()
                appendLine("당첨 통계")
                appendLine("---------")

                // 당첨 통계
                appendLine(formatResult2(result))

                // 수익률
                append(formatRoi(roi))
            }
        println(output)
    }

    private fun formatLotto(lotto: Lotto): String =
        lotto.lines
            .joinToString(NEWLINE) { formatLine(it) }

    private fun formatLine(
        line: LottoLine,
        prefix: String = "[",
        postfix: String = "]",
    ): String =
        line.numbers
            .joinToString(COMMA_SEPARATOR, prefix, postfix) { it.value.toString() }

    private fun formatResult(result: LottoResult): String =
        Rank.entries
            .sortedBy { it.prize }
            .filter { it != Rank.MISS }
            .joinToString(NEWLINE) { formatResultByRank(it, result.get(it)) }

    private fun formatResult2(result: LottoResult2): String =
        Rank2.entries
            .sortedBy { it.prize }
            .filter { it != Rank2.MISS }
            .joinToString(NEWLINE) { formatResultByRank2(it, result.get(it)) }

    private fun formatResultByRank(
        rank: Rank,
        count: Int,
    ): String =
        when (rank) {
            Rank.MISS -> ""
            Rank.SECOND -> "${rank.count}개 일치, 보너스 볼 일치 (${rank.prize}원)- ${count}개"
            else -> "${rank.count}개 일치 (${rank.prize}원)- ${count}개"
        }

    private fun formatResultByRank2(
        rank: Rank2,
        count: Int,
    ): String =
        when (rank) {
            Rank2.MISS -> ""
            Rank2.SECOND -> "${rank.count}개 일치, 보너스 볼 일치 (${rank.prize}원)- ${count}개"
            else -> "${rank.count}개 일치 (${rank.prize}원)- ${count}개"
        }

    private fun formatRoi(roi: Double): String {
        val formattedRoi = "%.2f".format(roi)
        return "총 수익률은 ${formattedRoi}입니다.(기준이 1이기 때문에 결과적으로 ${if (roi > 1.0) "수익이" else "손해"}라는 의미임)"
    }
}
