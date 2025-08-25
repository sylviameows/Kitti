package net.sylviameows.kitti.api.commands.arguments

import com.mojang.brigadier.Message
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import io.papermc.paper.command.brigadier.argument.CustomArgumentType
import net.kyori.adventure.text.Component
import net.sylviameows.kitti.api.types.KittiDuration
import java.util.concurrent.CompletableFuture

class DurationArgumentType(private val minimum: KittiDuration = KittiDuration(0), private val greedy: Boolean = true) : CustomArgumentType.Converted<KittiDuration, String> {
    companion object {
        fun extended(): DurationArgumentType {
            return DurationArgumentType()
        }
        fun extended(minimum: KittiDuration): DurationArgumentType {
            return DurationArgumentType(minimum)
        }

        fun unit(): DurationArgumentType {
            return DurationArgumentType(greedy = false)
        }
        fun unit(minimum: KittiDuration): DurationArgumentType {
            return DurationArgumentType(minimum, false)
        }
    }

    override fun getNativeType(): ArgumentType<String> {
        if (greedy) return StringArgumentType.greedyString()
        return StringArgumentType.word();
    }

    override fun convert(native: String): KittiDuration {
        val duration = KittiDuration.parse(native);
        if (duration.time < minimum.time) {
            // TODO: add parse to duration
            throw IllegalArgumentException("Duration is too short, must be at least ${minimum.time}")
        }
        return duration;
    }

    override fun <S : Any> listSuggestions(
        context: CommandContext<S>,
        builder: SuggestionsBuilder
    ): CompletableFuture<Suggestions> {
        val units: MutableList<String> = mutableListOf("w","d","h","m","s")
        val reader = StringReader(builder.remaining);

        if (!reader.canRead()) {
            return builder.buildFuture();
        }

        var display = false;
        while (reader.canRead()) {
            try {
                reader.readLong();
                display = true;

                if (!reader.canRead()) break;
                display = false;

                val next = reader.read();
                if (next == ' ') break;

                while (units.contains(next.lowercase())) {
                    units.removeFirst();
                }

                reader.skipWhitespace();
            } catch (ex: CommandSyntaxException) {
                display = false;
                break;
            }
        }

        if (!display) return builder.buildFuture();

        val offset = builder.createOffset(builder.start + reader.cursor)
        units.forEach(offset::suggest)

        return offset.buildFuture();
    }
}