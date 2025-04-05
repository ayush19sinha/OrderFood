package my.android.orderfood.data.repository

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import my.android.orderfood.data.model.MenuItem

class MenuRepository(private val context: Context) {
    fun getMenuData(): MenuData {
        val jsonString = context.assets.open("menu_data.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }
}

@Serializable
data class MenuData(
    val triedTastedLoved: List<MenuItem>,
    val lookingForMore: List<MenuItem>
)