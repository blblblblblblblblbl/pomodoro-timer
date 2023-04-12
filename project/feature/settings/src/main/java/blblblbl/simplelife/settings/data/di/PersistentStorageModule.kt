package blblblbl.simplelife.settings.data.di

import blblblbl.simplelife.settings.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.settings.data.persistent_storage.utils.StorageConverterImpl
import blblblbl.simplelife.settings.di.SettingsFeature
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    @SettingsFeature
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter


}