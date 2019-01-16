import gg.rsmod.plugins.osrs.api.helper.player
import gg.rsmod.plugins.osrs.content.inter.kod.KeptOnDeath

r.bindButton(parent = 387, child = 21) {
    val p = it.player()
    if (!p.lock.canInterfaceInteract()) {
        return@bindButton
    }
    KeptOnDeath.open(it.player())
}

r.bindInterfaceClose(interfaceId = KeptOnDeath.INTERFACE_ID) {
    /**
     * Have to resend inventory when this interface is closed as it sent a 'fake'
     * inventory container to the tab area, which can mess up other tab area
     * interfaces such as equipment stats.
     */
    it.player().inventory.dirty = true
}