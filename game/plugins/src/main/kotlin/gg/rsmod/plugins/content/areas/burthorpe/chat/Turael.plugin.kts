package gg.rsmod.plugins.content.areas.burthorpe.chat

on_npc_option(npc = Npcs.TURAEL, option = "talk-to") {
    player.queue {
        dialog(this)
    }
}

suspend fun dialog(it: QueueTask) {
    it.chatNpc("'Ello, and what are you after then?")
    when(it.options(
            "I need another assignment.",
            "Have you any rewards for me, or anything to trade?",
            "Let's talk about the difficulty, of my assignments.",
            "I'm here about blessed axes again.", // I think this is after a quest has been done?
            "Er... Nothing..."
    )) {
        1 -> {

        }
        2 -> {
            it.chatPlayer("Have you any rewards for me, or anything to trade?")
            it.chatNpc("I have quite a few rewards you can earn, and a wide variety of Slayer equipment for sale.")
            when(it.options(
                    "Look at rewards.",
                    "Look at shop.",
                    "Cancel."
            )) {
                1 -> {
                    open_rewards_shop(it.player)
                }
                2 -> {
                    open_slayer_shop(it.player)
                }
                3 -> {
                    return
                }
            }
        }
        3 -> {
            it.chatPlayer("Let's talk about the difficulty, of my assignments.")
            it.chatNpc("The Slayer Masters will take your combat level into account when choosing tasks for you, so you shouldn't get anything too hard.")
            // TODO: Implement slayer player data options for below option
            when(it.options(
                    "That's fine - I don't want anything too tough.",
                    "Stop checking my combat level - I can take anything!"
            )) {
                1 -> {
                    it.chatPlayer("That's fine - I don't want anything too tough.")
                    it.chatNpc("Okay, we'll keep checking your combat level.")
                }
                2 -> {
                    it.chatPlayer("Stop checking my combat level - I can take anything!")
                    it.chatNpc("Okay, from now on, all the Slayer Masters will assign you anything from their lists, regardless of your combat level.")
                }
            }
        }
        5 -> {
            it.chatPlayer("Er... Nothing...")
        }
    }
}

fun open_slayer_shop(p: Player) {
    p.openShop("Slayer Equipment")
}

fun open_rewards_shop(p: Player) {
    p.setInterfaceUnderlay(color = -1, transparency = -1)
    p.openInterface(interfaceId = 426, dest = InterfaceDestination.MAIN_SCREEN)
}