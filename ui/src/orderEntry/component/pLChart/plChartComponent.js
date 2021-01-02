import React from 'react';
import Chart from 'chart.js';
import './plChartComponent.scss';

class LineChart extends React.Component {

    constructor(props) {
        super(props);
        this.canvasRef = React.createRef();
    }

    componentDidMount() {
        this.myChart = new Chart(this.canvasRef.current, {
            type: 'line',
            data: {
                labels: [0, 10, 20, 30, 40, 50, 60, 70, 80],
                datasets: [
                    {
                        label: '# Profit',
                        data: [0, 20, 1000, 1050, 3000, 4000, 1000, 2000, 2050,],
                        borderWidth: 1,
                        fill: false,
                        backgroundColor: "#5b9bd5",
                        borderColor: "#5b9bd5",
                        lineTension: 0
                    }
                ]
            },
            options: {
                legend: {
                    display: false,
                },
                scales: {
                    xAxes: [{
                        gridLines: {
                            color: "#404040"
                        },
                        ticks: {
                            padding: 5,
                            min: 0,
                            max: 80,
                            stepSize: 10,
                            fontColor: "#c5c1c1"
                        }
                    }],
                    yAxes: [{
                        gridLines: {
                            drawBorder: false,
                            color: "#404040"
                        },
                        ticks: {
                            padding: 10,
                            min: -1000,
                            max: 6000,
                            stepSize: 1000,
                            fontColor: "#c5c1c1"
                        }
                    }]
                },
                elements: {
                    point: { radius: 0 }
                }
            }
        });
    }

    render() {
        return (<div className="pl-chart-div">
            <h3>P&L Charting</h3>
            <canvas ref={this.canvasRef} />
        </div>);
    }
}

export default LineChart;