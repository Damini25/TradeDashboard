import React from 'react';
import { Route, Switch, BrowserRouter } from 'react-router-dom';
import OrderEntry from '../orderEntry/container/orderEntry';
/**
 * Not used yet
 */
const MainNavRoutes = () => {
    return <BrowserRouter>
        <Switch>
            {/* <Route path="/mainNav" exact component={MainNavigation} ></Route>
            <Route path="/mainNav/orderEntry" exact component={OrderEntry} ></Route> */}
        </Switch>
    </BrowserRouter>
}

export default MainNavRoutes;