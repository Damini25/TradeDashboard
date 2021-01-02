import React from 'react';
import Chart from 'chart.js';
import './PLComponent.scss';
import { connect } from 'react-redux';
import * as actiontypes from '../../common/store/actions/actionIndex';
import { convertTimeToDecimal } from '../../common/utilities/utilities';

class PLChart extends React.Component {

    constructor(props) {
        super(props);
        this.canvasRef = React.createRef();
    }

    componentDidUpdate(prevProps) {
        if (this.props.plList !== prevProps.pLList) {
            // console.log('pllist', this.props.pLList, this.props.minY, this.props.maxY);
            const plData = [];
            if (this.props.plList.length) {
                this.props.plList.map((elem) => {
                    if (elem.plValue) {
                        plData.push({
                            x: convertTimeToDecimal(elem.time),
                            y: elem.plValue
                        })
                    }
                });
            }
            // console.log('plList', plData)
            this.myChart = new Chart(this.canvasRef.current, {
                type: 'line',
                data: {
                    datasets: [
                        {
                            label: '# P&L',
                            data:plData,
                            borderWidth: 4,
                            fill: false,
                            backgroundColor: "#5b9bd5",
                            borderColor: "#5b9bd5",
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
                            type: 'linear'
                        }],
                        yAxes: [{
                            gridLines: {
                                drawBorder: false,
                               //  display: false
                            },
                            ticks: {
                                // suggestedMin: this.props['minY'] - 1,
                                // suggestedMax: this.props['maxY'] + 2,
                                // stepSize: ((this.props['maxY'] + 2) - (this.props['minY'] - 1)) / 8
                            }
                        }]
                    }
                }
            });
        }
    }
    render() {
        return (<div className="pl-chart-div">
            <canvas ref={this.canvasRef} />
        </div>);
    }
}


const mapStateToProps = (state) => {
    return {
        plList: state.fetchDataReducer['portfolio']['pLData']['pLList'],
        minY: state.fetchDataReducer['portfolio']['pLData']['minY'],
        maxY: state.fetchDataReducer['portfolio']['pLData']['maxY']
    }
}
const mapdispatchToProps = (dispatch) => {
    return {
        onLoadPortfolioList: (payload) => {
            dispatch(actiontypes.LoadPortfolioList(payload));
        }
    }
}
export default connect(mapStateToProps, mapdispatchToProps)(PLChart);