package me.preceding.menuapi.builder

import org.bukkit.inventory.ItemStack

data class MenuOptions(
    val placeholderItem: ItemStack,
    val autoUpdateTicks: Int
) {

    val autoUpdate: Boolean
        get() = autoUpdateTicks > 0

    companion object {
        @JvmStatic
        fun create(placeholderItem: ItemStack, autoUpdateTicks: Int = 0) = MenuOptions(placeholderItem, autoUpdateTicks)
    }

}