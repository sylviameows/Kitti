package net.sylviameows.kitti.api.types

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class KittiDuration(time: Long) {
    companion object {
        private val periodRegex = """\d+([wd])(?=\s+|${"$"})""".toRegex(RegexOption.IGNORE_CASE)
        private val timeRegex = """\d+[hms](?=\s+|${"$"})""".toRegex(RegexOption.IGNORE_CASE)

        fun parse(string: String): KittiDuration {
            val period: MutableMap<String, Long> = HashMap()
            val time: MutableMap<String, Long> = HashMap()

            val input = string.lowercase();

            periodRegex.findAll(input).forEach(handler(period))
            timeRegex.findAll(input).forEach(handler(time))

            var iso = "P"
            if (period.containsKey("w")) {
                val weeks = period["w"]!!
                period["d"] = period.getOrDefault("d", 0) + (weeks*7)
            }
            if (period.containsKey("d")) {
                iso += "${period["d"]}d"
            }
            if (time.isNotEmpty()) iso += "T"
            listOf("h","m","s").forEach {designation ->
                if (time.containsKey(designation)) {
                    iso += "${time[designation]}$designation"
                }
            }

            return KittiDuration(Duration.parseIsoString(iso.uppercase()).toLong(DurationUnit.MILLISECONDS))
        }

        private fun handler(map: MutableMap<String, Long>): (result: MatchResult) -> Unit {
            return fun(result: MatchResult) {
                val raw = result.value;
                val value = raw.substring(0, raw.length - 1).toLong();
                val designation = raw.last().toString();

                if (map.containsKey(designation)) {
                    map[designation] = map[designation]!! + value;
                } else {
                    map[designation] = value;
                }
            }
        }
    }

    var time: Long
        private set;

    init {
        this.time = time;
    }

    fun toString(full: Boolean = true): String {
        val duration = time.toDuration(DurationUnit.MILLISECONDS)
        return duration.toComponents { days, hours, minutes, seconds, _ ->
            if (full) return "${days}d ${hours}h ${minutes}m ${seconds}s"
            else {
                var string = "";
                if (days > 0) string += "${days}d"
                if (hours > 0) string += "${hours}h"
                if (minutes > 0) string += "${minutes}m"
                if (seconds > 0) string += "${seconds}s"
                return string;
            }
        }
    }
}