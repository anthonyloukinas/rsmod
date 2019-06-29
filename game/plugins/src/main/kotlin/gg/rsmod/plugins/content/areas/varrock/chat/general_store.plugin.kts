package gg.rsmod.plugins.content.areas.varrock.chat

for(n in arrayOf(Npcs.SHOP_KEEPER_2815, Npcs.SHOP_ASSISTANT_2816)) {
    on_npc_option(npc = n, option = "talk-to") {
        player.queue { dialog(this) }
    }

    on_npc_option(npc = n, option = "trade") {
        player.queue { open_shop(player) }
    }
}


suspend fun dialog(it: QueueTask) {
    it.chatNpc("Can I help you at all?")
    when(it.options(
            "Yes please. What are you selling?",
            "No thanks."
    )) {
        1 -> {
            open_shop(it.player)
        }
        2 -> {
            it.chatPlayer("No thanks.")
        }
    }
}

fun open_shop(p: Player) {
    p.openShop("Varrock General Store")
}