import axios from 'axios';
import { env } from '../common/environment';

export const createNewGame = (formvalues) => {
  const playbackStartTime= new Date(formvalues['playbackStartTime']).getHours() + ":"+ new Date(formvalues['playbackStartTime']).getMinutes();
  const playbackEndTime= new Date(formvalues['playbackEndTime']).getHours() + ":"+ new Date(formvalues['playbackEndTime']).getMinutes();
 
  const payload = {
    'gameCode': formvalues['gameName'],
    'gameMode': formvalues['gameMode'],
    'buySell': formvalues['transaction'],
    'gameInterval': formvalues['gameInterval'],
    'startingBalance': formvalues['startingCash'],
    'startingVolume': formvalues['volume'],
    'playbackStartTime': new Date(formvalues['playbackDate'] +' ' + playbackStartTime),
    'playbackEndTime': new Date(formvalues['playbackDate'] +' ' + playbackEndTime),
    'playbackFrequency': formvalues['playbackFrequency']
  }
  if (formvalues['gameId']) {
    payload['gameId'] = formvalues['gameId']
  }
  if (formvalues['playbackFlag'] !== undefined || formvalues['playbackFlag'] !== null) {
    payload['playbackFlag'] = formvalues['playbackFlag']
  }
  if (formvalues['isGameActive'] !== undefined || formvalues['isGameActive'] !== null) {
    payload['isGameActive'] = formvalues['isGameActive']
  }
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/game/creategame`, payload);
}


export const uploadHistoricalDataFile = (payload) => {
  console.log('uploadHistoricalDataFile', payload);
  const formData = new FormData()
  formData.append('file', payload['file']);
 // formData.append('gameId', gameId);
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/gamedata/uploadFile`, formData).then(response => ({ response }))
  .catch(error => ({ error }));
};

export const uploadNewsDataFile = (payload) => {
  console.log('uploadHistoricalDataFile', payload);
  const formData = new FormData()
  formData.append('file', payload['file']);
  formData.append('gameId', payload['gameId']);

  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/gamedata/uploadNews`, formData).then(response => ({ response }))
  .catch(error => ({ error }));
};

export const getGameList = (payload) => {
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/game/allgames`, {});
}

export const callJoinGame = (payload) => {
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/game/joingame?gameId=${payload['gameId']}`, {}).then(response => ({ response }))
    .catch(error => ({ error }));;
}

export const callStartGame = (payload) => {
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/game/startgame`, payload);
}
export const callStopGame = (payload) => {
  return axios.post(`${env.apiUrl}/trading/gamemgmt-service/game/stopgame?gameId=${payload['gameId']}`, {});
}
export const callDeleteGame = (payload) => {
  return axios.delete(`${env.apiUrl}/trading/gamemgmt-service/game/deletegame?gameId=${payload['gameId']}`);
}

export const getGameBasedDateList = () => {
 return axios.get(`${env.apiUrl}/trading/gamemgmt-service/gamedata/datewisedata`);
}