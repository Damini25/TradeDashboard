import * as ActionTypes from '../actions/actionTypes';
import _ from 'lodash';

const initialState = {
    stockSymbols: [],
    userDetails: {
        name: 'john',
        password: '',
        traderId: ''
    },
    loginFormError: {
        emailInvalid: false,
        passwordInvalid: false
    },
    bookedOrders: [],
    executedOrders: [],
    newsFeed: [],
    portfolio: {
        portFolioList: [],
        availableBalance: '',
        startingBalance: '',
        startingVolume: '',
        availableVolume: '',
        pLData: {
            pLList: [],
            minY: null,
            maxY: null
        }
    }
}

const FetchDataReducer = (state = initialState, action) => {
    //  console.log('ftech reducer state action',state,action)
    switch (action.type) {
        case ActionTypes.Set_User_Details:
            // const updatedOrderFormValue = { ...state.bookOrderFormValue, ...action.element };
            return {
                ...state,
                userDetails: {
                    ...state.userDetails,
                    ...action.element
                }
            }

        case ActionTypes.Fetch_Stock_Symbols:
            return {
                ...state,
                stockSymbols: action.data
            }
        case ActionTypes.Set_LoginForm_ValidityState:
            const loginFormValidity = { ...state.loginFormError }
            if (action.fieldName === 'email') {
                loginFormValidity.emailInvalid = action.value;
            } else if (action.fieldName === 'password') {
                loginFormValidity.passwordInvalid = action.value;
            }
            return {
                ...state,
                loginFormError: { ...state.loginFormError, ...loginFormValidity }
            }
        case ActionTypes.Fetch_Booked_Orders:
            // console.log('fetchredbooked', action)
            return {
                ...state,
                bookedOrders: action.data[0]['allOrders'],
                executedOrders: action.data[0]['allTrades']
            }
        case ActionTypes.Fetch_Executed_Orders:
            // console.log('fetchredexecutd',action)
            return {
                ...state,
                executedOrders: action.data
            }
        case ActionTypes.Fetch_News_List:
            // console.log('fetchredexecutd',action)
            return {
                ...state,
                newsFeed: action.data
            }
        /* case ActionTypes.On_Get_Order_Fetch_Interval:
             return {
                 ...state,
                 orderFetchInterval: action.data
             }*/
        case ActionTypes.Recieve_Portfolio_List:
            let pLDataList = [...state.portfolio.pLData.pLList];
            const time = new Date().getHours() + ':' + new Date().getMinutes() + ':' + new Date().getSeconds();
            if (action.data['pnlValue']) {
                pLDataList.push({ plValue: action.data['pnlValue'], time: time });
              //  console.log('plData', pLDataList, action.data['pnlValue'], time);
            }

            const maxValue = _.maxBy(pLDataList, (o) => {
                return o.plValue;
            });
            const minValue = _.minBy(pLDataList, (o) => {
                return o.plValue;
            });

            return {
                ...state, portfolio: {
                    ...state.portfolio,
                    portFolioList: [...action.data['portfolioDtos']],
                    availableBalance: action.data['availableBalance'],
                    startingBalance: action.data['startingBalance'],
                    startingVolume: action.data['startingVolume'],
                    availableVolume: action.data['availableVolume'],
                    pLData: {
                        ...state.portfolio.pLData,
                        pLList: [...pLDataList],
                        minY: minValue,
                        maxY: maxValue
                    }
                }
            }
        default:
            return state;
    }
}

export default FetchDataReducer;