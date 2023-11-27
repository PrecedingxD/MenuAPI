package me.preceding.menuapi

import me.preceding.menuapi.builder.MenuOptions
import me.preceding.menuapi.builder.MenuPaginationOptions
import me.preceding.menuapi.builder.MenuSoundOptions
import me.preceding.menuapi.listener.MenuListener
import me.preceding.menuapi.task.MenuAutoUpdateTask
import org.bukkit.plugin.java.JavaPlugin

object MenuAPI {

    lateinit var plugin: JavaPlugin
    lateinit var menuOptions: MenuOptions
    lateinit var paginationOptions: MenuPaginationOptions
    lateinit var soundOptions: MenuSoundOptions

    fun register(
        plugin: JavaPlugin,
        menuOptions: MenuOptions,
        paginationOptions: MenuPaginationOptions,
        soundOptions: MenuSoundOptions
    ): MenuAPI {
        MenuAPI.plugin = plugin
        MenuAPI.menuOptions = menuOptions
        MenuAPI.paginationOptions = paginationOptions
        MenuAPI.soundOptions = soundOptions
        plugin.server.pluginManager.registerEvents(MenuListener, plugin)
        registerAutoUpdateTask()

        return this
    }

    private fun registerAutoUpdateTask() {
        if (!menuOptions.autoUpdate) return
        plugin.server.scheduler.runTaskTimer(plugin, MenuAutoUpdateTask, 0L, menuOptions.autoUpdateTicks.toLong())
    }


}