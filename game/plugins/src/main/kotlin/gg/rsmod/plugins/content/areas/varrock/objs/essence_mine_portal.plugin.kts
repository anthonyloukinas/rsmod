package gg.rsmod.plugins.content.areas.varrock.objs

on_obj_option(obj = Objs.PORTAL_34774, option = "Use") {
    player.queue {
        player.lock()
        wait(1)
        player.moveTo(3253, 3402)
        player.unlock()
    }
}
