package lotto.domain

import lotto.domain.model.LottoFactory
import lotto.domain.model.Lottos

object LottoBendingMachine {
    private const val LOTTO_PRICE = 1000

    fun purchase(purchaseAmount: Int, lottoFactory: LottoFactory): Lottos {
        val purchaseCount = getPurchaseCount(purchaseAmount)

        return Lottos.of(purchaseCount, lottoFactory)
    }

    private fun getPurchaseCount(purchaseAmount: Int): Int = purchaseAmount / LOTTO_PRICE
}