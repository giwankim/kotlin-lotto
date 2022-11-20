package calculator

private const val MIN_NUMBER = 0

data class PositiveNumber(
    private val value: Int
) {

    init {
        if (value < MIN_NUMBER) {
            throw RuntimeException("음수는 올 수 없어요.")
        }
    }

    operator fun plus(number: PositiveNumber): PositiveNumber {
        return PositiveNumber(value + number.value)
    }

    fun toInt(): Int {
        return this.value
    }

    companion object {
        fun of(input: String): PositiveNumber {
            return PositiveNumber(input.toInt())
        }
    }

}