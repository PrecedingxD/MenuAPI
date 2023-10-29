package me.preceding.menuapi.listener

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.AsyncPlayerChatEvent

object MenuListener : Listener {

    @EventHandler
    fun onMenuInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val slot = event.slot
        val menu = MenuController.menuMap[player.uniqueId] ?: return
        val button = menu.getButtons(player)[slot] ?: return
        event.isCancelled = true
        if (event.clickedInventory == player.inventory) return
        Bukkit.getServer().scheduler.runTask(MenuAPI.plugin) {
            button.onClick(player, slot, event.click)
        }
    }

    @EventHandler
    fun onMenuClose(event: InventoryCloseEvent) {
        val player = event.player
        MenuController.menuMap.remove(player.uniqueId)
    }

    @EventHandler
    fun onPaginationInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val slot = event.slot
        val menu = MenuController.paginatedMenuMap[player.uniqueId] ?: return
        val button = menu.getResolvedButtons(player)[slot] ?: return
        event.isCancelled = true
        if (event.clickedInventory == player.inventory) return
        Bukkit.getServer().scheduler.runTask(MenuAPI.plugin) {
            button.onClick(player, slot, event.click)
        }
    }

    @EventHandler
    fun onPaginationClose(event: InventoryCloseEvent) {
        val player = event.player
        MenuController.paginatedMenuMap.remove(player.uniqueId)
    }

}