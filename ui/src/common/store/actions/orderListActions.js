import * as Actiontypes from './actionTypes';

export const LoadBidAskList = (payload) => {
    return {
        type: Actiontypes.Load_BidAsk_List,
        payload: payload
    }
}


export const UpdateRecentOrders = (type, item) => {
    return {
        type: Actiontypes.Update_Order_Front,
        element: item,
        orderType: type
    }
}

export const AddNewOrders = (type, item) => {
    return {
        type: Actiontypes.Add_New_Orders,
        element: item,
        orderType: type
    }
}

export const ClearBidAskOrders = () => {
    return {
        type: Actiontypes.Clear_BidAsk_List_Orders,
    }
}

export const CheckGamePlayPaused = (payload) => {
    return {
        type: Actiontypes.Check_Game_PlayPause,
        payload:payload
    }
}