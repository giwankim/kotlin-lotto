package lottoAuto.domain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LottoNumberTest {
    @Test
    fun `주어진 로또 번호가 1 미만이거나 45를 초과했을 경우 IllegalArgumentException을 발생시킨다`() {
        // given
        val number = 0

        assertThrows<IllegalArgumentException> { // then
            LottoNumber.of(number) // when
        }
    }
}