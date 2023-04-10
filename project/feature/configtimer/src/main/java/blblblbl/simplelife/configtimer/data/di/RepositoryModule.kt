package blblblbl.simplelife.configtimer.data.di

import blblblbl.simplelife.configtimer.data.persistent_storage.PersistentStorage
import blblblbl.simplelife.configtimer.data.persistent_storage.PersistentStorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
annotation class ConfigFeature
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    abstract fun bindPersistentStorage(persistentStorageImpl: PersistentStorageImpl): PersistentStorage

}