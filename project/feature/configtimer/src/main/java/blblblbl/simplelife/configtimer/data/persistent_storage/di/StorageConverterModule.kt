package blblblbl.simplelife.configtimer.data.persistent_storage.di

import blblblbl.simplelife.configtimer.data.di.ConfigFeature
import blblblbl.simplelife.configtimer.data.persistent_storage.utils.GsonParser
import blblblbl.simplelife.configtimer.data.persistent_storage.utils.JsonParser
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
    @ConfigFeature
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    @ConfigFeature
    fun provideJsonParser(@ConfigFeature gson:Gson): JsonParser = GsonParser(gson)

}