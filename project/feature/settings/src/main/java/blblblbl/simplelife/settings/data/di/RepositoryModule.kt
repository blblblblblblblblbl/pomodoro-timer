package blblblbl.simplelife.settings.data.di

import blblblbl.simplelife.settings.data.persistent_storage.SettingsPersistentStorage
import blblblbl.simplelife.settings.data.persistent_storage.SettingsPersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: SettingsPersistentStorageImpl): SettingsPersistentStorage

}