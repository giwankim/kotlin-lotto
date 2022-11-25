package calculator

enum class MessageCode(val message: String) {
    NegativeException("음수는 계산할 수 없습니다."),
    NotNumber("숫자가 아닙니다."),
    InputFormatException("입력형식이 맞지 않습니다.")
}