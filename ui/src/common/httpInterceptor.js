import axios from 'axios';
import { getLocalStorage,clearLocalStorageKey } from '../common/localStorageService';
import { showToast} from '../common/component/toastMessages/toastcomponent';
import {toast } from 'react-toastify';
//axios.defaults.baseURL = 'http://localhost:8303';
axios.defaults.headers.post['Content-Type'] = 'application/json';
axios.defaults.headers.post['Access-Control-Allow-Origin'] = '*';

axios.interceptors.request.use((req) => {

    // req.headers = {
    //     gameSessionId: getLocalStorage('gameSessionId') ? getLocalStorage('gameSessionId') : '',
    //     userId: getLocalStorage('traderId') ? getLocalStorage('traderId') : ''
    // }
    if (getLocalStorage('gameSessionId')) {
        req['headers']['gameSessionId'] = getLocalStorage('gameSessionId')
    }
    if (getLocalStorage('traderId')) {
        req['headers']['userId'] = getLocalStorage('traderId')
    }
    if (getLocalStorage('gameId')) {
        req['headers']['gameId'] = getLocalStorage('gameId')
    }
    
    //  req.url = req.url + `?requestedTime=${Date.now()}`
    return req;
});

axios.interceptors.response.use((res) => {
    if(res['error']==='gameSessionEnded'){
        clearLocalStorageKey('gameSessionId');
     //   console.log('toast',toast.isActive('gameSessionId'))
        // if(!toast.isActive('gameSessionEnded')){
        //     showToast('error', 'Game session has expired','gameSessionEnded');
        // }
        
    }
    return res;
});