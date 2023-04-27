package blblblbl.simplelife.onboarding

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class ShowOnBoarding @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val editor = sharedPreferences.edit()
    private fun addProperty(name: String?, value: String?) {

        Log.d("MyLog", "addProperty:$name $value")
        editor.putString(name, value)
        editor.apply()
    }

    private fun clear() {
        editor.clear()
        editor.apply()
    }

    private fun getProperty(name: String?): String? {
        return sharedPreferences.getString(name, null)
    }
    fun IsShown():Boolean{
        val state = getProperty(ONBOARDING_SHOWN_STATE_KEY)
        state?.let {
            if (state=="true") return true
        }
        return false
    }
    fun saveShown(){
        addProperty(ONBOARDING_SHOWN_STATE_KEY,"true")
    }
    companion object{
        const val ONBOARDING_SHOWN_STATE_KEY = "ONBOARDING_SHOWN_STATE_KEY"
    }

}