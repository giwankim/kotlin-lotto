package specific.lotto.domain

data class WinningResult(val ranks: List<Rank>) {

    val aggregatedData: RankCounts = aggregate()

    val totalPrize: Long = calculateTotalPrize()

    private fun aggregate(): RankCounts {
        val aggregatedData = ranks
            .groupBy { it }
            .mapValues { (_, tickets) -> tickets.size }
        return Rank.values()
            .associateWith { aggregatedData[it] ?: 0 }
    }

    private fun calculateTotalPrize() =
        aggregatedData.entries.fold(0L) { sum, (rank, count) -> sum + rank.prize * count }
}

typealias RankCounts = Map<Rank, Int>
