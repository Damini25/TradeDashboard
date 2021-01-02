import * as Actiontypes from './actionTypes';

export const LoadTraderGameList = (param) => {
    return {
        type: Actiontypes.Load_Trader_Games,
        payload:param
    }
}

export const JoinGame = (payload) => {
    return {
        type: Actiontypes.Join_Game,
        payload:payload
    }
}