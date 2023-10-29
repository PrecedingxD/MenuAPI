package me.preceding.menuapi.menu.button

import me.preceding.menuapi.MenuAPI
import me.preceding.menuapi.menu.MenuController
import me.preceding.menuapi.utils.MenuAPIUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

abstract class Button {

    companion object {

        @JvmStatic
        fun placeholder() : Button {
            return object: Button() {

                override fun getButtonItem(player: Player): ItemStack {
                    return MenuAPI.menuOptions.placeholderItem
                }

                override fun onClick(player: Player, slot: Int, clickType: ClickType) {
                }

            }
        }

        @JvmStatic
        fun of(item: ItemStack, function: (player: Player, slot: Int, clickType: ClickType) -> Unit) : Button {
            return object: Button() {
                override fun getButtonItem(player: Player): ItemStack {
                    return item
                }

                override fun onClick(player: Player, slot: Int, clickType: ClickType) {
                    function.invoke(player, slot, clickType)
                }

            }
        }

    }

    abstract fun getButtonItem(player: Player) : ItemStack?
    abstract fun onClick(player: Player, slot: Int, clickType: ClickType)


}