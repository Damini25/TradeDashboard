import { all, call, put, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../actions/actionTypes';
import { getBookedOrderList, getExecutedOrderList} from '../../../orderEntry/services/orderEntry.service';

export function* fetchBookedOrders(action) {
  const response = yield call(getBookedOrderList,action.payload);
  yield put({ type: ActionTypes.Fetch_Booked_Orders, data: response.data['data'] });
}
export function* loadBookedOrders() {
  yield takeLatest(ActionTypes.Load_Booked_Orders, fetchBookedOrders);
}

export function* fetchExecutedOrders(action) {
  const response = yield call(getExecutedOrderList,action.payload);
  yield put({ type: ActionTypes.Fetch_Executed_Orders, data: response.data['data'] });
}
export function* loadExecutedOrders() {
  yield takeLatest(ActionTypes.Load_Executed_Orders, fetchExecutedOrders);
}

export default function* bookedExecutedOrderList() {
  yield all([loadBookedOrders(),loadExecutedOrders()]);
}