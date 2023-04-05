package blblblbl.simplelife.timer.di

import blblblbl.simplelife.timer.ui.TimerNotifications
import blblblbl.simplelife.timer.ui.TimerNotificationsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkerModule {
    @Binds
    abstract fun bindNotify(timerNotificationsImpl: TimerNotificationsImpl):TimerNotifications
}