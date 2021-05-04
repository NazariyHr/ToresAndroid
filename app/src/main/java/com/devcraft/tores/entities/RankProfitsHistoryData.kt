package com.devcraft.tores.entities

import android.content.Context
import com.devcraft.tores.R

class RankProfitsHistoryData(
    val rankProfits: List<RankProfit>
) {
    class RankProfit(
        val id: Long,
        val createdAt: String,
        val rank: Rank,
        val amount: Double
    ) {
        enum class Rank {
            CLIENT, AGENT, MANAGER, VIP_MANAGER, DIRECTOR, DIRECTOR_1_STAR, DIRECTOR_2_STARS,
            DIRECTOR_3_STARS, DIRECTOR_4_STARS, DIRECTOR_5_STARS, DIRECTOR_6_STARS, DIRECTOR_7_STARS,
            DIRECTOR_8_STARS, DIRECTOR_9_STARS, PRESIDENT;


            companion object {
                fun parseStatus(statusString: String): Rank {
                    return when (statusString) {
                        "Клиент" -> CLIENT
                        "Агент" -> AGENT
                        "Менеджер" -> MANAGER
                        "VIP-Менджер" -> VIP_MANAGER
                        "Директор" -> DIRECTOR
                        "Директор 1 звезда" -> DIRECTOR_1_STAR
                        "Директор 2 звезды" -> DIRECTOR_2_STARS
                        "Директор 3 звезды" -> DIRECTOR_3_STARS
                        "Директор 4 звезды" -> DIRECTOR_4_STARS
                        "Директор 5 звезд" -> DIRECTOR_5_STARS
                        "Директор 6 звезд" -> DIRECTOR_6_STARS
                        "Директор 7 звезд" -> DIRECTOR_7_STARS
                        "Директор 8 звезд" -> DIRECTOR_8_STARS
                        "Директор 9 звезд" -> DIRECTOR_9_STARS
                        "Президент" -> PRESIDENT
                        else -> throw Exception("Error parsing rank status, passed status: $statusString")
                    }
                }
            }

            fun getStatusText(context: Context): String {
                return when (this) {
                    CLIENT -> context.getString(R.string.ranks_client)
                    AGENT -> context.getString(R.string.ranks_agent)
                    MANAGER -> context.getString(R.string.ranks_manager)
                    VIP_MANAGER -> context.getString(R.string.ranks_vip_manager)
                    DIRECTOR -> context.getString(R.string.ranks_director)
                    DIRECTOR_1_STAR -> context.getString(R.string.ranks_director_1_star)
                    DIRECTOR_2_STARS -> context.getString(R.string.ranks_director_2_stars)
                    DIRECTOR_3_STARS -> context.getString(R.string.ranks_director_3_stars)
                    DIRECTOR_4_STARS -> context.getString(R.string.ranks_director_4_stars)
                    DIRECTOR_5_STARS -> context.getString(R.string.ranks_director_5_stars)
                    DIRECTOR_6_STARS -> context.getString(R.string.ranks_director_6_stars)
                    DIRECTOR_7_STARS -> context.getString(R.string.ranks_director_7_stars)
                    DIRECTOR_8_STARS -> context.getString(R.string.ranks_director_8_stars)
                    DIRECTOR_9_STARS -> context.getString(R.string.ranks_director_9_stars)
                    PRESIDENT -> context.getString(R.string.ranks_president)
                }
            }
        }
    }
}
