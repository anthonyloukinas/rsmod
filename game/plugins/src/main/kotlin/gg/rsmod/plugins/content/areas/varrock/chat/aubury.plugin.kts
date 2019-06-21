package gg.rsmod.plugins.content.areas.varrock.chat

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

on_npc_option(npc = Npcs.AUBURY, option = "talk-to") {
    player.queue { dialog(this) }
}

on_npc_option(npc = Npcs.AUBURY, option = "teleport") {
    // TODO: QuestReq=> Need to have completed Rune Mysteries
    player.queue { teleport(this) }
}

on_npc_option(npc = Npcs.AUBURY, option = "trade") {
    open_shop(player)
}

suspend fun dialog(it: QueueTask) {
    it.chatNpc("Do you want to buy some runes?")
    when(it.options(
            "Can you tell me about your cape?",
            "Yes please!",
            "Oh, it's a rune shop. No thank you, then.",
            "Can you teleport me to the Rune Essence?")) {
        1 -> {
            it.chatNpc("Certainly! Skillcapes are a symbol of achievement. Only people who have mastered a skill and reached level 99 can get their hands on them and gain the benefits they carry.")
            it.chatNpc("The Cape of Runecrafting has been upgraded with each talisman, allowing you to access all Runecrafting altars. Is there anything else I can help you with?")
            when(it.options("I'd like to view your store please.", "No thank you.")) {
                1 -> {
                    open_shop(it.player)
                }
                2 -> {
                    it.chatPlayer("No thank you.")
                    it.chatNpc("Well, if you find someone who does want runes, please send them my way.")
                }
            }
        }
        2 -> {
            open_shop(it.player)
        }
        3 -> {
            it.chatPlayer("Oh, it's a rune shop. No thank you, then.")
            it.chatNpc("Well, if you find someone who does want runes, please send them my way.")
        }
        4 -> {
            it.chatPlayer("Can you teleport me to the Rune Essence?")
            teleport(it)
        }
    }
}

suspend fun teleport(it: QueueTask) {
    // TODO: Add NPC Teleport Animation
    it.player.getInteractingNpc().forceChat("<i>Senventior Disthine Molenko!</i>")
    it.player.teleport(Tile(2911,4832), TeleportType.LUNAR)
}

fun open_shop(p: Player) {
    p.openShop("Aubury's Rune Shop.")
}