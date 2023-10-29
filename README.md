# MenuAPI
Lightweight Asynchronous Menu API for all versions made in Kotlin

## How To Utilize in Your Project

You have to initialize the API by using MenuAPI#register. Here's an example preset for 1.8.

```kt
        MenuAPI.register(
            plugin = this,
            menuOptions = MenuOptions.create(
                placeholderItem = ItemBuilder.of(Material.STAINED_GLASS_PANE).data(15.toShort()).name(" ").build(),
                autoUpdateTicks = 15
            ),
            MenuPaginationOptions.create(
                paginationTitleFormat = "{title}&r &7({currentPage}/{maxPages})",
                previousPageItem = ItemBuilder.of(Material.SPECKLED_MELON)
                    .name("&aNext Page &7(&e{nextPage}&7/&e{maxPages}&7)")
                    .setLore(CC.translate(mutableListOf(
                        "&7Click to navigate to",
                        "&7the next page.",
                    )))
                    .build(),
                nextPageItem = ItemBuilder.of(Material.MELON)
                    .name("&cPrevious Page &7(&e{previousPage}&7/&e{maxPages}&7)")
                    .setLore(CC.translate(mutableListOf(
                        "&7Click to navigate to",
                        "&7the previous page.",
                    )))
                    .build(),
                alreadyOnLastPageItem = null,
                alreadyOnFirstPageItem = null,
                viewablePageItemCurrent = ItemBuilder.of(Material.ENCHANTED_BOOK)
                    .name(CC.translate("&e&lPage {navigationPage}"))
                    .setLore(CC.translate(mutableListOf(
                        "&r",
                        "&eSelected!"
                    ))
                    ).build(),
                viewablePageItem = ItemBuilder.of(Material.BOOK)
                    .name(CC.translate("&e&lPage {navigationPage}"))
                    .setLore(CC.translate(mutableListOf(
                        "&r",
                        "&eClick to open!"
                    ))
                ).build()
            ),
            MenuSoundOptions.create(
                nextPageSound = CompiledSound(Sound.CLICK, 100.0F, 1.0F),
                previousPageSound = CompiledSound(Sound.CLICK, 100.0F, 1.0F),
                alreadyOnFirstPageSound = CompiledSound(Sound.NOTE_BASS_DRUM, 100.0F, 0.0F),
                alreadyOnLastPageSound = CompiledSound(Sound.NOTE_BASS_DRUM, 100.0F, 0.0F)
            )
        )
    }
```
