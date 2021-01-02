import React from 'react';
import './portfolioListComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../../common/store/actions/actionIndex';

class PortfolioComponent extends React.Component {

    componentDidUpdate(prevProps) {
        /**
        * Play and Pause feature
        */
        if (this.fetchPortfolioListInterval && !this.props.playbackOrdersFlow) {
            if (this.fetchPortfolioListInterval) {
                clearInterval(this.fetchPortfolioListInterval);
            }
        } else if (this.props.playbackOrdersFlow && prevProps['playbackOrdersFlow'] !== this.props.playbackOrdersFlow) {
            this.fetchPortfolioList();
        }
    }
   
    render() {
        let row = [];
        if (this.props.portFolioList && this.props.portFolioList.length) {
            row = this.props.portFolioList.map((elem, i) => {
                let statusColorCode;
                if (elem['colorCoding'] === 1) {
                    statusColorCode = 'show-green';
                } else if (elem['colorCoding'] === -1) {
                    statusColorCode = 'show-red';
                } else {
                    statusColorCode = 'show-white';
                }
                return (
                    <tr key={i}>
                        <td>{elem['ticker']}</td>
                        <td>{elem['productType']}</td>
                        <td>{elem['position']}</td>
                        <td>{elem['cost']}</td>
                        <td className={statusColorCode}>{elem['last']}</td>
                        <td className={statusColorCode}>{elem['bid']}</td>
                        <td className={statusColorCode}>{elem['ask']}</td>
                        <td>{elem['realizedPnl']}</td>
                        <td>{elem['unrealizedPnl']}</td>
                    </tr>
                );
            })
        }

        return (
                <div className="table-div">
                    <table>
                        <thead>
                            <tr>
                                <th>Ticker</th>
                                <th>Type</th>
                                <th>Position</th>
                                <th>WAP</th>
                                <th>Last</th>
                                <th>Bid</th>
                                <th>Ask</th>
                                <th>Realized P&L</th>
                                <th>Unrealized P&L</th>
                            </tr>
                        </thead>
                        <tbody>
                            {row}
                        </tbody>
                    </table>
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
        bookOrderFormNewValue: state.orderBookReducer.bookOrderFormValue,
        traderId: state.fetchDataReducer['userDetails']['traderId'],
        portFolioList: state.fetchDataReducer['portfolio']['portFolioList'],
        playbackOrdersFlow: state.orderListReducer['playbackOrdersFlow']
    }
}
export default connect(mapStateToProps, mapdispatchToProps)(PortfolioComponent)