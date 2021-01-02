import * as ActionTypes from '../../actions/actionTypes';

const initialState = {
    gameCreatedSucess: false,
    createGameFormValue: {
        gameName: '',
        gameMode: 'general',
        startingCash: '',
        volume: '',
        transaction: '',
        gameInterval:60,
        playbackFrequency: '',
        playbackDate:'',
        playbackStartTime: '',
        playbackEndTime: '',
        file: ''
    },
    createGameBtnLabel:true,
  //  fetchOrderInterval: '',
    listGames: [],
    gameBasedDates:[],
    gameStarted: {
    },
    ordersFile:'',
    newsFile:''
}

const GameManagementReducer = (state = initialState, action) => {
    switch (action.type) {
        case ActionTypes.Update_CreateGameForm_Values:
            const updateCreateGameFormValue = { ...state.createGameFormValue, ...action.element };
            return {
                ...state,
                createGameFormValue: updateCreateGameFormValue,
                createGameBtnLabel:false
            }
        case ActionTypes.Game_Created_Success: {
            const clearData = { ...state['createGameFormValue'] }
            clearData['gameName'] = '';
            clearData['gameMode'] = 'general';
            clearData['startingCash'] = '';
            clearData['volume'] = '';
            clearData['transaction'] = '';
            clearData['playbackStartTime'] = '';
            clearData['playbackEndTime'] = '';
            clearData['playbackDate'] ='';
            clearData['playbackFrequency'] = '';
            clearData['file'] = '';

            return {
                ...state, createGameFormValue: clearData, gameCreatedSucess: true, createGameBtnLabel:true
            }
        }
        case ActionTypes.Fetch_All_Games:
            // console.log('Fetch_All_Games', [...action.data])
            return {
                ...state,
                listGames: [...action.data]
            }
        case ActionTypes.Game_Started_ByAdmin:
            //  console.log('Game_Started_ByAdmin', { ...action.data })
            return {
                ...state,
                gameStarted: { ...action.data }
            }

        case ActionTypes.OnFetch_GameBased_Dates:{
            return {
                ...state,
                gameBasedDates: [ ...action.data ]
            }
        }
        default:
            return state;
    }
}

export default GameManagementReducer;