package me.preceding.menuapi.task

import me.preceding.menuapi.menu.MenuController
import org.bukkit.Bukkit

object MenuAutoUpdateTask : Runnable {

    override fun run() {
        for(player in Bukkit.getServer().onlinePlayers) {
            val menu = MenuController.menuMap[player.uniqueId]
            val paginatedMenu = MenuController.paginatedMenuMap[player.uniqueId]
            if(menu != null && menu.autoUpdate) {
                menu.open(player, true)
            } else if(paginatedMenu != null && paginatedMenu.autoUpdate) {
                paginatedMenu.open(player, true)
            }
        }
    }

}