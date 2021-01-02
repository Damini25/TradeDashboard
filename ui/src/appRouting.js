import React from 'react';
import { Route, Switch} from 'react-router-dom';
import Login from './login/login';
import Notfound from './common/component/notFound/notFoundComponent';
import MainNavigation from './mainNavigation/mainNavigationcomponent';
import PrivateRoute from './common/component/privateRouteComponent';

const appRoutes = <Switch>
    <Route path="/" exact strict component={Login} ></Route>
    <Route path="/login" exact strict component={Login} ></Route>
    {/* <Route path="/mainNav/orderEntry" exact component={OrderEntry} ></Route> */}
    {/* <Route path="/mainNav"  strict  component={MainNavigation} ></Route> */}
    <PrivateRoute strict path="/mainNav" component={MainNavigation}></PrivateRoute>
    <Route component={Notfound}></Route>
    {/* <PrivateRoute path="/admin" component={AdminRouting}></PrivateRoute> */}
</Switch>

export default appRoutes;