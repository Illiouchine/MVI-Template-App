package com.illiouchine.mviapplication.ui.first

import com.illiouchine.mviapplication.core.MviViewModel
import com.illiouchine.mviapplication.core.Reducer
import com.illiouchine.mviapplication.data.DataRepository
import kotlinx.coroutines.flow.collect

class FirstViewModel(
    private val repository: DataRepository = DataRepository()
) : MviViewModel<
        FirstContract.MainIntent,
        FirstContract.MainState,
        FirstContract.MainEvent,
        FirstContract.MainPartialState,
        FirstContract.MainAction
        >() {

    init {
        setAction { FirstContract.MainAction.LoadData }
    }

    override fun createInitialState(): FirstContract.MainState =
        FirstContract.MainState(
            mainListState = FirstContract.MainListState.Loading
        )

    override fun createReducer(): Reducer<FirstContract.MainState, FirstContract.MainPartialState> =
        object : Reducer<FirstContract.MainState, FirstContract.MainPartialState>() {
            override fun reduce(
                currentState: FirstContract.MainState,
                partialState: FirstContract.MainPartialState
            ): FirstContract.MainState {
                return when (partialState) {
                    is FirstContract.MainPartialState.DataError -> {
                        currentState.copy(
                            mainListState = FirstContract.MainListState.Error(
                                errorCode = partialState.errorCode
                            )
                        )
                    }
                    is FirstContract.MainPartialState.DataLoaded -> {
                        currentState.copy(mainListState = FirstContract.MainListState.Success(data = partialState.data))
                    }
                    FirstContract.MainPartialState.DataLoading -> {
                        currentState.copy(mainListState = FirstContract.MainListState.Loading)
                    }
                }
            }
        }

    override fun handleUserIntent(intent: FirstContract.MainIntent): FirstContract.MainAction =
        when (intent) {
            is FirstContract.MainIntent.DataClick -> {
                FirstContract.MainAction.GoToDetail(intent.data)
            }
            FirstContract.MainIntent.ReloadDataClick -> {
                setEvent { FirstContract.MainEvent.ShowToast("Reload Click") }
                FirstContract.MainAction.LoadData
            }
        }

    override suspend fun handleAction(action: FirstContract.MainAction) =
        when (action) {
            is FirstContract.MainAction.GoToDetail -> {
                setEvent { FirstContract.MainEvent.GoToDetail(action.dataClicked) }
            }
            FirstContract.MainAction.LoadData -> {
                setPartialState { FirstContract.MainPartialState.DataLoading }
                try {
                    repository.loadData()
                        .collect {
                            setPartialState { FirstContract.MainPartialState.DataLoaded(it) }
                        }
                } catch (e: Exception) {
                    setPartialState { FirstContract.MainPartialState.DataError("Load Data Error") }
                }
            }
        }
}