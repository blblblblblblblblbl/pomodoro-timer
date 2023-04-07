package blblblbl.simplelife.pomodorotimer.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier



@Module
@InstallIn(SingletonComponent::class)
class SharedPrefsModule(
){
    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    companion object {
        const val STORAGE_NAME = "StorageName"
    }

}