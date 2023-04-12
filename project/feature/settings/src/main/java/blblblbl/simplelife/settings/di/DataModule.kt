package blblblbl.simplelife.settings.di

import blblblbl.simplelife.settings.data.repository.SettingsRepositoryImpl
import blblblbl.simplelife.settings.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindRepository(configRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}