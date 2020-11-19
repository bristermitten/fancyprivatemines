package me.bristermitten.fancyprivatemines.hook.fawe

import com.boydti.fawe.util.EditSessionBuilder
import com.sk89q.worldedit.EditSession
import org.bukkit.World
import java.util.*

private val sessionCache = WeakHashMap<World, EditSession>()

internal val World.editSession
    get() = sessionCache.getOrPut(this) {
        EditSessionBuilder(this.name)
                .allowedRegionsEverywhere()
                .fastmode(true)
                .changeSetNull()
                .build().apply { enableQueue() }
    }
