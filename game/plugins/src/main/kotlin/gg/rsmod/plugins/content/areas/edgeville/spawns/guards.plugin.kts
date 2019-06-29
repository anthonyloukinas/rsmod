package gg.rsmod.plugins.content.areas.edgeville.spawns

// Near jail
spawn_npc(npc = Npcs.GUARD_3094, x = 3108, z = 3513, walkRadius = 5)
spawn_npc(npc = Npcs.GUARD_3094, x = 3110, z = 3515, walkRadius = 5)
spawn_npc(npc = Npcs.GUARD_3094, x = 3114, z = 3513, walkRadius = 5)
spawn_npc(npc = Npcs.GUARD_3094, x = 3114, z = 3517, walkRadius = 5)

// Near ditch
spawn_npc(npc = Npcs.GUARD_3094, x = 3094, z = 3518, walkRadius = 5)
spawn_npc(npc = Npcs.GUARD_3094, x = 3085, z = 3519, walkRadius = 5)

set_combat_def(npc = Npcs.GUARD_3094) {
    configs {
        attackSpeed = 4
        respawnDelay = 45
    }

    stats {
        hitpoints = 22
        attack = 19
        strength = 18
        defence = 14
        magic = 1
        ranged = 1
    }

    bonuses {
        attackBonus = 4
        defenceStab = 18
        defenceSlash = 25
        defenceCrush = 19
        defenceMagic = -4
        defenceRanged = 20
    }

    anims {
        death = 836
    }
}