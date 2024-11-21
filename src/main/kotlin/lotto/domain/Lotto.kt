package lotto.domain

class Lotto(
    val lines: List<LottoLine>,
) {
    val numberOfLines: Int
        get() = lines.size

    fun match(winner: LottoLine): LottoResult {
        val rankToCount =
            lines
                .groupBy { it.match(winner) }
                .mapValues { (_, value) -> value.size }
        return LottoResult(rankToCount)
    }

    companion object {
        fun from(lines: List<LottoLine>): Lotto = Lotto(lines)

        fun from(vararg lines: LottoLine): Lotto = Lotto(lines.toList())
    }
}
