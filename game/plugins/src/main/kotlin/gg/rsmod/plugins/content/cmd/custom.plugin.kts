package gg.rsmod.plugins.content.cmd

import gg.rsmod.game.model.priv.Privilege
import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.canTeleport
import gg.rsmod.plugins.content.magic.teleport

private val gracefulSet = setOf(
        Items.GRACEFUL_BOOTS,
        Items.GRACEFUL_TOP,
        Items.GRACEFUL_CAPE,
        Items.GRACEFUL_GLOVES,
        Items.GRACEFUL_HOOD,
        Items.GRACEFUL_LEGS
)

on_command("spawn_graceful", Privilege.ADMIN_POWER) {
    spawnGraceful(player, action = "spawn")
}

on_command("cook", Privilege.ADMIN_POWER) {
    player.openInterface(277, InterfaceDestination.MAIN_SCREEN)
    player.setComponentText(interfaceId = 277, component = 2, text = "You have completed the Cook's Assistant Quest!")
    player.setComponentItem(interfaceId = 277, component = 3, item = 1891, amountOrZoom = 260)
    player.setComponentText(interfaceId = 277, component = 8, text = "")
    player.setComponentText(interfaceId = 277, component = 9, text = "")
    player.setComponentText(interfaceId = 277, component = 10, text = "")
    player.setComponentText(interfaceId = 277, component = 11, text = "")
    player.setComponentText(interfaceId = 277, component = 12, text = "")
    player.setComponentText(interfaceId = 277, component = 13, text = "")
    player.setComponentText(interfaceId = 277, component = 14, text = "")
    player.setComponentText(interfaceId = 277, component = 8, text = "1 Quest Point")
    player.setComponentText(interfaceId = 277, component = 9, text = "300 Cooking XP")
}

on_command("alsetup", Privilege.ADMIN_POWER) {
    spawnInventory(player)
    player.equipment.set(EquipmentType.RING.id, Item(Items.RING_OF_DUELING8))
    player.equipment.set(EquipmentType.AMULET.id, Item(Items.AMULET_OF_GLORY_T6))

    spawnGraceful(player, action = "equip")
}

on_command("melee", Privilege.ADMIN_POWER) {
    player.equipment.set(EquipmentType.WEAPON.id, Item(Items.SCYTHE_OF_VITUR))
    player.equipment.set(EquipmentType.CAPE.id, Item(Items.INFERNAL_MAX_CAPE))
    player.equipment.set(EquipmentType.CHEST.id, Item(Items.BANDOS_CHESTPLATE))
    player.equipment.set(EquipmentType.LEGS.id, Item(Items.BANDOS_TASSETS))
    player.equipment.set(EquipmentType.BOOTS.id, Item(Items.PRIMORDIAL_BOOTS))
    player.equipment.set(EquipmentType.RING.id, Item(Items.WARRIOR_RING_I))
    player.equipment.set(EquipmentType.GLOVES.id, Item(Items.FEROCIOUS_GLOVES))
    player.equipment.set(EquipmentType.AMULET.id, Item(Items.AMULET_OF_TORTURE))
    player.equipment.set(EquipmentType.AMMO.id, Item(Items.RADAS_BLESSING_4))
    player.equipment.set(EquipmentType.HEAD.id, Item(Items.WARRIOR_HELM))

    player.inventory.add(item = Item(Items.SUPER_RESTORE4, 2))
    player.inventory.add(item = Item(Items.PRAYER_POTION4, 10))
    player.inventory.add(item = Item(Items.SARADOMIN_BREW4, 6))
    player.inventory.add(item = Item(Items.SANFEW_SERUM4, 4))

    player.calculateBonuses()
    player.calculateAndSetCombatLevel()
}

on_command("dia", Privilege.ADMIN_POWER) {
    player.queue(TaskPriority.STRONG) {
        val tile = getTeleportOption()
        player.Teleport(tile = tile)
    }
}

fun Player.Teleport(tile: Tile) {
    val SOUNDAREA_ID = 200
    val SOUNDAREA_RADIUS = 5
    val SOUNDAREA_VOLUME = 1

    if(canTeleport(TeleportType.MODERN)) {
        world.spawn(AreaSound(tile, SOUNDAREA_ID, SOUNDAREA_RADIUS, SOUNDAREA_VOLUME))
        teleport(tile, TeleportType.MODERN)
    }
}

suspend fun QueueTask.getTeleportOption(): Tile {
    // I think this needs to return a tile, and then teleport so we can add the teleport anim
    val teleportOption = when(options(
            "Teleport to Stronghold Slayer Cave",
            "Teleport to Morytania Slayer Tower",
            "Teleport to Rellekka Slayer Caves",
            "Teleport to Tarn's Lair",
            "Teleport to Dark Beasts"
    )) {
        1 -> return Tile(2434, 3422)
        2 -> return Tile(3420, 3536)
        3 -> return Tile(2804, 9999)
        4 -> return Tile(3185, 4601)
        5 -> return Tile(2030, 4637)
        else -> Tile(1, 1)
    }
    return teleportOption
}

fun spawnInventory(player: Player) {
    player.inventory.removeAll()
    player.inventory.set(0, Item(Items.AIR_RUNE, 1000000))
    player.inventory.set(1, Item(Items.LAW_RUNE, 1000000))
    player.inventory.set(2, Item(Items.EARTH_RUNE, 1000000))
    player.inventory.set(3, Item(Items.FIRE_RUNE, 1000000))
    player.inventory.set(4, Item(Items.WATER_RUNE, 1000000))
    player.inventory.set(5, Item(Items.BANDOS_GODSWORD, 1))
    player.inventory.set(6, Item(Items._3RD_AGE_PICKAXE, 1))
    player.inventory.set(7, Item(Items.DRAGON_AXE, 1))
    player.inventory.set(8, Item(Items.ECTOPHIAL, 1))
    player.inventory.set(9, Item(Items.SLAYER_RING_8, 1))

    for (i in 0 until player.getSkills().maxSkills) {
        player.getSkills().setBaseLevel(i, 99)
    }
    player.calculateAndSetCombatLevel()
}

fun spawnGraceful(player: Player, action: String = "equip") {
    // Maybe make this an player.equipment.set
    val freeInventorySpaces = player.inventory.freeSlotCount

    when(action) {
        "equip" -> {
            player.equipment.set(EquipmentType.HEAD.id, Item(Items.GRACEFUL_HOOD))
            player.equipment.set(EquipmentType.CAPE.id, Item(Items.GRACEFUL_CAPE))
            player.equipment.set(EquipmentType.CHEST.id, Item(Items.GRACEFUL_TOP))
            player.equipment.set(EquipmentType.LEGS.id, Item(Items.GRACEFUL_LEGS))
            player.equipment.set(EquipmentType.BOOTS.id, Item(Items.GRACEFUL_BOOTS))
            player.equipment.set(EquipmentType.GLOVES.id, Item(Items.GRACEFUL_GLOVES))
        }
        "spawn" -> {
            if(freeInventorySpaces >= 5) {
                for(i in gracefulSet) {
                    player.inventory.add(i)
                }
                player.message("A full high quality Graceful lightweight agility set has been placed in your inventory.")
            } else {
                for(i in gracefulSet) {
                    player.world.spawn(GroundItem(item = i, amount = 1, tile = player.tile, owner = player))
                }
                player.message("You baffoon.. You don't have enough inventory spaces for that. I threw them on the ground.")
            }
        }
    }
}