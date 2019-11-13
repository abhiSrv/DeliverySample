package com.abhi.deliverylist.di.module


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhi.deliverylist.di.AppViewModelFactory
import com.abhi.deliverylist.viewModel.DeliveryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DeliveryViewModel::class)
    internal abstract fun deliveryListViewModel(viewModel: DeliveryViewModel): ViewModel
}
