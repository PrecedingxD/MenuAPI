package me.preceding.menuapi.menu.pagination.button

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.button.Button
import me.preceding.menuapi.menu.pagination.PaginatedMenu
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

class ViewPageButton(
    private val menu: PaginatedMenu,
    private val currentPage: Int,
    private val navigationPage: Int
) : Button() {

    override fun getButtonItem(player: Player): ItemStack {
        return (if(currentPage == navigationPage) MenuAPI.paginationOptions.viewablePageButtonCurrent else MenuAPI.paginationOptions.viewablePageButton).clone().apply {
            val meta = itemMeta
            meta.displayName = MenuAPIUtils.translate(meta.displayName
                .replace("{navigationPage}", navigationPage.toString())
            )
            itemMeta = meta
        }
    }

    override fun onClick(player: Player, slot: Int, clickType: ClickType) {
        MenuAPI.soundOptions.nextPageSound.play(player)
        menu.page = navigationPage
        menu.open(player)
    }

}