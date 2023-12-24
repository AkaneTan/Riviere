package org.akanework.riviere.ui.data

object HolderTypes {
    data class PresetType (
        val icon: Int? = null,
        val desc: String? = null,
        val defVal: Float? = null,
        val currencyType: Char? = null,
        val isBlock: Boolean = false
    )

    data class ChipType (
        val icon: Int? = null,
        val desc: String? = null,
        val isBlock: Boolean = false
    )
}