package com.devcraft.tores.entities

import com.devcraft.tores.R
import com.devcraft.tores.app.App

data class RankSystemLevel(
    val rank: Rank,
    val deposit: String?,
    val referralLevel: String?,
    val marketingBase: String?,
    val payments: String?,
    val conditions: String?
) {
    companion object {
        fun getAllRankSystemLevels(): MutableList<RankSystemLevel> = mutableListOf(
            RankSystemLevel(
                Rank.MEMBER,
                null,
                null,
                null,
                null,
                App.instance.getString(R.string.registration)
            ),
            RankSystemLevel(
                Rank.CLIENT,
                App.instance.getString(R.string.tores_amount, 100.0),
                "1",
                App.instance.getString(R.string.marketing_base_tores, "5", "250"),
                null,
                null
            ),
            RankSystemLevel(
                Rank.AGENT,
                App.instance.getString(R.string.tores_amount, 250.0),
                "2",
                App.instance.getString(R.string.marketing_base_tores, "20", "250"),
                null,
                null
            ),
            RankSystemLevel(
                Rank.MANAGER,
                App.instance.getString(R.string.tores_amount, 250.0),
                "3",
                App.instance.getString(R.string.tores_volume, 5_000),
                App.instance.getString(R.string.rank_profit_tores, 100),
                App.instance.getString(R.string.rank_condition_or_more_branch, 2)
            ),
            RankSystemLevel(
                Rank.VIP_MANAGER,
                App.instance.getString(R.string.tores_amount, 250.0),
                null,
                App.instance.getString(R.string.tores_volume, 15_000),
                App.instance.getString(R.string.rank_profit_tores, 500),
                App.instance.getString(R.string.rank_condition_or_more_branch, 2)
            ),
            RankSystemLevel(
                Rank.DIRECTOR,
                App.instance.getString(R.string.tores_amount, 500.0),
                null,
                App.instance.getString(R.string.tores_volume, 40_000),
                App.instance.getString(R.string.rank_profit_tores, 1_000),
                App.instance.getString(R.string.rank_condition_or_more_branch, 3)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_1_STAR,
                App.instance.getString(R.string.tores_amount, 750.0),
                null,
                App.instance.getString(R.string.tores_volume, 90_000),
                App.instance.getString(R.string.rank_profit_tores, 1_500),
                null
            ),
            RankSystemLevel(
                Rank.DIRECTOR_2_STARS,
                App.instance.getString(R.string.tores_amount, 1_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 180_000),
                App.instance.getString(R.string.rank_profit_tores, 3_000),
                null
            ),
            RankSystemLevel(
                Rank.DIRECTOR_3_STARS,
                App.instance.getString(R.string.tores_amount, 2_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 360_000),
                App.instance.getString(R.string.rank_profit_tores, 5_000),
                App.instance.getString(R.string.rank_condition_or_more_branch, 4)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_4_STARS,
                App.instance.getString(R.string.tores_amount, 5_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 720_000),
                App.instance.getString(R.string.rank_profit_tores, 10_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 3, 3)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_5_STARS,
                App.instance.getString(R.string.tores_amount, 10_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 1_440_000),
                App.instance.getString(R.string.rank_profit_tores, 20_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 4, 3)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_6_STARS,
                App.instance.getString(R.string.tores_amount, 20_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 2_880_000),
                App.instance.getString(R.string.rank_profit_tores, 50_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 3, 4)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_7_STARS,
                App.instance.getString(R.string.tores_amount, 30_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 6_720_000),
                App.instance.getString(R.string.rank_profit_tores, 100_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 4, 4)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_8_STARS,
                App.instance.getString(R.string.tores_amount, 40_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 13_440_000),
                App.instance.getString(R.string.rank_profit_tores, 200_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 4, 6)
            ),
            RankSystemLevel(
                Rank.DIRECTOR_9_STARS,
                App.instance.getString(R.string.tores_amount, 50_000.0),
                null,
                App.instance.getString(R.string.tores_volume, 26_880_000),
                App.instance.getString(R.string.rank_profit_tores, 400_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 5, 7)
            ),
            RankSystemLevel(
                Rank.PRESIDENT,
                App.instance.getString(R.string.tores_amount, 100_000.0),
                App.instance.getString(R.string.rank_level, 3),
                App.instance.getString(R.string.tores_volume, 60_000_000),
                App.instance.getString(R.string.rank_profit_tores, 800_000),
                App.instance.getString(R.string.rank_condition_in_branches_level_director, 5, 9)
            )
        )
    }
}