package blblblbl.simplelife.history.di

import blblblbl.simplelife.history.data.repository.HistoryRepositoryImpl
import blblblbl.simplelife.history.domain.repository.HistoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(historyRepositoryImpl: HistoryRepositoryImpl): HistoryRepository
}