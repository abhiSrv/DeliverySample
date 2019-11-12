package com.abhi.deliverylist.di.builder


import androidx.fragment.app.Fragment
import com.abhi.deliverylist.ui.DetailActivity
import com.abhi.deliverylist.ui.MainActivity
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.Multibinds

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeDetailActivity(): DetailActivity

    @Multibinds
    internal abstract fun buildNativeFragments(): Map<Class<out Fragment>, AndroidInjector.Factory<out Fragment>>

}


