import * as ActionTypes from '../actions/actionTypes';
import _ from 'lodash';

const initialState = {
    isFetching: false
}

const ShowLoaderReducer = (state = initialState, action) => {
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
        case ActionTypes.RecieveError_Posts:{
            return {
                ...state, isFetching: false
            }
        }
        default:
            return state;
    }
}

export default ShowLoaderReducer;