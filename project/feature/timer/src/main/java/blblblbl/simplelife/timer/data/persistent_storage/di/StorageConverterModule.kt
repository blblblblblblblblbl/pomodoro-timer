package blblblbl.simplelife.timer.data.persistent_storage.di

import blblblbl.simplelife.timer.data.di.TimerFeature
import blblblbl.simplelife.timer.data.persistent_storage.utils.GsonParser
import blblblbl.simplelife.timer.data.persistent_storage.utils.JsonParser
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
class StorageConverterModule {

    @Provides
    @TimerFeature
    fun provideGson(): Gson {
        val gson = GsonBuilder().setLenient().create()
        return gson
    }

    @Provides
    @TimerFeature
    fun provideJsonParser(@TimerFeature gson:Gson): JsonParser = GsonParser(gson)

}