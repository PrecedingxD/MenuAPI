package me.preceding.menuapi.menu.pagination.menu

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.Menu
import me.preceding.menuapi.menu.button.Button
import me.preceding.menuapi.menu.pagination.PaginatedMenu
import me.preceding.menuapi.menu.pagination.button.ViewPageButton
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.entity.Player

class ViewAllPagesMenu(
    private val menu: PaginatedMenu
) : Menu(MenuAPI.paginationOptions.viewAllPagesMenuTitle,
    if(menu.maxPages >= 28) (9 * 6)
    else if(menu.maxPages >= 21) (9 * 5)
    else if(menu.maxPages >= 14) (9 * 4)
    else if(menu.maxPages >= 7) (9 * 4)
    else 9 * 3
) {

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()
        for(i in MenuAPIUtils.borderSlotsMap[size] ?: return buttons) {
            buttons[i] = Button.placeholder()
        }
        var current = 1
        for(i in 0 until size) {
            if(buttons.contains(i)) continue
            if(current > menu.maxPages) break
            buttons[i] = ViewPageButton(menu, menu.page, current)
            current++
        }
        return buttons
    }

}