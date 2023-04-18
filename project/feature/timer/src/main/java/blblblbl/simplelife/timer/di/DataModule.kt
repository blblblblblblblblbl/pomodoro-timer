package blblblbl.simplelife.timer.di

import blblblbl.simplelife.timer.data.di.TimerFeature
import blblblbl.simplelife.timer.data.persistent_storage.ConfigurationPersistentStorage
import blblblbl.simplelife.timer.data.persistent_storage.ConfigurationPersistentStorageImpl
import blblblbl.simplelife.timer.data.repository.HistoryRepositoryImpl
import blblblbl.simplelife.timer.data.repository.TimerRepositoryImpl
import blblblbl.simplelife.timer.domain.repository.HistoryRepository
import blblblbl.simplelife.timer.domain.repository.TimerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(configRepositoryImpl: TimerRepositoryImpl): TimerRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryRepositoryModule{
    @Binds
    @TimerFeature
    abstract fun bindHistoryRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository



}