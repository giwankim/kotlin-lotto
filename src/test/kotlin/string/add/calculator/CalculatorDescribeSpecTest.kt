package string.add.calculator

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe

class CalculatorDescribeSpecTest :
    DescribeSpec({
        describe("calculate") {
            val sut = StringAddCalculator

            describe("when input is null") {
                it("should return 0") {
                    sut.calculate(null) shouldBe 0
                }
            }

            describe("when input is empty") {
                it("should return 0") {
                    blanks.forAll { sut.calculate(it) shouldBe 0 }
                }
            }

            describe("when input is a single number") {
                it("should return the number") {
                    singleInputs.forAll { (input, expected) ->
                        sut.calculate(input) shouldBe expected
                    }
                }
            }

            describe("when input is two numbers separated by a comma") {
                it("should return the sum of two numbers") {
                    sut.calculate("1,2") shouldBe 3
                }
            }

            describe("when input has a colon as a delimiter") {
                it("should return the sum of numbers separated by a comma or colon") {
                    sut.calculate("1,2:3") shouldBe 6
                }
            }

            context("when input has a custom delimiter") {
                it("should return the sum numbers separated by the custom delimiter") {
                    sut.calculate("//;\n1;2;3") shouldBe 6
                }
            }

            context("when input has a negative number or non-numeric value") {
                it("should throw an exception") {
                    invalidInputs.forAll {
                        shouldThrow<RuntimeException> { sut.calculate(it) }
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
