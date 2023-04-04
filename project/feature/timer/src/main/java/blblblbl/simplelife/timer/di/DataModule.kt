package blblblbl.simplelife.timer.di

import blblblbl.simplelife.timer.data.repository.TimerRepositoryImpl
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