import { all, call, put, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../actions/actionTypes';
import {
    createNewGame, getGameList, uploadHistoricalDataFile, callJoinGame, callStartGame, callStopGame,
    callDeleteGame, getGameBasedDateList, uploadNewsDataFile
} from '../../../manageGame/manageGameService';
import { setLocalStorage, getLocalStorage } from '../../../common/localStorageService';
import { push } from 'react-router-redux';

export function* callCreateGameAPI(action) {
    const { payload } = action;
    try {
        // console.log('create new game api response', 1);
        yield put({ type: ActionTypes.Request_Posts });
        const response = yield call(createNewGame, payload);
        yield put({ type: ActionTypes.Game_Created_Success });
        yield put({
            type: ActionTypes.Load_ALL_Games, payload: {
                'userId': parseInt(getLocalStorage('traderId'))
            }
        });
        // if (response['data']['data']['gameId']) {
        //     yield call(uploadHistoricalDataFile, payload, response['data']['data']['gameId']);
        // }
        //   console.log('create new game api response', response);

        yield put({ type: ActionTypes.Recieve_Posts });
    }
    catch (e) {
        yield put({ type: ActionTypes.RecieveError_Posts });
    }
}

export function* postGameFormValues() {
    yield takeLatest(ActionTypes.Post_CreateGameForm_Values, callCreateGameAPI);
}

export function* fetchGameList(action) {
    const { payload } = action
    const response = yield call(getGameList, payload);
    yield put({ type: ActionTypes.Fetch_All_Games, data: response.data['data'] });
}
export function* loadGameList() {
    yield takeLatest(ActionTypes.Load_ALL_Games, fetchGameList);
}


export function* fetchTraderGameList(action) {
    const { payload } = action
    const response = yield call(getGameList, payload);
    yield put({ type: ActionTypes.Fetch_All_TraderGames, data: response.data['data'] });
}
export function* loadTraderGameList() {
    yield takeLatest(ActionTypes.Load_Trader_Games, fetchTraderGameList);
}

export function* callJoinGameAPI(action) {
    const { payload } = action;
    const { response, error } = yield call(callJoinGame, payload);
    //  console.log('gamejoin',response.data['data']['gameSessionId'])
    if (response['data'].success) {
        setLocalStorage({
            name: 'gameSessionId',
            value: response.data['data']['gameSessionId']
        });
        setLocalStorage({
            name: 'gameId',
            value: response.data['data']['gameId']
        });
        setLocalStorage({
            name: 'orderFetchInterval',
            value: response.data['data']['playbackFrequency']
        });

        yield put(push('/mainNav/orderEntry'));
        yield put({ type: ActionTypes.Set_GameSession_Id, data: response.data['data'] });
    } else {

    }

}
export function* joinGame() {
    yield takeLatest(ActionTypes.Join_Game, callJoinGameAPI);
}


export function* callStartGameAPI(action) {
    const { payload } = action;
    const response = yield call(callStartGame, payload);
    yield put({
        type: ActionTypes.Load_ALL_Games, payload: {
            'userId': parseInt(getLocalStorage('traderId'))
        }
    });
    //const games = yield call(getGameList);
    //yield put({ type: ActionTypes.Fetch_All_Games, data: games.data['data'] });
    yield put({ type: ActionTypes.Game_Started_Success, data: response.data['data'] });
    // yield put({ type: ActionTypes.Generate_Orders});
}

export function* startGameAdmin() {
    yield takeLatest(ActionTypes.Game_Started_ByAdmin, callStartGameAPI);
}

export function* callStopGameAPI(action) {
    const { payload } = action;
    const response = yield call(callStopGame, payload);
    yield put({
        type: ActionTypes.Load_ALL_Games, payload: {
            'userId': parseInt(getLocalStorage('traderId'))
        }
    });
    // const games = yield call(getGameList);
    // yield put({ type: ActionTypes.Fetch_All_Games, data: games.data['data'] });
}

export function* stopGameAdmin() {
    yield takeLatest(ActionTypes.Game_Stopped_ByAdmin, callStopGameAPI);
}

export function* callDeleteGameAPI(action) {
    const { payload } = action;
    const response = yield call(callDeleteGame, payload);
    yield put({
        type: ActionTypes.Load_ALL_Games, payload: {
            'userId': parseInt(getLocalStorage('traderId'))
        }
    });
    //  const games = yield call(getGameList);
    //  yield put({ type: ActionTypes.Fetch_All_Games, data: games.data['data'] });
}

export function* gameDeleteByAdmin() {
    yield takeLatest(ActionTypes.Game_Deleted_ByAdmin, callDeleteGameAPI);
}


export function* fetchBasedDateList() {
    const response = yield call(getGameBasedDateList);
    yield put({ type: ActionTypes.OnFetch_GameBased_Dates, data: response.data['data'] });
}

export function* getGameBasedData() {
    yield takeLatest(ActionTypes.Get_GameBased_Dates, fetchBasedDateList);
}

export function* callUploadFile(action) {
    const type = action['data']['type'];
    const payload = action['data']['payload'];
    if (type === 'gameData') {
        yield put({ type: ActionTypes.Request_Posts });
        const {response,error} = yield call(uploadHistoricalDataFile, payload);
       
        if(response['data'].success){
            yield put({ type: ActionTypes.Show_SnackBar, msg: 'File uploaded successfully' })
            yield put({ type: ActionTypes.Recieve_Posts });
        }else{
            yield put({ type: ActionTypes.RecieveError_Posts});
        }

    } else {
        yield put({ type: ActionTypes.Request_Posts });
        const {response,error} = yield call(uploadNewsDataFile, payload);
        yield put({ type: ActionTypes.Recieve_Posts });
        if(response['data'].success){
            yield put({ type: ActionTypes.Show_SnackBar, msg: 'File uploaded successfully' })
            yield put({ type: ActionTypes.Recieve_Posts });
        }else{
            yield put({ type: ActionTypes.RecieveError_Posts});
        }
    }
}

export function* uploadGameBasedData() {
    yield takeLatest(ActionTypes.Upload_Game_data, callUploadFile);
}

export default function* GameManagementSaga() {
    yield all([postGameFormValues(), loadGameList(), loadTraderGameList(),
    joinGame(), startGameAdmin(), stopGameAdmin(), gameDeleteByAdmin(), getGameBasedData(), uploadGameBasedData()
    ]);
}