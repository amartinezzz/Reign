package com.amartinez.reign.presentation.hits.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.amartinez.reign.domain.usecase.LoadHitsUseCase
import com.amartinez.reign.domain.usecase.local.LoadHitsLocalUseCase
import com.amartinez.reign.domain.usecase.local.SaveHitsLocalUseCase
import com.amartinez.reign.presentation.hits.viewmodel.HitsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class HitsModule(private val owner: Fragment) {

    @Singleton
    @Provides
    fun provideViewModel(loadHitsUseCase: LoadHitsUseCase, saveHitsLocalUseCase: SaveHitsLocalUseCase,
                         loadHitsLocalUseCase: LoadHitsLocalUseCase
    ): HitsViewModel {
        val viewModel = ViewModelProvider(owner).get(HitsViewModel::class.java)
        viewModel.setUseCase(loadHitsUseCase, saveHitsLocalUseCase, loadHitsLocalUseCase)

        return viewModel
    }
}