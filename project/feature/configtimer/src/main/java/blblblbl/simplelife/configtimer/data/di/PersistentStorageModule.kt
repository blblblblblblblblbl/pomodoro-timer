package blblblbl.simplelife.configtimer.data.di


import blblblbl.simplelife.configtimer.data.persistent_storage.utils.StorageConverter
import blblblbl.simplelife.configtimer.data.persistent_storage.utils.StorageConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PersistentStorageModule{
    @Binds
    @ConfigFeature
    abstract fun bindStorageConverter(storageConverterImpl: StorageConverterImpl): StorageConverter


}
