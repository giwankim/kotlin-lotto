package calculator

import calculator.StringAddCalculator.add
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

class StringAddCalculatorTest {

    @DisplayName("입력값이 비어 있을 때(공백 or Null), 0을 반환한다")
    @ParameterizedTest
    @NullAndEmptySource
    fun `empty or null`(text: String?) {
        assertThat(add(text)).isEqualTo(0)
    }

    @DisplayName("입력값 중 음수가 있을 경우 RuntimeException을 발생시킨다.")
    @Test
    fun negative() {
        assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy { add("-1") }.withMessage("숫자 이외의 값 또는 음수 입력 불가")
    }

    @DisplayName("입력값이 숫자 하나일 때 숫자 그대로 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = ["7"])
    fun `just one number`(text: String) {
        assertThat(add(text)).isSameAs(text.toInt())
    }

    @DisplayName("쉼표(,) 또는 콜론(:)을 구분자로 사용해 모든 숫자의 합을 반환한다")
    @ParameterizedTest
    @ValueSource(strings = ["3,7:10"])
    fun `default delimiter`(text: String) {
        assertThat(add(text)).isSameAs(20)
    }

    @DisplayName("//와 \\n 문자 사이에 커스텀 구분자를 지정할 수도 있다.")
    @ParameterizedTest
    @ValueSource(strings = ["//?\\n1?2:3"])
    fun `custom delimiter`(text: String) {
        assertThat(add(text)).isSameAs(6)
    }

    @DisplayName("구분자가 아닌 문자가 사용되었을 때 RuntimeException을 발생시킨다.")
    @Test
    fun `illegal letter`() {
        assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy { add("aa,1,2") }.withMessage("숫자 이외의 값 또는 음수 입력 불가")
    }
}
