package me.preceding.menuapi.menu

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.button.Button
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.concurrent.CompletableFuture

abstract class Menu(
    var title: String,
    var size: Int
) {

    var autoUpdate = true

    init {
        if (size % 9 != 0) throw IllegalArgumentException("Menus must have a size that can be divided by 9.")
    }

    abstract fun getButtons(player: Player): MutableMap<Int, Button>

    fun open(player: Player, checkIfClosed: Boolean = false) {
        val openInventory = player.openInventory.topInventory
        createInventory(player, checkIfClosed) {
            if(MenuAPIUtils.isSameInventory(openInventory, it)) return@createInventory
            Bukkit.getServer().scheduler.runTask(MenuAPI.plugin) {
                player.openInventory(it)
                MenuController.menuMap[player.uniqueId] = this
            }
        }
    }

    private fun createInventory(player: Player, checkIfClosed: Boolean, function: (inventory: Inventory) -> Unit) {
        val inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title))
        val buttons = getButtons(player)
        CompletableFuture.runAsync {
            for (i in 0 until size) {
                val button = buttons[i] ?: continue
                inventory.setItem(i, button.getButtonItem(player))
            }
            if (checkIfClosed && !MenuController.menuMap.contains(player.uniqueId)) {
                return@runAsync
            }
            function.invoke(inventory)
        }.whenComplete { _, t ->
            if (t == null) return@whenComplete
            t.printStackTrace()
            player.sendMessage("${ChatColor.RED}There was an issue while attempting to open this menu.")
        }
    }

}