import { all} from 'redux-saga/effects';
import loginSaga from './loginSaga';
import bookedExecutedOrderListSaga from './fetchBookedExecutedOrderSaga';
import commonFetchSaga from './commonSaga';
import bookTraderSaga from './bookTraderSaga';
import gameManagementSaga from './gameManagementSaga';


export default function* rootSaga() {
  yield all([ loginSaga(),commonFetchSaga(),bookTraderSaga(),bookedExecutedOrderListSaga(),gameManagementSaga()]);
}