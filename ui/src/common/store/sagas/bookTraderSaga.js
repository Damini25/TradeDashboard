import { all, call, put, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../actions/actionTypes';
import { clearLocalStorageKey } from '../../localStorageService'
import { getInitialOrderList, generateOrders, getGamePlayPauseStatus } from '../../../orderEntry/services/orderEntry.service';
import { push } from 'connected-react-router';

export function* fetchBidAskOrders(action) {
    const { payload } = action;
    const { response, error } = yield call(getInitialOrderList, payload);
    if (response['data'].success) {
        yield put({ type: ActionTypes.OnRecieve_BidAsk_Data, data: response.data['data'] });
    } else if (response['data'].error) {
        console.log('responseerror',response)
        if (response['data'].error['key'] === 'gameSessionEnded') {
            clearLocalStorageKey('gameSessionId');
            clearLocalStorageKey('gameId');
            yield put(push('/mainNav/joinGame'))
        }
    } else {
        console.log('bid/ask', error);
    }
}

export function* loadBidAskData() {
    yield takeLatest(ActionTypes.Load_BidAsk_List, fetchBidAskOrders);
}

export function* callGenerateOrders() {
    yield call(generateOrders);
}

export function* onGenerateOrders() {
    yield takeLatest(ActionTypes.Generate_Orders, callGenerateOrders);
}

export function* callCheckGameStatusApi(action) {
    const response = yield call(getGamePlayPauseStatus, action['payload']['gameId']);
    if (response['data']['data']['playbackFlag']) {
        yield put({ type: ActionTypes.Set_Game_PlayPause_Status, playbackFlag: response['data']['data']['playbackFlag'] });
    }
}

export function* checkGamePlayPauseStatus() {
    yield takeLatest(ActionTypes.Check_Game_PlayPause, callCheckGameStatusApi);
}

export default function* bookTraderSaga() {
    yield all([loadBidAskData(), onGenerateOrders(), checkGamePlayPauseStatus()]);
}