import React from 'react';
import './askComponent.scss';
import _ from 'lodash';
import {getLocalStorage} from '../../../../common/localStorageService';

class AskComponent extends React.Component {
    constructor(props){
        super();
        this.props=props;
    }

    randomNumber(minimum, maximum) {
        return Math.round(Math.random() * (maximum - minimum) + minimum);
    }

    render() {
       // console.log('ask ordr',this.props.orders)
       //_.sortBy(this.props.orders, ['price','timestamp'],['asc','desc'])
        const row = _.sortBy(this.props.orders, ['price','timestamp'],['asc','desc']).map((elem,i) => {
            const random = this.randomNumber(0, 1);
            return (
                <tr key={i} className={elem['traderId'] === parseInt(getLocalStorage('traderId')) ? 'backgroundBlue' : ''}>
                    <td><a href="#" onClick={() => { this.props.askPriceClicked(elem) }}>{elem['price']}</a></td>
                    <td>{elem['unfulfilledQuantity']}</td>
                </tr>
            );
        })
        
        return (
            <div >
                <h3>Ask</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Price</th>
                            <th>Volume</th>
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

export default AskComponent;