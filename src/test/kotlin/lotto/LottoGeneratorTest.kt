package lotto

import lotto.domain.Lotto
import lotto.utils.LottoGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LottoGeneratorTest {

    private lateinit var lotto: Lotto
    private lateinit var numbers: List<Int>
    private val lottoGenerator = LottoGenerator((1..45).toMutableList())

    @BeforeEach
    fun setup() {
        numbers = lottoGenerator.getLottoNumbers()
        lotto = Lotto(numbers)
    }

    @Test
    fun `1 ~ 45 까지의 로또 번호에서 랜덤한 숫자의 로또를 생성할 수 있다`() {
        val actual = numbers
        assertThat(actual.size).isEqualTo(6)
    }
}