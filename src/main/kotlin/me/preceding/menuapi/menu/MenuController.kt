package me.preceding.menuapi.menu

import me.preceding.menuapi.menu.pagination.PaginatedMenu
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.entity.Player
import java.util.*

object MenuController {

    val menuMap = hashMapOf<UUID, Menu>()
    val paginatedMenuMap = hashMapOf<UUID, PaginatedMenu>()

    fun open(player: Player, menu: AbstractMenu, checkIfClosed: Boolean = false) {
        menu.openInternal(player, checkIfClosed)
    }

}