package me.preceding.menuapi.menu.pagination

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.AbstractMenu
import me.preceding.menuapi.menu.MenuController
import me.preceding.menuapi.menu.button.Button
import me.preceding.menuapi.menu.pagination.button.PageButton
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.concurrent.CompletableFuture
import kotlin.math.ceil

abstract class PaginatedMenu(
    var title: String,
    var size: Int
) : AbstractMenu() {

    var page = 1
    var maxPages = 1
    var autoUpdate = true

    init {
        if (size % 9 != 0) throw IllegalArgumentException("Menus must have a size that can be divided by 9.")
        if (size < 27) throw IllegalArgumentException("Paginated menus must have a size of at least 27.")
    }

    override fun onCloseInternal(player: Player) {
        onClose(player)
    }

    open fun onClose(player: Player) {

    }

    open fun getBorderSlots(): Array<Int> {
        return MenuAPIUtils.borderSlotsMap[size]!!
    }

    //(({_page} - 1) * 18) + 1

    abstract fun getButtons(player: Player): MutableList<Button>

    open fun getMaximumItemsPerPage(): Int {
        var maximumItems = 0
        val borderSlots = getBorderSlots()
        for (i in 0 until size) {
            val slot = borderSlots.getOrNull(i)
            if (slot != null) {
                continue
            }
            maximumItems++
        }
        return maximumItems
    }

    fun calculateSize(buttons: MutableMap<Int, Button>): Int {
        val maxItems = buttons.filter { !getBorderSlots().contains(it.key) }.size
        return if (maxItems <= 9) {
            18
        } else if (maxItems <= 18) {
            27
        } else if (maxItems <= 27) {
            36
        } else if (maxItems <= 36) {
            45
        } else if (maxItems <= 45) {
            54
        } else -1
    }

    fun getAvailableSlots(): MutableList<Int> {
        val availableSlots = mutableListOf<Int>()
        val borderSlots = getBorderSlots()
        for (i in 0 until size) {
            if (borderSlots.contains(i)) continue
            availableSlots.add(i)
        }
        return availableSlots
    }

    fun open(player: Player) {
        open(player, false)
    }

    fun open(player: Player, checkIfClosed: Boolean = false) {
        MenuController.open(player, this, checkIfClosed)
    }

    override fun openInternal(player: Player, checkIfClosed: Boolean) {
        val buttons = getButtons(player)
        maxPages = if (buttons.isEmpty()) 1 else ceil(buttons.size / getMaximumItemsPerPage().toDouble()).toInt()
        val openInventory = player.openInventory.topInventory
        createInventory(player, checkIfClosed, true) {
            if (MenuAPIUtils.isSameInventory(openInventory, it)) return@createInventory
            Bukkit.getServer().scheduler.runTask(MenuAPI.plugin) {
                //closedByMenu = true
                player.openInventory(it)
                MenuController.paginatedMenuMap[player.uniqueId] = this
            }
        }
    }

    /*fun openSync(player: Player) {
        openSync(player, false)
    }

    fun openSync(player: Player, checkIfClosed: Boolean = false) {
        val buttons = getButtons(player)
        maxPages = if (buttons.isEmpty()) 1 else ceil(buttons.size / getMaximumItemsPerPage().toDouble()).toInt()
        createInventory(player, checkIfClosed, false) {
            Bukkit.getServer().scheduler.runTask(MenuAPI.plugin) {
                closedByMenu = true
                player.openInventory(it)
                MenuController.paginatedMenuMap[player.uniqueId] = this
            }
        }
    }*/

    open fun getOverheadItems(): MutableMap<Int, Button> {
        return hashMapOf()
    }

    fun getResolvedButtons(player: Player): MutableMap<Int, Button> {
        val buttons = getButtons(player)
        val toReturn = mutableMapOf<Int, Button>()
        val borderSlots = getBorderSlots()
        val availableSlots = getAvailableSlots()
        var index = ((page - 1) * availableSlots.size)
        for (i in borderSlots) {
            toReturn[i] = Button.placeholder()
        }
        toReturn[0] = PageButton(this, -1)
        toReturn[8] = PageButton(this, 1)
        for (i in availableSlots) {
            val button = buttons.getOrNull(index) ?: continue
            toReturn[i] = button
            index++
        }
        for (entry in getOverheadItems().entries) {
            toReturn[entry.key] = entry.value
        }
        return toReturn
    }

    private fun createInventory(
        player: Player,
        checkIfClosed: Boolean = false,
        openAsync: Boolean,
        function: (inventory: Inventory) -> Unit,
    ) {
        val buttons = getResolvedButtons(player)
        val inventory =
            Bukkit.createInventory(
                null, calculateSize(buttons), ChatColor.translateAlternateColorCodes(
                    '&', MenuAPI.paginationOptions.paginationTitleFormat
                        .replace("{currentPage}", page.toString())
                        .replace("{maxPages}", maxPages.toString())
                        .replace("{title}", title)
                )
            )

        if (openAsync) {
            CompletableFuture.runAsync {
                for (i in 0 until size) {
                    val button = buttons[i] ?: continue
                    inventory.setItem(i, button.getButtonItem(player))
                }
                if (checkIfClosed && MenuController.paginatedMenuMap.getOrDefault(player.uniqueId, null) != this) {
                    return@runAsync
                }
                function.invoke(inventory)
            }.exceptionally { t ->
                if (t != null) {
                    player.sendMessage(MenuAPIUtils.translate("&cThere was an error while attempting to open this menu."))
                    t.printStackTrace()
                }
                null
            }
        } else {
            for (i in 0 until size) {
                val button = buttons[i] ?: continue
                inventory.setItem(i, button.getButtonItem(player))
            }
            if (checkIfClosed && MenuController.paginatedMenuMap.getOrDefault(player.uniqueId, null) != this) {
                return
            }
            function.invoke(inventory)
        }
    }

}