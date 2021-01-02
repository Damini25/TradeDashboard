import * as ActionTypes from '../actions/actionTypes';
import _ from 'lodash';

const initialState = {
    isFetching: false,
    snackBarInfo: {
        open: false,
        msg: '',
        duration: 3000,
        direction: {
            vertical: 'top',
            horizontal: 'center',
        },
        msgQueue: []
    }
}

const RequestStatusReducer = (state = initialState, action) => {
    switch (action.type) {
        case ActionTypes.Request_Posts: {
            return {
                ...state, isFetching: true
            }
        }
        case ActionTypes.Recieve_Posts: {
            return {
                ...state, isFetching: false
            }
        }
        case ActionTypes.RecieveError_Posts: {
            return {
                ...state, isFetching: false
            }
        }
        case ActionTypes.Show_SnackBar: {
            const info = { ...state.snackBarInfo }
            info['open'] = true;
            info['msg'] = action['msg'];
            info['duration'] = action['duration'] ? action['duration'] : state['snackBarInfo']['duration'];
            info['direction'] = action['direction'] ? { ...action['direction'] } :{ ...state['snackBarInfo']['direction'] } ;
            return {
                ...state, snackBarInfo: { ...info }
            }
        }
        case ActionTypes.Close_SnackBar: {
            const info = { ...state.snackBarInfo }
            info['open'] = false;
            return {
                ...state, snackBarInfo: { ...info }
            }
        }
        default:
            return state;
    }
}

export default RequestStatusReducer;