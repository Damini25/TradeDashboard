import * as Actiontypes from '../actionTypes';

export const LoadGameData = (payload) => {
    return {
        type: Actiontypes.Load_ALL_Games,
        payload: payload
    }
}


export const UpdateCreateGameFormValues = (item) => {
    return {
        type: Actiontypes.Update_CreateGameForm_Values,
        element: item
    }
}

export const PostCreateGameData = (payload) => {
    return {
        type: Actiontypes.Post_CreateGameForm_Values,
        payload: payload
    }
}

export const ResetGameFormValues = () => {
    return {
        //  type: Actiontypes.Clear_BookOrderForm_Values
    }
}

export const GameStartedByAdmin = (payload) => {
    return {
        type: Actiontypes.Game_Started_ByAdmin,
        payload: payload
    }
}
export const GameStoppedByAdmin = (payload) => {
    return {
        type: Actiontypes.Game_Stopped_ByAdmin,
        payload: payload
    }
}

export const GameDeletedByAdmin = (payload) => {
    return {
        type: Actiontypes.Game_Deleted_ByAdmin,
        payload: payload
    }
}

/*export const SetFetchOrderInterval = (data) => {
    return {
        type: Actiontypes.Set_Fetch_Order_Interval,
        data:data
    }
}*/

export const FetchGameBasedDates = () => {
    return {
        type: Actiontypes.Get_GameBased_Dates
    }
}

export const UploadGameBasedDataFile=(data)=>{
    return{
        type:Actiontypes.Upload_Game_data,
        data:{
            type:data['type'],
            payload:data['payload']
        }
    }
}