package me.preceding.menuapi.utils

import org.bukkit.Sound
import org.bukkit.entity.Player

data class CompiledSound(
    val sound: Sound,
    val volume: Float,
    val pitch: Float
) {

    fun play(player: Player) {
        player.playSound(player.location, sound, volume, pitch)
    }

}