package string.add.calculator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class CalculatorBehaviorSpecTest :
    BehaviorSpec({
        context("문자열의 숫자들을 더한다") {
            val sut = StringAddCalculator

            given("null 입력값을") {
                `when`("계산을 하면") {
                    then("0을 반환한다") {
                        sut.calculate(null) shouldBe 0
                    }
                }
            }

            given("빈 문자열을 입력해서") {
                `when`("계산을 하면") {
                    then("0을 반환한다") {
                        blanks.forAll { sut.calculate(it) shouldBe 0 }
                    }
                }
            }

            given("숫자 하나로 된 문자열을 입력해서") {
                `when`("계산을 하면") {
                    then("해당 숫자를 반환한다") {
                        singleInputs.forAll { (input, expected) ->
                            sut.calculate(input) shouldBe expected
                        }
                    }
                }
            }

            given("숫자 두개를 쉼표(,) 구분자로 읿력하여") {
                `when`("계산을 하면") {
                    then("두 숫자의 합을 반환한다") {
                        sut.calculate("1,2") shouldBe 3
                    }
                }
            }

            given("구분자를 콜론을 사용하여 입력하여") {
                `when`("계산을 하면") {
                    then("세 숫자의 합을 반환한다") {
                        sut.calculate("1,2:3") shouldBe 6
                    }
                }
            }

            given("커스텀 구분자를 지정해서") {
                `when`("계산을 하면") {
                    then("세 숫자의 합을 반환한다") {
                        sut.calculate("//;\n1;2;3") shouldBe 6
                    }
                }
            }

            given("숫자 이외의 값이나 음수를 입력하여") {
                `when`("계산을 하면") {
                    then("예외를 던진다") {
                        invalidInputs.forAll {
                            shouldThrow<RuntimeException> { sut.calculate(it) }
                        }
                    }
                }
            }
        }
    }) {
    companion object {
        private val blanks = listOf("", " ", "  ")
        private val singleInputs = listOf<Pair<String, Int>>("0" to 0, "1" to 1, "42" to 42)
        private val invalidInputs = listOf("-1,2,3", "1,2,가나다")
    }
}
