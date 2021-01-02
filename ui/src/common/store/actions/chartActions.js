import * as Actiontypes from './actionTypes';

export const AddTototalOrders = (type,item) => {
    return {
        type: Actiontypes.Total_Orders_TillNow,
        element: item,
        orderType:type
    }
}

export const ClearTotalOrders = () => {
    return {
        type: Actiontypes.Clear_Prev_TotalOrders,
    }
}

export const AddToTotalAskOrders = (item) => {
    return {
        type: Actiontypes.Add_To_Ask_TOrders,
        element: item
    }
}

export const AddToTotalBidOrders = (item) => {
    return {
        type: Actiontypes.Add_To_Bid_TOrders,
        element: item
    }
}

export const AddMinMaxTotalAskOrders = (item) => {
    return {
        type: Actiontypes.Add_MinMax_Ask_TOrders,
        element: item
    }
}

export const AddMinMaxTotalBidOrders = (item) => {
    return {
        type: Actiontypes.Add_MinMax_Bid_TOrders,
        element: item
    }
}

export const ClearTotalMinMaxOrders = (item) => {
    return {
        type: Actiontypes.Clear_MinMax_AskBid_TOrders,
        element: item
    }
}