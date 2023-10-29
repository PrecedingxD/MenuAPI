package me.preceding.menuapi.menu.pagination.button

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.MenuController
import me.preceding.menuapi.menu.button.Button
import me.preceding.menuapi.menu.pagination.PaginatedMenu
import me.preceding.menuapi.menu.pagination.menu.ViewAllPagesMenu
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class PageButton(
    private val menu: PaginatedMenu,
    private val mod: Int
) : Button() {

    override fun getButtonItem(player: Player): ItemStack? {
        val item = if (mod > 0) {
            if (hasNext()) {
                MenuAPI.paginationOptions.nextPageItem
            } else {
                MenuAPI.paginationOptions.alreadyOnLastPageItem ?: placeholder().getButtonItem(player)
            }
        } else {
            if (menu.page + mod <= 0) {
                MenuAPI.paginationOptions.alreadyOnFirstPageItem ?: placeholder().getButtonItem(player)
            } else {
                MenuAPI.paginationOptions.previousPageItem
            }
        }?.clone()?.apply {
            val meta = itemMeta
            meta.displayName = meta.displayName
                .replace("{page}", menu.page.toString())
                .replace("{previousPage}", (menu.page + mod).toString())
                .replace("{nextPage}", (menu.page + mod).toString())
                .replace("{maxPages}", (menu.maxPages).toString())
            itemMeta = meta
        }
        return item
    }

    override fun onClick(player: Player, slot: Int, clickType: ClickType) {
        if(getButtonItem(player) == null || getButtonItem(player)!!.isSimilar(Button.placeholder().getButtonItem(player))) return
        when(clickType) {
            ClickType.LEFT -> {
                if (menu.page + mod <= 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are already on the first page."))
                    return
                }
                if (!hasNext()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are already on the last page."))
                    return
                }
                menu.page += mod
                menu.open(player)
                if (mod >= 1) {
                    MenuAPI.soundOptions.nextPageSound.play(player)
                } else if (mod < 1) {
                    MenuAPI.soundOptions.previousPageSound.play(player)
                }
            } ClickType.RIGHT -> {
                ViewAllPagesMenu(menu).open(player)
            } else -> {}
        }

    }

    fun hasNext(): Boolean {
        return menu.page + mod <= menu.maxPages
    }

}