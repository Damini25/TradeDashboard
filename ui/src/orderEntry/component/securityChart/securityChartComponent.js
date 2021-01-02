import React from 'react';
import Chart from 'chart.js';
import 'chartjs-plugin-zoom';
import './securityChartComponent.scss';
import { connect } from 'react-redux';
import { convertTimeToDecimal } from '../../../common/utilities/utilities';

class SecurityChart extends React.Component {

    constructor(props) {
        super(props);
        this.canvasRef = React.createRef();
    }

    componentDidUpdate(prevProps) {
        //  console.log('securitychart', this.props.minMaxBidOrders, this.props.minMaxAskOrders);
        if (prevProps.minMaxBidOrders !== this.props.minMaxBidOrders ||
            prevProps.minMaxAskOrders !== this.props.minMaxAskOrders) {
            const bidData = [];
            const askData = [];
            if (this.props.minMaxBidOrders.length) {
                this.props.minMaxBidOrders.map((elem) => {

                    if (elem.maxBid) {
                        bidData.push({
                            x: convertTimeToDecimal(elem.time),
                            y: elem.maxBid.price
                        })
                    }
                    //  console.log('bid',bidData)
                });
            }
            if (this.props.minMaxAskOrders.length) {
                this.props.minMaxAskOrders.map((elem) => {

                    if (elem.minAsk) {
                        askData.push({
                            // x: this.convertTimeToDecimal(elem.order.timestamp),
                            x: this.convertTimeToDecimal(elem.time),
                            y: elem.minAsk.price
                        })
                        //  console.log('ask',askData)
                    }
                });
            }

            //   console.log('askData, bidData', askData, bidData, this.convertTimeToDecimal('16:05:02'));
             this.myChart = new Chart(this.canvasRef.current, {
                type: 'line',
                data: {
                    // labels: [],
                    datasets: [
                        {
                            label: '# Bid',
                            // data: this.props.bidOrderList,
                            data: bidData,
                            borderWidth: 1,
                            fill: false,
                            backgroundColor: "#ed7d31",
                            borderColor: "#ed7d31",
                            // lineTension: 1,
                            //  pointStyle: 'rectRot',
                            //   pointRadius: 4,
                            //  pointHitRadius: 10,
                            pointBorderColor: "#ed7d31",
                            pointBackgroundColor: "#ed7d31",
                            pointBorderWidth: 1,
                            pointHoverRadius: 4,
                            pointHoverBackgroundColor: "#ed7d31",
                            pointHoverBorderColor: "black",
                            pointHoverBorderWidth: 2,
                        },
                        {
                            label: '# Ask',
                            // data: this.props.askOrderList,
                            data: askData,
                            borderWidth: 1,
                            fill: false,
                            backgroundColor: "#5b9bd5",
                            borderColor: "#5b9bd5",
                            borderColor: "#5b9bd5",
                            // lineTension: 0,
                            //  pointStyle: 'rectRot',
                            //  pointRadius: 4,
                            //  pointHitRadius: 10,
                            pointBorderColor: "#5b9bd5",
                            pointBackgroundColor: "#5b9bd5",
                            pointBorderWidth: 1,
                            pointHoverRadius: 4,
                            pointHoverBackgroundColor: "#5b9bd5",
                            pointHoverBorderColor: "black",
                            pointHoverBorderWidth: 2,
                        }
                    ]
                },
                options: {
                    showTooltips: false,
                    animation: false,
                    elements: {
                        point: {
                            radius: 0.4
                        }
                    },
                    legend: {
                        display: true,
                    },
                    scales: {
                        xAxes: [{
                            gridLines: {
                                display: false
                            },
                            ticks: {
                                precision: 2
                            },
                            // ticks: {
                            //     padding: 5,
                            //     min: 6,
                            //     max: 18,
                            //     stepSize: 1
                            // },
                            type: 'linear'
                        }],
                        yAxes: [{
                            gridLines: {
                                drawBorder: false,
                            },
                            ticks: {
                                // padding: 15,
                                suggestedMin: this.props['minPrice'] - 1,
                                suggestedMax: this.props['maxPrice'] + 2,
                                stepSize: ((this.props['maxPrice'] + 2) - (this.props['minPrice'] - 1)) / 8
                            }
                        }]
                    },
                    // pan: {
                    //     enabled: true,
                    //     mode: 'yx'
                    // },
                    // zoom: {
                    //     enabled: true,
                    //     mode: 'xy',
                    // }
                }
            });
        }
    }

    convertTimeToDecimal(val) {
        if (val && val.indexOf(':') > -1) {
            val = val.split(':');
            val = parseFloat(parseInt(val[0], 10) + parseInt(val[1], 10) / 60 + parseInt(val[2], 10) / 3600);
            return Math.round(val * 1000) / 1000;
        } else {
            const v = parseInt(val, 10);
            return Math.round(v * 1000) / 1000;
        }
    }

    render() {
        let prodName;
        if (this.props.stockSymbolData && this.props.stockSymbolData.length) {
            this.props.stockSymbolData.forEach(elem => {
                // console.log('elem', elem['productId'], this.props.bookOrderFormNewValue['stockSymbol'])
                if (elem['productId'] === this.props.bookOrderFormNewValue['stockSymbol']) {

                    prodName = elem;
                }
            });
        }
        return (<div className="security-chart-div">
            <h3>Bid/Ask Spread</h3>
            <div className="prod-name-label">
                <label>Product Name - </label> {prodName ? prodName['productCode'] + '-' + prodName['productName'] : ''}
            </div>
            <canvas ref={this.canvasRef} />
        </div>);
    }
}

const mapStateToProps = (state) => {
    console.log('minPrice,maxPrice', state.orderListReducer['ordersToShow']['minPriceYAxis'], state.orderListReducer['ordersToShow']['maxPriceYAxis'])
    return {
        bidOrderList: state.chartReducer['totalOrderTillNow']['bidOrders'],
        askOrderList: state.chartReducer['totalOrderTillNow']['askOrders'],
        bookOrderFormNewValue: state.orderBookReducer.bookOrderFormValue,
        stockSymbolData: state.fetchDataReducer.stockSymbols['data'],
        minMaxAskOrders: state.orderListReducer['ordersToShow']['minAskOrders'],
        minMaxBidOrders: state.orderListReducer['ordersToShow']['maxBidOrders'],
        minPrice: state.orderListReducer['ordersToShow']['minPriceYAxis'],
        maxPrice: state.orderListReducer['ordersToShow']['maxPriceYAxis']
    }
}

export default connect(mapStateToProps)(SecurityChart);