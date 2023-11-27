package me.preceding.menuapi.menu

import org.bukkit.entity.Player

abstract class AbstractMenu {

    abstract fun openInternal(player: Player, checkIfClosed: Boolean = false)
    abstract fun onCloseInternal(player: Player)

}