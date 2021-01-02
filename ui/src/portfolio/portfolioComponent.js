import React from 'react';
import './portfolioComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../common/store/actions/actionIndex';
import PLChart from './P&L/PLComponent';
import PortfolioList from './portfolioList/portfolioListComponent';
import { getLocalStorage } from '../common/localStorageService';

class PortfolioComponent extends React.Component {
    state = {
        portfolioListActive: true,
        pLActive: false
    }

    componentDidMount() {
        this.fetchPortfolioList();
    }

    /**
      * API call to fetch Portfolios List data
      */
     fetchPortfolioList = () => {
        const payload = {
            "productId": parseInt(this.props.bookOrderFormNewValue['stockSymbol']),
            "noOfRows": 20
        }
        this.props.onLoadPortfolioList(payload);
        if (this.fetchPortfolioListInterval) {
            clearInterval(this.fetchPortfolioListInterval);
        }
        if (!this.props.playbackOrdersFlow) {
            if (this.fetchPortfolioListInterval) {
                clearInterval(this.fetchPortfolioListInterval);
            }
        }
        this.fetchPortfolioListInterval = setInterval(this.fetchPortfolioList, getLocalStorage('orderFetchInterval'));
    }

    portfolioTabSwitch(type) {
        this.setState({
            portfolioListActive: !this.state.portfolioListActive,
            pLActive: !this.state.pLActive,
        })
    }

    render() {
        return (
            <div className="portfolio-list-div">
                {/* <h3>Portfolio/Position</h3> */}
                <div className="tab-div">
                    <label className={this.state.portfolioListActive ? 'lbl-active' : ''}
                        onClick={() => { this.portfolioTabSwitch('positionList') }}>Portfolio/Position</label>
                    <label className={this.state.pLActive ? 'lbl-active' : ''}
                        onClick={() => { this.portfolioTabSwitch('PL') }}>P&L</label>
                </div>
                <div className="user-info-div">
                    <div>
                        <label>Starting Balance:</label>
                        <span>{this.props.startingBalance}</span>
                    </div>
                    <div>
                        <label>Available Balance:</label>
                        <span>{this.props.availableBalance}</span>
                    </div>
                </div>
                {this.props.availableVolume ?
                    <div className="user-info-div">
                        <div>
                            <label>Starting Volume:</label>
                            <span>{this.props.startingVolume}</span>
                        </div>
                        <div>
                            <label>Available Volume:</label>
                            <span>{this.props.availableVolume}</span>
                        </div>
                    </div> : ''
                }

                {
                    this.state.portfolioListActive ? <PortfolioList></PortfolioList> : <PLChart></PLChart>
                }
            </div>
        );
    }
}


const mapdispatchToProps = (dispatch) => {
    return {
        onLoadPortfolioList: (payload) => {
            dispatch(actiontypes.LoadPortfolioList(payload));
        }
    }
}
const mapStateToProps = (state) => {
    return {
        availableBalance: state.fetchDataReducer['portfolio']['availableBalance'],
        startingBalance: state.fetchDataReducer['portfolio']['startingBalance'],
        startingVolume: state.fetchDataReducer['portfolio']['startingVolume'],
        availableVolume: state.fetchDataReducer['portfolio']['availableVolume'],
        bookOrderFormNewValue: state.orderBookReducer.bookOrderFormValue,
    }
}
export default connect(mapStateToProps, mapdispatchToProps)(PortfolioComponent)