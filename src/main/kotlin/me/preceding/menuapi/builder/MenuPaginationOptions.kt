package me.preceding.menuapi.builder

import org.bukkit.inventory.ItemStack

data class MenuPaginationOptions(
    val previousPageItem: ItemStack,
    val nextPageItem: ItemStack,
    val alreadyOnFirstPageItem: ItemStack?,
    val alreadyOnLastPageItem: ItemStack?,
    val paginationTitleFormat: String,
    val viewablePageButton: ItemStack,
    val viewablePageButtonCurrent: ItemStack,
    val viewAllPagesMenuTitle: String
) {

    companion object {
        @JvmStatic
        fun create(
            previousPageItem: ItemStack,
            nextPageItem: ItemStack,
            alreadyOnFirstPageItem: ItemStack?,
            alreadyOnLastPageItem: ItemStack?,
            paginationTitleFormat: String = "({currentPage}/{maxPages}) {title}",
            viewablePageItem: ItemStack,
            viewablePageItemCurrent: ItemStack,
            viewAllPagesMenuTitle: String = "View All Pages",
        ) =
            MenuPaginationOptions(previousPageItem, nextPageItem, alreadyOnFirstPageItem, alreadyOnLastPageItem, paginationTitleFormat, viewablePageItem, viewablePageItemCurrent, viewAllPagesMenuTitle)
    }

}