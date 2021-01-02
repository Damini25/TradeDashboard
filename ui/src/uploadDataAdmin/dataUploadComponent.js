import React from 'react';
import './dataUploadComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../common/store/actions/actionIndex';
import { getLocalStorage } from '../common/localStorageService';

class DataUpload extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            gameId: "",
            newsFile: "",
            ordersFile: ""
        }

        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        this.props.onLoadGameData({ 'userId': parseInt(getLocalStorage('traderId')) });
    }
    componentDidUpdate(prevProps) {
        if(prevProps.apiResolved !== this.props.apiResolved && this.props.apiResolved){
            this.setState({
                ...this.state,
                gameId:'',
                newsFile: '',
                ordersFile: ''
            })
            this.fileRefNews.value = null;
            this.fileRefData.value = null;
        }
    }
    fileRefNews = React.createRef();
    fileRefData = React.createRef();

    uploadFile(type) {
        const payload = {};
        if (type === 'newsFile') {
            this.props.onUploadData({
                type: 'newsData',
                payload: { gameId: this.state.gameId, file: this.fileRefNews.files[0] }
            })
         /*   if (this.state.apiResolved) {
                this.setState({
                    ...this.state,
                    newsFile: ''
                })
                this.fileRefNews.value = null;
            }*/

        } else {
            this.props.onUploadData({
                type: 'gameData',
                payload: { file: this.fileRefData.files[0] }
            })
           /* if (this.state.apiResolved) {
                this.setState({
                    ...this.state,
                    ordersFile: ''
                })
                this.fileRefData.value = null;
            }*/
        }
    }

    handleChange(event) {
        const val = event.target.name === 'gameId' ? event.target.value : event.target.files[0]
        this.setState({
            ...this.state,
            [event.target.name]: val
        })
    }



    render() {
       // console.log('render', this.state)
        return (
            <div className="main-upload-data-div">

                <div className="file-input-div">
                    <h3>
                        Upload News File
                    </h3>
                    <div className="game-id-div">
                        <label title="Select Game (for which you want to upload news)">Select Game</label>
                        <select name="gameId"
                            value={this.state.gameId}
                            onChange={(e) => { this.handleChange(e) }}>
                            <option disabled value="">Select Game</option>
                            {this.props.gameList && this.props.gameList.length ?
                                this.props.gameList.map((elem, i) => {
                                    return (
                                        <option key={i} value={elem['gameId']}>
                                            {elem['gameCode']}
                                        </option>
                                    )
                                }) : ''}
                        </select>
                    </div>
                    <div >
                        {/* <label title="Choose file">Upload file</label> */}
                        <input
                            title="Upload news file (in csv format)"
                            ref={(input) => { this.fileRefNews = input; }}
                            onChange={this.handleChange}
                            name="newsFile" type="file"
                        />
                        <button className="upload-div-btn primary-color button" onClick={() => this.uploadFile('newsFile')}>Upload</button>
                    </div>
                </div>

                <div className="file-input-div">
                    <h3>
                        Upload Data file
                    </h3>
                    <div >
                        {/* <label title="Choose file">Upload file</label> */}
                        <input type="file"
                            name="ordersFile"
                            onChange={this.handleChange}
                            ref={(input) => { this.fileRefData = input; }}

                        />
                        <button className="upload-div-btn primary-color button" onClick={() => this.uploadFile('ordersFile')}>Upload</button>
                    </div>
                </div>

            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        ordersFile: state.gameManagementReducer.ordersFile,
        newsFile: state.gameManagementReducer.newsFile,
        gameList: state.gameManagementReducer['listGames'],
        apiResolved: state.requestStatusReducer['isFetching'],
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        onUploadData: (data) => {
            dispatch(actiontypes.UploadGameBasedDataFile(data))
        },
        onLoadGameData: (param) => {
            dispatch(actiontypes.LoadGameData(param))
        }
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(DataUpload);