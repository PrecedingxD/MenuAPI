package me.preceding.menuapi.utils

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.MenuController
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.metadata.FixedMetadataValue

object MenuAPIUtils {

    var borderSlotsMap = mutableMapOf(

        54 to arrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 44,
            45, 46, 47, 48, 49, 50, 51, 52, 53
        ),

        45 to arrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 35,
            36, 37, 38, 39, 40, 41, 42, 43, 44
        ),

        36 to arrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35
        ),

        27 to arrayOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8,
            9, 17,
            18, 19, 20, 21, 22, 23, 24, 25, 26
        ),

        )

    fun translate(input: String): String {
        return ChatColor.translateAlternateColorCodes('&', input)
    }

    fun translate(input: MutableList<String>): MutableList<String> {
        for (i in 0 until input.size) {
            input[i] = translate(input[i])
        }
        return input
    }

    fun isSameInventory(inventory: Inventory?, compare: Inventory?): Boolean {
        if (inventory == null || compare == null) return false
        if (inventory.size != compare.size) return false
        for (i in 0 until inventory.size) {
            val inventoryItem = inventory.getItem(i)
            val compareItem = compare.getItem(i)
            if (inventoryItem != compareItem) {
                return false
            }
        }
        return true
    }

    fun closeInventory(player: Player) {
        player.closeInventory()
    }

    fun allowInventoryClose(player: Player) {
        player.setMetadata("closedInventory", FixedMetadataValue(MenuAPI.plugin, true))
    }

}