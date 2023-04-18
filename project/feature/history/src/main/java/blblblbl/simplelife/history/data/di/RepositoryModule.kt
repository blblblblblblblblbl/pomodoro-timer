package blblblbl.simplelife.history.data.di

import blblblbl.simplelife.history.data.datasource.HistoryDataSource
import blblblbl.simplelife.history.data.datasource.HistoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindHistoryDataSource(searchDataSource: HistoryDataSourceImpl): HistoryDataSource
}