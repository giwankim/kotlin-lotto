package lotto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LottoMachineTest {

    @Test
    fun `1에서 45 숫자를 갖는 로또 객체 생성`() {
        val lottoMaker = object : LottoMaker {
            override fun make(): Lotto {
                val lottoNumbers = setOf(
                    LottoNumber(2),
                    LottoNumber(3),
                    LottoNumber(1),
                    LottoNumber(4),
                    LottoNumber(5),
                    LottoNumber(6)
                )
                return Lotto(lottoNumbers)
            }
        }
        val lottoMachine = LottoMachine(lottoMaker)

        val buyLotto = lottoMachine.buyLotto(1)
        val lotto = buyLotto.lottos[0]

        assertThat(lotto.numbers.size).isEqualTo(6)
    }
}