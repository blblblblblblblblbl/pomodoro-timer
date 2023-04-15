package blblblbl.simplelife.settings.data.persistent_storage.di


import blblblbl.simplelife.settings.di.SettingsFeature
import blblblbl.simplelife.settings.data.persistent_storage.utils.GsonParser
import blblblbl.simplelife.settings.data.persistent_storage.utils.JsonParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class StorageConverterModule {

    @Provides
    @SettingsFeature
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    @SettingsFeature
    fun provideJsonParser(@SettingsFeature gson:Gson): JsonParser = GsonParser(gson)

}