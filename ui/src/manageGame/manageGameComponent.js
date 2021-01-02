import React from 'react';
import './manageGameComponent.scss';
import CreateGame from './createGame/createGameComponent';
import ListGames from './listGame/listGameComponent';

class ManageGame extends React.Component {

    render() {
        return (
            <div className="manage-game">
                <CreateGame></CreateGame>
                <ListGames></ListGames>
            </div>
        );
    }
}
export default ManageGame;