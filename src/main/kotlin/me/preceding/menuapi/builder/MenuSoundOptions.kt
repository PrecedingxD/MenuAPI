package me.preceding.menuapi.builder

import me.preceding.menuapi.utils.CompiledSound

data class MenuSoundOptions(
    val nextPageSound: CompiledSound,
    val previousPageSound: CompiledSound,
    val alreadyOnFirstPageSound: CompiledSound,
    val alreadyOnLastPageSound: CompiledSound
) {

    companion object {
        @JvmStatic
        fun create(
            nextPageSound: CompiledSound,
            previousPageSound: CompiledSound,
            alreadyOnFirstPageSound: CompiledSound,
            alreadyOnLastPageSound: CompiledSound
        ) =
            MenuSoundOptions(nextPageSound, previousPageSound, alreadyOnFirstPageSound, alreadyOnLastPageSound)
    }

}
