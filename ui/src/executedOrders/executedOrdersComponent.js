import React from 'react';
import './executedOrdersComponent.scss';
import { getBookedOrderList, getExecutedOrderList } from '../orderEntry/services/orderEntry.service';
import { connect } from 'react-redux';
import * as actiontypes from '../common/store/actions/actionIndex';
import { getLocalStorage } from '../common/localStorageService';

class ExecutedOrderList extends React.Component {
    state = {
        orderTabActive: true,
        tradeTabActive: false,
        dataToShow: []
    }

    componentDidMount() {
        this.fetchBookedOrderList();
        // this.fetchExecutedOrderList();
        this.setState({
            ...this.state,
            dataToShow: [...this.props.bookedOrdersList]
        })
    }
    componentDidUpdate(prevProps) {
        if (prevProps.bookedOrdersList !== this.props.bookedOrdersList) {
            this.setState({
                // orderTabActive: this.state.orderTabActive ? this.state.orderTabActive : true,
                // tradeTabActive: this.state.tradeTabActive ? this.state.tradeTabActive : false,
                dataToShow: this.state.tradeTabActive ? [...this.props.executedOrdersList] : [...this.props.bookedOrdersList]
            })
        }

        if (this.props.bookOrderFormNewValue['stockSymbol'] &&
            prevProps.bookOrderFormNewValue['stockSymbol'] !== this.props.bookOrderFormNewValue['stockSymbol']) {
            this.fetchBookedOrderList();
        }

        /**
         * Play and Pause feature
         */
        if (this.fetchOrderListInterval && !this.props.playbackOrdersFlow) {
            if (this.fetchOrderListInterval) {
                clearInterval(this.fetchOrderListInterval);
            }
        } else if (this.props.playbackOrdersFlow && prevProps['playbackOrdersFlow'] !== this.props.playbackOrdersFlow) {
            this.fetchBookedOrderList();
        }
    }
    showOrderTradeData(type) {
        // this.setState({
        //     ...this.state,
        //     orderTabActive: !this.state.orderTabActive,
        //     tradeTabActive: !this.state.tradeTabActive
        // })
        // console.log('ss', this.state);
        if (type === 'trades') {
            this.setState({
                orderTabActive: !this.state.orderTabActive,
                tradeTabActive: !this.state.tradeTabActive,
                dataToShow: [...this.props.executedOrdersList]
            })
        } else {
            this.setState({
                orderTabActive: !this.state.orderTabActive,
                tradeTabActive: !this.state.tradeTabActive,
                dataToShow: [...this.props.bookedOrdersList]
            })
        }
    }

    /**
      * API call to fetch Booked Orders List data
      */
    fetchBookedOrderList = () => {
        const payload = {
            "productId": parseInt(this.props.bookOrderFormNewValue['stockSymbol']),
            "gameId": 1,
            "traderId": parseInt(getLocalStorage('traderId')),
            "noOfRows": 20
        }
        this.props.onLoadBookedOrders(payload);
        if (this.fetchOrderListInterval) {
            clearInterval(this.fetchOrderListInterval);
        }
        //  console.log('val', this.state);
        if (!this.props.playbackOrdersFlow) {
            if (this.fetchOrderListInterval) {
                clearInterval(this.fetchOrderListInterval);
            }
        }
        this.fetchOrderListInterval = setInterval(this.fetchBookedOrderList, getLocalStorage('orderFetchInterval'));
    }

    /**
        * Function call when component is destroyed
         */
    componentWillUnmount() {
        clearInterval(this.fetchOrderListInterval);
    }
    render() {
        // console.log('this.state.dataToShow', this.props.bookedOrdersList, this.props.executedOrdersList)
        let row = [];

        // this.state.dataToShow=this.props.bookedOrdersList;
        if (this.state.dataToShow && this.state.dataToShow.length) {
            row = this.state.dataToShow.map((elem, i) => {
                const time = this.state.tradeTabActive ? elem['orderExecutionTime'] : elem['orderTime']
                const d = new Date(time);
                const date = d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();

                return (
                    <tr key={i}>
                        <td>{date}</td>
                        {this.state.tradeTabActive ?
                            <td>{elem['isSelling'] ? 'Sell' : 'Buy'}</td> :
                            <td>{elem['bidOffer']}</td>
                        }
                        <td>{elem['price']}</td>
                        <td>{elem['unfulfilledQuantity']}</td>
                    </tr>
                );
            })
        }

        return (
            <div className="exec-orderlist-div">
                {/* <h3>Executed Orders</h3> */}
                <div className="tab-div">
                    <label className={this.state.orderTabActive ? 'lbl-active' : ''}
                        onClick={() => { this.showOrderTradeData('orders') }}>Order Book</label>
                    <label className={this.state.tradeTabActive ? 'lbl-active' : ''}
                        onClick={() => { this.showOrderTradeData('trades') }}>Trade Book</label>
                </div>
                <div className="table-div">
                    <table>
                        <thead>
                            <tr>
                                <th>Time</th>
                                <th>{this.state.orderTabActive ? 'Bid/Ask' : 'Buy/Sell'}</th>
                                <th>Price</th>
                                <th>Volume</th>
                            </tr>
                        </thead>
                        <tbody>
                            {row}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}


const mapdispatchToProps = (dispatch) => {
    return {
        onLoadBookedOrders: (payload) => {
            dispatch(actiontypes.LoadBookedOrders(payload));
        },
        onLoadExecutedOrders: (payload) => {
            dispatch(actiontypes.LoadExecutedOrders(payload));
        },
    }
}
const mapStateToProps = (state) => {
    // console.log('stateExecutedOrderList', state.fetchDataReducer['bookedOrders'],
    // state.fetchDataReducer['executedOrders']);
    return {
        bookedOrdersList: state.fetchDataReducer['bookedOrders'],
        executedOrdersList: state.fetchDataReducer['executedOrders'],
        bookOrderFormNewValue: state.orderBookReducer.bookOrderFormValue,
        traderId: state.fetchDataReducer['userDetails']['traderId'],
        playbackOrdersFlow: state.orderListReducer['playbackOrdersFlow']
    }
}
export default connect(mapStateToProps, mapdispatchToProps)(ExecutedOrderList)