package lotto.view

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import lotto.domain.Lotto

internal class LottoViewTest : StringSpec({

    "구매한 로또 개수를 출력한다" {
        val lottos = List(5) { Lotto.of(listOf(1, 2, 3, 4, 5, 6)) }
        val output = StubOutput()
        val view = LottoView(output, lottos)

        view.print()

        output.printed.first() shouldBe "5개를 구매했습니다."
    }

    "구매한 로또의 숫자리스트를 출력한다" {
        val lottos = listOf(
            Lotto.of(listOf(1, 2, 3, 4, 5, 6)),
            Lotto.of(listOf(7, 8, 9, 10, 11, 12)),
        )
        val output = StubOutput()
        val view = LottoView(output, lottos)

        view.print()

        output.printed.drop(1) shouldBe listOf(
            "[1, 2, 3, 4, 5, 6]",
            "[7, 8, 9, 10, 11, 12]",
        )
    }
})