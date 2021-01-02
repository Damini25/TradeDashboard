import { all, call, put, takeEvery, takeLatest } from 'redux-saga/effects';
import * as ActionTypes from '../actions/actionTypes';
import { getProducts, getNewsList, getPortfolioList } from '../../../orderEntry/services/orderEntry.service';

export function* fetchStockSymbol() {
  const response = yield call(getProducts);
  //  console.log('products',response)
  yield put({ type: ActionTypes.Fetch_Stock_Symbols, data: response.data });
}
export function* loadStockSymbol() {
  yield takeEvery(ActionTypes.Load_Stock_Symbols, fetchStockSymbol);
}

export function* fetchNewsList() {
  const { response, error } = yield call(getNewsList);
  if (response.data['success']) {
    yield put({ type: ActionTypes.Fetch_News_List, data: response.data['data'] });
  }else{
    console.log(error);
  }
}
export function* loadNewsList() {
  yield takeLatest(ActionTypes.Load_News_List, fetchNewsList);
}

export function* fetchPortfolioList(action) {
  const { payload } = action;
  const { response, error } = yield call(getPortfolioList);
  if (response.data['success']) {
    yield put({ type: ActionTypes.Recieve_Portfolio_List, data: response.data['data'] });
  } else {
    console.log('portfolio error', response.data['error']);
  }
}

export function* loadPortfolioList() {
  yield takeLatest(ActionTypes.Load_Portfolio_List, fetchPortfolioList);
}


export default function* commonFetchSaga() {
  yield all([loadStockSymbol(), loadNewsList(), loadPortfolioList()]);
}