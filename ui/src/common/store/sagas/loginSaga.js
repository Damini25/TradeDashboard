import { all, call, put, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../actions/actionTypes';
import { login } from '../../../login/loginService';
import { setLocalStorage } from '../../../common/localStorageService';
import { push } from 'connected-react-router';



export function* loginUser(action) {
  yield put({ type: ActionTypes.Request_Posts });
  const { response, error } = yield call(login, action.payload);
  yield put({ type: ActionTypes.Recieve_Posts });
  if (response) {
    setLocalStorage({
      name: 'traderId',
      value: response.data['data'][0]['userId']
    });
    setLocalStorage({
      name: 'userTypeId',
      value: response.data['data'][0]['userTypeId']
    });
    yield put({ type: ActionTypes.Set_User_Details, element: { 'traderId': response.data['data'][0]['userId'] } });
    yield put({ type: ActionTypes.Show_SnackBar, msg: 'Login successfull' })
    if (parseInt(response.data['data'][0]['userTypeId']) === 0) {
      yield put(push('/mainNav/manageGame'))
    } else {
      yield put(push('/mainNav/joinGame'))
    }
  } else {
    yield put({ type: ActionTypes.Show_SnackBar, msg: 'Some Error Occurred. Please try again' })
    yield put({ type: ActionTypes.RecieveError_Posts});
    console.log('loginerror',error);
  }
}

export function* callLoginApi() {
  yield takeLatest(ActionTypes.Call_Login_Api, loginUser);
}

export default function* loginSaga() {
  yield all([callLoginApi()]);
}