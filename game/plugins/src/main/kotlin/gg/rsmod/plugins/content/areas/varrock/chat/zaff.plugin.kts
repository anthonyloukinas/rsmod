package gg.rsmod.plugins.content.areas.varrock.chat

on_npc_option(npc = Npcs.ZAFF, option = "talk-to") {
    player.queue { dialog(this) }
}

on_npc_option(npc = Npcs.ZAFF, option = "trade") {
    open_shop(p = player)
}

suspend fun dialog(it: QueueTask) {
    it.chatNpc("Would you like to buy or sell some staffs? Or is there something else you need?")
    when(it.options(
            "Yes please!",
            // TODO: Add extra stock content
            "Have you any extra stock of battlestaffs I can buy?",
            "No, thank you."
            // TODO: Add option when quest exists
            // "Can I have another ring?"
    )) {
        1 -> {
            open_shop(it.player)
        }
        2 -> {
            open_shop(it.player)
        }
        3 -> {
            it.chatPlayer("No, thank you.")
            it.chatNpc("Well, 'stick' your head in again if you change your mind.")
            it.chatPlayer("Huh, terrible pun! You just can't get the 'staff' these days!")
        }
    }
}

fun open_shop(p: Player) {
    p.openShop("Zaff's Superior Staffs!")
}