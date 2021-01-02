import * as ActionTypes from '../actions/actionTypes';

const initialState = {
    listTraderGames: [],
    gameSessionId:''
}

const TraderGameManagementReducer = (state = initialState, action) => {
    switch (action.type) {
        case ActionTypes.Fetch_All_TraderGames:
           // console.log('Fetch_All_TraderGames', [...action.data])
            return {
                ...state,
                listTraderGames: [...action.data]
            }
            case ActionTypes.Set_GameSession_Id:
             //   console.log('Call_Join_Game', action.data)
                return {
                    ...state,
                    gameSessionId: action.data['gameSessionId']
                }
        default:
            return state;
    }
}

export default TraderGameManagementReducer;