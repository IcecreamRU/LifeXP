package ru.icecreamru.lifexp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.icecreamru.lifexp.domain.usecase.AddActionUseCase
import javax.inject.Inject

@HiltViewModel
class AddActionViewModel @Inject constructor(
    val addActionUseCase: AddActionUseCase
): ViewModel() {

}