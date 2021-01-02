import { all, call, put, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../../actions/actionTypes';
import { createNewGame, getGameList, uploadHistoricalDataFile } from '../../../../manageGame/manageGameService';

export function* callCreateGameAPI(action) {
    const { payload } = action;
    try {
        console.log('create new game api response', 1);
        yield put({ type: ActionTypes.Request_Posts });
        yield call(uploadHistoricalDataFile, payload);
        const response = yield call(createNewGame, payload);
        console.log('create new game api response', response);
        yield put({ type: ActionTypes.Game_Created_Success });
        yield put({ type: ActionTypes.Recieve_Posts });
    }
    catch (e) {
        yield put({ type: ActionTypes.RecieveError_Posts });
    }
}

export function* postGameFormValues() {
    yield takeLatest(ActionTypes.Post_CreateGameForm_Values, callCreateGameAPI);
}

export function* fetchGameList() {
    const response = yield call(getGameList);
    yield put({ type: ActionTypes.Fetch_All_Games, data: response.data['data'] });
}
export function* loadGameList() {
    yield takeLatest(ActionTypes.Load_ALL_Games, fetchGameList);
}

export default function* GameManagementSaga() {
    yield all([postGameFormValues(), loadGameList()]);
}