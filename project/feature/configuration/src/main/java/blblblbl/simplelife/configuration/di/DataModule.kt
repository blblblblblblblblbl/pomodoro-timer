package blblblbl.simplelife.configuration.di

import blblblbl.simplelife.configuration.data.repository.ConfigurationRepositoryImpl
import blblblbl.simplelife.configuration.domain.repository.ConfigurationRepository
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