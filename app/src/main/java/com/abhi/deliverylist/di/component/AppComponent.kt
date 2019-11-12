package com.abhi.deliverylist.di.component


import android.app.Application
import com.abhi.deliverylist.AppController
import com.abhi.deliverylist.di.builder.ActivityBuilder
import com.abhi.deliverylist.di.module.*

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AppModule::class, ViewModelModule::class,
        ActivityBuilder::class, DatabaseModule::class,
        RepoModule::class,
        NetworkModule::class]
)
interface AppComponent {

    fun inject(app:AppController )

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
