import React from 'react';
import './listGameComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../../common/store/actions/actionIndex';
import EditGameDialog from '../editGame/editGameComponent';
import { convertToDateTime } from '../../common/utilities/utilities';

// import * as actiontypes from '../common/store/actions/actionIndex'; 
import { getLocalStorage } from '../../common/localStorageService';

class ListGames extends React.Component {
    state = {
        editModalOpen: false
    }

    componentDidMount() {
        this.props.onLoadGameData({ 'userId': parseInt(getLocalStorage('traderId')) });
    }

    /* openEditDialog(elem) {
         console.log('editdialogelem', elem)
         this.setState({
             editModalOpen: true
         })
     }*/

    editGame(elem) {

        this.props.onUpdateCreateGameFormValue({
            gameId: elem['gameId'],
            gameName: elem['gameCode'],
            gameMode: elem['gameMode'],
            startingCash: elem['startingBalance'],
            volume: elem['startingVolume'],
            transaction: elem['bidAsk'],
            playbackFrequency: elem['playbackFrequency'],
            playbackStartTime: new Date(elem['playbackStartTime']),
            playbackEndTime: new Date(elem['playbackEndTime'])
        })
        //   this.props.onUpdateOrderFormValue({ 'price': elem['price'] })
        //   this.props.onUpdateOrderFormValue({ 'quantity': elem['unfulfilledQuantity'] })
    }

    handleEditDialogClose = (value) => {
        this.setState({
            editModalOpen: false
        })
    };

    startGame(elem) {
        this.props.onGameStart(elem);
    }

    endGame(elem) {
        const payload = {
            gameId: elem['gameId']
        }
        this.props.onGameEnd(payload);
    }

    deleteGame(elem) {
        this.props.onDeleteGame(elem);
    }

    playPauseDataFlow(elem) {
        //  elem['playbackFlag'] = !elem['playbackFlag'];

        const payload = {
            'gameId': elem['gameId'],
            'gameName': elem['gameCode'],
            'gameMode': elem['gameMode'],
            'transaction': elem['transaction'],
            'gameInterval': elem['gameInterval'],
            'startingCash': elem['startingBalance'],
            'startingVolume': elem['startingVolume'],
            'playbackStartTime': elem['playbackStartTime'],
            'playbackEndTime': elem['playbackEndTime'],
            'playbackFrequency': elem['playbackFrequency'],
            'playbackFlag': !elem['playbackFlag'],
            'isGameActive': elem['isGameActive']
        }
        console.log('pause', elem)
        this.props.onPostCreateGameData(payload);
    }
    render() {
        let row = [];
        if (this.props.gameList && this.props.gameList.length) {
            row = this.props.gameList.map((elem, i) => {

                return (
                    <tr key={i} >
                        <td>{elem['gameCode']}</td>
                        <td>{elem['gameMode']}</td>
                        <td>{elem['startingBalance']}</td>
                        <td>{elem['startingVolume'] ? elem['startingVolume'] : '-'}</td>
                        <td>{elem['bidAsk'] ? elem['bidAsk'] : '-'}</td>
                        <td>{elem['isGameActive'] ? 'Active' : 'Inactive'}</td>
                        <td>
                            <label >
                                <i className="fa fa-edit" onClick={() => this.editGame(elem)}></i></label>
                            <label onClick={() => this.deleteGame(elem)} title="Delete Game"><i className="fa fa-trash" ></i></label>
                            {
                                elem['isGameActive'] !== true ? <label title="Start Game" onClick={() => this.startGame(elem)}><i className="fa fa-arrow-right start-game-icon" ></i></label> :
                                    <label title="End Game" onClick={() => this.endGame(elem)}><i className="fa fa-stop end-game-icon"></i></label>
                            }

                        </td>
                        <td>{convertToDateTime(elem['playbackStartTime'])}</td>
                        <td>{convertToDateTime(elem['playbackEndTime'])}</td>
                        <td>
                            {!elem['playbackFlag'] ? <button disabled={!elem['isGameActive']} title="Play" onClick={() => this.playPauseDataFlow(elem)}><i className="fa fa-play start-game-icon" ></i></button> :
                                <button title="Pause" onClick={() => this.playPauseDataFlow(elem)}><i className="fa fa-pause start-game-icon"></i></button>
                            }
                        </td>
                    </tr>
                );
            })
        }

        return (
            <div className="list-games-div">
                {/* <h3>games</h3> */}
                <div className="table-div">
                    <table>
                        <thead>
                            {/* <tr>
                                <th colSpan="6">Basic game rules</th>
                                <th colSpan="3">Playback rules</th>
                            </tr> */}
                            <tr className="secondary-color">
                                <th>Game Name</th>
                                <th>Game Mode</th>
                                <th>Starting Cash</th>
                                <th>Volume</th>
                                <th>Buy/Sell</th>
                                <th>Status</th>
                                <th>Action</th>
                                <th>Playback Start Time</th>
                                <th>Playback End Time</th>
                                <th>Play/Pause</th>
                            </tr>
                        </thead>
                        <tbody>
                            {row}
                        </tbody>
                    </table>
                </div >
                <EditGameDialog editDialogopen={this.state.editModalOpen}
                    editDialogClose={this.handleEditDialogClose}></EditGameDialog>
            </div >
        );
    }
}


const mapdispatchToProps = (dispatch) => {
    return {
        onLoadGameData: (param) => {
            dispatch(actiontypes.LoadGameData(param))
        },
        onGameStart: (payload) => {
            dispatch(actiontypes.GameStartedByAdmin(payload))
        },
        onGameEnd: (payload) => {
            dispatch(actiontypes.GameStoppedByAdmin(payload))
        },
        onDeleteGame: (payload) => {
            dispatch(actiontypes.GameDeletedByAdmin(payload))
        },
        onUpdateCreateGameFormValue: (data) => {
            dispatch(actiontypes.UpdateCreateGameFormValues(data))
        },
        onPostCreateGameData: (obj) => {
            dispatch(actiontypes.PostCreateGameData(obj))
        }
    }
}
const mapStateToProps = (state) => {
    // console.log('stateExecutedOrderList', state.gameManagementReducer['listGames']);
    return {
        gameList: state.gameManagementReducer['listGames']
    }
}
export default connect(mapStateToProps, mapdispatchToProps)(ListGames)