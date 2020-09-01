package com.amartinez.reign.presentation.hits.di

import com.amartinez.reign.di.ProviderModule
import com.amartinez.reign.presentation.hits.ui.HitsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ProviderModule::class, HitsModule::class])
interface HitsComponent {
    fun inject(hitsFragment: HitsFragment)
}