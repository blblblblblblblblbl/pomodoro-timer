package blblblbl.simplelife.timer.data.di


import android.content.Context
import android.content.SharedPreferences
import blblblbl.simplelife.timer.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.timer.data.persistent_storage.utils.StorageConverterImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    @TimerFeature
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter


}
