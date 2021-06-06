package com.devcraft.tores.entities

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.devcraft.tores.R

enum class Rank {
    MEMBER, CLIENT, AGENT, MANAGER, VIP_MANAGER, DIRECTOR, DIRECTOR_1_STAR, DIRECTOR_2_STARS,
    DIRECTOR_3_STARS, DIRECTOR_4_STARS, DIRECTOR_5_STARS, DIRECTOR_6_STARS, DIRECTOR_7_STARS,
    DIRECTOR_8_STARS, DIRECTOR_9_STARS, PRESIDENT;


    companion object {
        fun parseRank(rankString: String): Rank {
            return when (rankString) {
                "Участник" -> MEMBER
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
                else -> throw Exception("Error parsing rank status, passed status: $rankString")
            }
        }
    }

    fun getRankText(context: Context): String {
        return when (this) {
            MEMBER -> context.getString(R.string.ranks_member)
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

    fun getRankDrawable(context: Context): Drawable? {
        return when (this) {
            MEMBER -> null
            CLIENT -> ContextCompat.getDrawable(context, R.drawable.ic_rank_client)
            AGENT -> ContextCompat.getDrawable(context, R.drawable.ic_rank_agent)
            MANAGER -> ContextCompat.getDrawable(context, R.drawable.ic_rank_manager)
            VIP_MANAGER -> ContextCompat.getDrawable(context, R.drawable.ic_rank_vip_manager)
            DIRECTOR -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director)
            DIRECTOR_1_STAR -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_1)
            DIRECTOR_2_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_2)
            DIRECTOR_3_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_3)
            DIRECTOR_4_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_4)
            DIRECTOR_5_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_5)
            DIRECTOR_6_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_6)
            DIRECTOR_7_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_7)
            DIRECTOR_8_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_8)
            DIRECTOR_9_STARS -> ContextCompat.getDrawable(context, R.drawable.ic_rank_director_9)
            PRESIDENT -> ContextCompat.getDrawable(context, R.drawable.ic_rank_president)
        }
    }
}
