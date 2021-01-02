import * as Actiontypes from './actionTypes';

export const SetUserDetails = (data) => {
    return {
        type: Actiontypes.Set_User_Details,
        element: data
    }
}

export const SetLoginFormValidity = (data) => {
    return {
        type: Actiontypes.Set_LoginForm_ValidityState,
        fieldName: data['fieldName'],
        value: data['formError']
    }
}

export const LoadBookedOrders = (payload) => {
    return {
        type: Actiontypes.Load_Booked_Orders,
        payload: payload
    }
}

export const LoadExecutedOrders = (payload) => {
    return {
        type: Actiontypes.Load_Executed_Orders,
        payload: payload
    }
}

export const LoadNewsList = () => {
    return {
        type: Actiontypes.Load_News_List
    }
}

export const CallLoginApi = (payload) => {
    return {
        type: Actiontypes.Call_Login_Api,
        payload: payload
    }
}

/*export const GetOrderFetchInterval=()=>{
    return {
        type:Actiontypes.Get_Order_Fetch_Interval,
    }
}*/

export const LoadPortfolioList = (payload) => {
    return {
        type: Actiontypes.Load_Portfolio_List,
        payload: payload
    }
}

export const ShowSnackbar = (data) => {
   // console.log('show',data)
    return {
        type: Actiontypes.Show_SnackBar,
        msg: data['msg'],
        duration: data['duration'] ? data['duration'] : 4000,
        direction:data['direction']? data['direction']:{
            vertical: 'top',
            horizontal: 'middle',
        }
    }
}

export const CloseSnackbar = () => {
    return {
        type: Actiontypes.Close_SnackBar,
    }
}
