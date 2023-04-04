package blblblbl.simplelife.timer.data.di

import blblblbl.simplelife.timer.data.persistent_storage.ConfigurationPersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.ConfigurationPersistentStorageImpl
import blblblbl.simplelife.timer.data.persistent_storage.TimerPersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.TimerPersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: ConfigurationPersistentStorageImpl): ConfigurationPersistentStorage

    @Binds
    abstract fun bindTimerPersistentStorage(persistentStorageImpl: TimerPersistentStorageImpl): TimerPersistentStorage

}