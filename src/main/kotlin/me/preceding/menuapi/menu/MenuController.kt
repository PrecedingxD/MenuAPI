package me.preceding.menuapi.menu

import me.preceding.menuapi.menu.pagination.PaginatedMenu
import java.util.*

object MenuController {

    val menuMap = hashMapOf<UUID, Menu>()
    val paginatedMenuMap = hashMapOf<UUID, PaginatedMenu>()

}