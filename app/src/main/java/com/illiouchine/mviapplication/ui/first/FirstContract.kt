package com.illiouchine.mviapplication.ui.first

import com.illiouchine.mviapplication.core.*

interface FirstContract {

    sealed class MainIntent : UiIntent{
        object ReloadDataClick: MainIntent()
        data class DataClick(val data: String): MainIntent()
    }

    sealed class MainAction: UiAction{
        object LoadData: MainAction()
        data class GoToDetail(val dataClicked: String): MainAction()
    }

    data class MainState(
        val mainListState: MainListState
    ): UiState

    sealed class MainListState {
        object Loading : MainListState()
        data class Success(val data: List<String>) : MainListState()
        data class Error(val errorCode: String) : MainListState()
    }

    sealed class MainPartialState : UiPartialState{
        object Nothing : MainPartialState()
        object DataLoading: MainPartialState()
        data class DataLoaded(val data :List<String>): MainPartialState()
        data class DataError(val errorCode: String):  MainPartialState()
    }

    sealed class MainEvent: UiEvent{
        data class ShowToast(val text: String): MainEvent()
        data class GoToDetail(val dataClicked: String):MainEvent()
    }
}