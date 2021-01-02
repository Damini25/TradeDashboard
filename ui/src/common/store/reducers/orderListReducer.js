import * as ActionTypes from '../actions/actionTypes';
import _ from 'lodash';

const initialState = {
    ordersToShow: {
        askOrders: [],
        bidOrders: [],
        minAskOrders: [],
        maxBidOrders: [],
        latestNewsFeed:[],
        minPriceYAxis: null,
        maxPriceYAxis: null
    },
    playbackOrdersFlow: true,
    totalOrdersToBeShown: 20
}

const OrderListReducer = (state = initialState, action) => {
    //  console.log('OrderListReducer', state, action);
    switch (action.type) {

        case ActionTypes.OnRecieve_BidAsk_Data: {

            console.log('bid/as', action)
            const newOrderToShow = { ...state.ordersToShow };
            
            newOrderToShow.askOrders = [...action['data']['allAskOrders']];
            newOrderToShow.bidOrders = [...action['data']['allBidOrders']];
            
            if(action['data']['latestNews'].length){
                newOrderToShow.latestNewsFeed=[...action['data']['latestNews']];
            }

            const time = new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds();
            let minAskPrice; let maxBidPrice;
            if (action['data']['allAskOrders'].length) {
                minAskPrice = _.minBy(action['data']['allAskOrders'], (o) => {
                    return o.price;
                });
                newOrderToShow.minAskOrders = [...state.ordersToShow.minAskOrders,
                { minAsk: minAskPrice, time: time }];
            }
            if (action['data']['allBidOrders'].length) {
                maxBidPrice = _.maxBy(action['data']['allBidOrders'], (o) => {
                    return o.price;
                });
                newOrderToShow.maxBidOrders = [...state.ordersToShow.maxBidOrders,
                { maxBid: maxBidPrice, time: time }];
            }

            let minPrice = maxBidPrice && maxBidPrice['price']? maxBidPrice['price']:'';
            let maxPrice = minAskPrice && minAskPrice['price']? minAskPrice['price']:'';
            if (state.ordersToShow['minPriceYAxis']) {
                minPrice = minPrice < state.ordersToShow['minPriceYAxis'] ? minPrice : state.ordersToShow['minPriceYAxis'];
            }else{
                state.ordersToShow['minPriceYAxis']=minPrice;
            }

            maxPrice = maxPrice > state.ordersToShow['maxPriceYAxis'] ? maxPrice : state.ordersToShow['maxPriceYAxis'];
            newOrderToShow['minPriceYAxis'] = minPrice;
            newOrderToShow['maxPriceYAxis'] = maxPrice;

            return {
                ...state, ordersToShow: newOrderToShow, playbackOrdersFlow: action['data']['playbackFlag']
            }
        }

        case ActionTypes.Clear_BidAsk_List_Orders: {
            const newOrderToShow = { ...state.ordersToShow };
            newOrderToShow['askOrders'] = [];
            newOrderToShow['bidOrders'] = [];
            newOrderToShow['minAskOrders'] = [];
            newOrderToShow['maxBidOrders'] = [];
            return {
                ...state, ordersToShow: newOrderToShow
            }
        }

        case ActionTypes.Set_Game_PlayPause_Status: {
            return {
                ...state, playbackOrdersFlow: action.playbackFlag
            }
        }
        default:
            return state;
    }
}

export default OrderListReducer;