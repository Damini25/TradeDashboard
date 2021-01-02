import React from 'react';
import { Route, Redirect} from 'react-router-dom';
import {getLocalStorage} from '../localStorageService';

const PrivateRouteComponent = ({ component: Component, ...rest }) => {
    // console.log('privateRoute1 rest', rest);
    return <Route {...rest} exact strict render={
        (props) => {
           // console.log('privateRoute2 props', props,getLocalStorage('traderId'));
           return getLocalStorage('traderId') ? <Component {...props} /> : <Redirect
                to={{
                    pathname: '/login',
                    state: { from: props.location }
                }
                }
            ></Redirect>
        }
    }></Route >

}
export default PrivateRouteComponent;