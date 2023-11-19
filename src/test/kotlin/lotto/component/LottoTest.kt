package lotto.component

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.doubles.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import lotto.model.LottoNumbers
import lotto.model.LottoResult
import lotto.model.WinningNumbers

class LottoTest : FunSpec({
    val analyzer = LottoResultAnalyzer()
    val lotto = Lotto(analyzer)

    test("1장의 로또 입력 시 1개의 당첨 결과 성공 테스트") {
        val numbers = listOf(1, 2, 3, 4, 5, 6)
        val lottoNumbers = listOf(LottoNumbers.create(numbers))
        val winningNumbers = WinningNumbers.create(numbers)

        val result: LottoResult = lotto.draw(lottoNumbers, winningNumbers)

        result.lottoPrizes.size shouldBe 1
        result.revenueRate shouldBeGreaterThan -0.0
    }
})
