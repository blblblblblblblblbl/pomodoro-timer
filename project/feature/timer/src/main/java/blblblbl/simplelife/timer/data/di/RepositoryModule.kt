package blblblbl.simplelife.timer.data.di

import blblblbl.simplelife.timer.data.persistent_storage.PersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.PersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: PersistentStorageImpl): PersistentStorage

}