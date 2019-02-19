package gg.rsmod.plugins.content.areas.edgeville.chat

import gg.rsmod.game.model.entity.Player
import gg.rsmod.game.plugin.Plugin
import gg.rsmod.plugins.api.SkullIcon
import gg.rsmod.plugins.api.cfg.Items
import gg.rsmod.plugins.api.cfg.Npcs
import gg.rsmod.plugins.api.ext.*

val HIDE_STREAK_VARBIT = 1621
val LIMIT_TARGETS_VARBIT = 6503

val SKULL_SHORT_DURATION = 500
val SKULL_LONG_DURATION = 2000

arrayOf(Npcs.EMBLEM_TRADER, Npcs.EMBLEM_TRADER_316).forEach { npc ->
    on_npc_option(npc = npc, option = "talk-to") {
        it.suspendable { chat(it) }
    }

    on_npc_option(npc = npc, option = "trade") {
        open_bounty_store(it.player())
    }

    on_npc_option(npc = npc, option = "skull") {
        it.suspendable {
            if (it.player().hasSkullIcon(SkullIcon.NONE)) {
                give_pk_skull(it)
            } else {
                extend_pk_skull(it)
            }
        }
    }

    if (npc == Npcs.EMBLEM_TRADER) {
        on_npc_option(npc = npc, option = "hide-streaks") {
            it.suspendable {
                if (it.options("Yes", "No", title = "Hide kill streak data?") == 1) {
                    val player = it.player()
                    hide_killstreak_data(player)
                    player.message("Bounty Hunter kill streak data has now been hidden.")
                }
            }
        }
    } else if (npc == Npcs.EMBLEM_TRADER_316) {
        on_npc_option(npc = npc, option = "show-streaks") {
            it.suspendable {
                if (it.options("Yes", "No", title = "Show kill streak data?") == 1) {
                    val player = it.player()
                    show_killstreak_data(player)
                    player.message("Bounty Hunter kill streak data has now been activated.")
                }
            }
        }
    }
}

suspend fun chat(it: Plugin) {
    it.chatNpc("Hello, wanderer.", animation = 588)
    it.chatNpc("Don't suppose you've come across any strange...<br>emblems or artefacts along your journey?", animation = 589)
    it.chatPlayer("Not that I've seen.", animation = 588)
    it.chatNpc("If you do, please do let me know. I'll reward you<br>handsomely.", animation = 589)
    options(it)
}

suspend fun options(it: Plugin) {
    val player = it.player()

    val skullOption = if (player.hasSkullIcon(SkullIcon.NONE)) "Can I have a PK skull, please?" else "Can you make my PK skull last longer?"
    val killstreakOption = if (player.getVarbit(HIDE_STREAK_VARBIT) == 1) "I'd like to see kill streak data." else "I don't want to see kill streak data."
    val targetOption = if (player.getVarbit(LIMIT_TARGETS_VARBIT) == 0) "I'd like to limit my potential targets." else "I don't want to limit my potential targets."

    when (it.options("What rewards have you got?", skullOption, killstreakOption, targetOption, "That's nice.")) {
        1 -> rewards(it)
        2 -> pk_skull(it)
        3 -> killstreak_data(it)
        4 -> limit_targets(it)
        5 -> {
            it.chatPlayer("That's nice.", animation = 567)
        }
    }
}

suspend fun rewards(it: Plugin) {
    it.chatPlayer("What rewards have you got?", animation = 554)
    open_bounty_store(it.player())
}

fun open_bounty_store(p: Player) {
    p.openShop("Bounty Hunter Store")
}

suspend fun pk_skull(it: Plugin) {
    val player = it.player()
    if (player.hasSkullIcon(SkullIcon.NONE)) {
        it.chatPlayer("Can I have a PK skull, please?", animation = 554)
        give_pk_skull(it)
    } else {
        it.chatPlayer("Can you make my PK skull last longer?", animation = 554)
        extend_pk_skull(it)
    }
}

suspend fun give_pk_skull(it: Plugin) {
    if (it.options("Give me a PK skull.", "Cancel.", title = "A PK skull means you drop ALL your items on death.") == 1) {
        it.player().skull(SkullIcon.WHITE, durationCycles = SKULL_SHORT_DURATION)
        it.itemMessageBox("You are now skulled.", item = Items.SKULL)
    }
}

suspend fun extend_pk_skull(it: Plugin) {
    if (it.options("Yes", "No", title = "Extend your PK skull duration?") == 1) {
        it.player().skull(SkullIcon.WHITE, durationCycles = SKULL_LONG_DURATION)
        it.itemMessageBox("Your PK skull will now last for the full 20 minutes.", item = Items.SKULL)
    }
}

suspend fun killstreak_data(it: Plugin) {
    if (it.player().getVarbit(HIDE_STREAK_VARBIT) == 0) {
        it.chatPlayer("I don't want to see kill streak data.", animation = 554)
        hide_killstreak_data(it.player())
        it.chatNpc("Very well, you will no longer see kill streak data when<br>you are engaged in Bounty Hunting.", animation = 568)
    } else {
        it.chatPlayer("I'd like to see kill streak data.", animation = 554)
        show_killstreak_data(it.player())
        it.chatNpc("Very well, you will now see kill streak data when you<br>are engaged in Bounty Hunting.", animation = 568)
    }
}

fun hide_killstreak_data(p: Player) {
    p.setVarbit(HIDE_STREAK_VARBIT, 1)
}

fun show_killstreak_data(p: Player) {
    p.setVarbit(HIDE_STREAK_VARBIT, 0)
}

suspend fun limit_targets(it: Plugin) {
    val player = it.player()
    if (player.getVarbit(LIMIT_TARGETS_VARBIT) == 0) {
        it.chatPlayer("I'd like to limit my potential targets.", animation = 554)
        it.chatNpc("Very well, you will no longer be assigned targets deep<br>in the wilderness.", animation = 568)
    } else {
        it.chatPlayer("I don't want to limit my potential targets.", animation = 554)
        it.chatNpc("Very well, you may now be assigned targets deep in<br>the wilderness.", animation = 568)
    }
}