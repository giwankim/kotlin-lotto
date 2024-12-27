package string.add.calculator

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

@Suppress("NonAsciiCharacters")
class CalculatorAnnotationSpecTest : AnnotationSpec() {
    private val sut = StringAddCalculator

    @Test
    fun `빈 문자열 또는 null 을 입력할 경우 0을 반환해야 한다`() {
        assertSoftly {
            sut.calculate(null) shouldBe 0

            blanks.forAll {
                sut.calculate("") shouldBe 0
            }
        }
    }

    @Test
    fun `숫자 하나를 입력한 경우 해당 숫자를 반환한다`() {
        singleInputs.forAll { (input, expected) ->
            sut.calculate(input) shouldBe expected
        }
    }

    @Test
    fun `숫자 두개를 쉼표(,) 구분자로 입력할 경우 두 숫자의 합을 반환한다`() {
        sut.calculate("1,2") shouldBe 3
    }

    @Test
    fun `구분자를 쉼표 이외에 콜론을 사용할 수 있다`() {
        sut.calculate("1,2:3") shouldBe 6
    }

    @Test
    fun `커스텀 구분자를 지정할 수 있다`() {
        sut.calculate("//;\n1;2;3") shouldBe 6
    }

    @Test
    fun `숫자 이외의 값 또는 음수를 전달하는 경우 예외를 던진다`() {
        invalidInputs.forAll {
            shouldThrow<RuntimeException> { sut.calculate(it) }
        }
    }

    private val blanks = listOf("", " ", "  ")
    private val singleInputs = listOf<Pair<String, Int>>("0" to 0, "1" to 1, "42" to 42)
    private val invalidInputs = listOf("-1,2,3", "1,2,가나다")
}
