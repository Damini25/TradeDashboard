import React from 'react';
import './traderInfoComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../../../common/store/actions/actionIndex';

class TraderInfo extends React.Component {
     /**
     *Function call on product symbol change
     */
    productChange(event) {
        this.props.onUpdateProductValue({ 'stockSymbol': parseInt(event.target.value) })
        this.props.onClearMinMaxChartData();
    }

    render() {
        return (<div className="trader-info-div">
            <h3>Trader Info</h3>
            <div className="sub-div">
                {/* <div>
                    <label>Trader Id </label>
                    <span>-</span>
                    <span>Trd-012</span>
                </div> */}
                <div>
                    <label>Name </label>
                    <span>-</span>
                    <span>{this.props.getTraderDetails['name']}</span>
                </div>
                <div>
                    <label>Game </label>
                    <span>-</span>
                    <span>Interactive Trading</span>
                </div>
                <div>
                    <label>Balance </label>
                    <span>-</span>
                    <span>$1,00,00</span>
                </div>
            </div>
            <div className="sub-div2">
                <label>Select product name</label>
                <select onChange={(e) => { this.productChange(e) }}
                    value={this.props.bookOrderFormNewValue['stockSymbol']}
                    name="stockSymbol">
                    <option disabled value="">Select product name</option>
                    {this.props.stockSymbol && this.props.stockSymbol.length ?
                        this.props.stockSymbol.map((elem) => {
                            return (
                                <option key={elem['productId']} value={elem['productId']}>
                                    {elem['productCode'] + '-' + elem['productName']}
                                </option>
                            )
                        }) : ''}
                </select>
            </div>
        </div>);
    }
}
const mapdispatchToProps = (dispatch) => {
    return {
        onUpdateProductValue: (obj) => {
            dispatch(actiontypes.UpdateOrderFormValues(obj))
        },
        onClearMinMaxChartData: () => {
            dispatch(actiontypes.ClearTotalMinMaxOrders())
        }
    }
}
const mapStateToProps = (state) => {
    //console.log('name',state.fetchDataReducer.userDetails)
    return {
        getTraderDetails: state.fetchDataReducer.userDetails,
        bookOrderFormNewValue: state.orderBookReducer.bookOrderFormValue,
        stockSymbol: state.fetchDataReducer.stockSymbols['data']
    }
}
export default connect(mapStateToProps,mapdispatchToProps)(TraderInfo);