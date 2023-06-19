package lottery.domain.lottery

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import lottery.domain.Money
import lottery.domain.lottery.Lottery.Companion.isDivisibleLotteryCost
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_1
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_10
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_2
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_3
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_4
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_5
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_6
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_7
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_8
import lottery.domain.lottery.LotteryNumberTest.Companion.LOTTERY_NUMBER_9
import java.math.BigDecimal

class LotteryTest : FunSpec({

    context("init") {
        test("로또 번호가 6개 입력되지 않는 경우 예외가 발생한다.") {
            forAll(
                row(
                    listOf(
                        LOTTERY_NUMBER_1,
                        LOTTERY_NUMBER_2,
                        LOTTERY_NUMBER_3,
                        LOTTERY_NUMBER_4,
                        LOTTERY_NUMBER_5,
                    ),
                ),
                row(
                    listOf(
                        LOTTERY_NUMBER_1,
                        LOTTERY_NUMBER_2,
                        LOTTERY_NUMBER_3,
                        LOTTERY_NUMBER_4,
                        LOTTERY_NUMBER_5,
                        LOTTERY_NUMBER_6,
                        LOTTERY_NUMBER_7,
                    ),
                ),
            ) { input ->
                val exception = shouldThrowExactly<IllegalArgumentException> { Lottery(values = input) }
                exception.message shouldBe "로또 번호는 6개만 입력하여야한다"
            }
        }

        test("로또 번호가 중복되어 입력되는 경우 예외가 발생한다.") {
            val input = listOf(
                LOTTERY_NUMBER_1,
                LOTTERY_NUMBER_2,
                LOTTERY_NUMBER_3,
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_5,
            )

            val exception = shouldThrowExactly<IllegalArgumentException> { Lottery(values = input) }
            exception.message shouldBe "로또 번호는 중복되어 입력될 수 없다"
        }

        test("로또 번호를 6개 가지고 있다") {
            val input = listOf(
                LOTTERY_NUMBER_1,
                LOTTERY_NUMBER_2,
                LOTTERY_NUMBER_3,
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
            )
            val actual = Lottery(values = input)
            actual.values shouldHaveSize 6
        }
    }

    context("matchCount") {
        forAll(
            row(LOTTERY_1_6, 6),
            row(LOTTERY_2_7, 5),
            row(LOTTERY_3_8, 4),
            row(LOTTERY_4_9, 3),
            row(LOTTERY_5_10, 2),
        ) { input, expected ->
            test("번호 일치한 갯수 $expected 를 계산한다") {
                val lottery = LOTTERY_1_6
                val actual = lottery.matchCount(input)
                actual shouldBe expected
            }
        }
    }

    context("containsLotteryNumber") {
        test("포함되어있다면 true를 반환한다") {
            val lottery = LOTTERY_1_6
            val givenLotteryNumber = LOTTERY_NUMBER_1

            val actual = lottery.containsLotteryNumber(givenLotteryNumber)
            actual shouldBe true
        }

        test("포함되어있지 않다면 false를 반환한다") {
            val lottery = LOTTERY_1_6
            val givenLotteryNumber = LOTTERY_NUMBER_7

            val actual = lottery.containsLotteryNumber(givenLotteryNumber)
            actual shouldBe false
        }
    }

    context("from") {
        test("숫자 문자 list를 입력받아 Lottery를 생성한다") {
            val actual = Lottery.from(values = listOf("1", "2", "3", "4", "5", "6"))
            actual.values shouldHaveSize 6
        }
    }

    context("isDivisibleLotteryCost") {
        test("로또금액으로 나누어떨어지는 돈인지 확인한다") {
            forAll(
                row(Money(value = BigDecimal(999)), false),
                row(Money(value = BigDecimal(1_000)), true),
            ) { input, expected ->
                val actual = input.isDivisibleLotteryCost()
                actual shouldBe expected
            }
        }
    }

    context("purchaseLottery") {
        test("로또 구매 결과를 반환한다") {
            forAll(
                row(Money(value = BigDecimal(999)), 0),
                row(Money(value = BigDecimal(1_000)), 1),
            ) { input, expected ->
                val actual = Lottery.purchaseLottery(input)
                actual.purchaseCount shouldBe expected
            }
        }
    }

    context("canPurchaseLottery") {
        test("로또를 구매할 수 있다면 true를 반환한다") {
            val actual = Lottery.canPurchaseLottery(Money(value = BigDecimal(1_000)))
            actual shouldBe true
        }

        test("로또를 구매할 수 없다면 false를 반환한다") {
            val actual = Lottery.canPurchaseLottery(Money(value = BigDecimal(999)))
            actual shouldBe false
        }

        test("구입 갯수를 받았을 때 로또를 구매할 수 있다면 true를 반환한다") {
            val actual = Lottery.canPurchaseLottery(purchaseCount = 2, money = Money(value = BigDecimal(2_999)))
            actual shouldBe true
        }

        test("구입 갯수를 받았을 때 로또를 구매할 수 없다면 false를 반환한다") {
            val actual = Lottery.canPurchaseLottery(purchaseCount = 3, money = Money(value = BigDecimal(2_999)))
            actual shouldBe false
        }
    }
}) {
    companion object {
        val LOTTERY_1_6 = Lottery(
            values = listOf(
                LOTTERY_NUMBER_1,
                LOTTERY_NUMBER_2,
                LOTTERY_NUMBER_3,
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
            ),
        )
        val LOTTERY_2_7 = Lottery(
            values = listOf(
                LOTTERY_NUMBER_2,
                LOTTERY_NUMBER_3,
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
                LOTTERY_NUMBER_7,
            ),
        )
        val LOTTERY_3_8 = Lottery(
            values = listOf(
                LOTTERY_NUMBER_3,
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
                LOTTERY_NUMBER_7,
                LOTTERY_NUMBER_8,
            ),
        )
        val LOTTERY_4_9 = Lottery(
            values = listOf(
                LOTTERY_NUMBER_4,
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
                LOTTERY_NUMBER_7,
                LOTTERY_NUMBER_8,
                LOTTERY_NUMBER_9,
            ),
        )
        val LOTTERY_5_10 = Lottery(
            values = listOf(
                LOTTERY_NUMBER_5,
                LOTTERY_NUMBER_6,
                LOTTERY_NUMBER_7,
                LOTTERY_NUMBER_8,
                LOTTERY_NUMBER_9,
                LOTTERY_NUMBER_10,
            ),
        )
    }
}