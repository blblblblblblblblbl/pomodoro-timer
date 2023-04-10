package blblblbl.simplelife.configtimer.di

import blblblbl.simplelife.configtimer.data.repository.ConfigurationRepositoryImpl
import blblblbl.simplelife.configtimer.domain.repository.ConfigurationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(configRepositoryImpl: ConfigurationRepositoryImpl): ConfigurationRepository
}