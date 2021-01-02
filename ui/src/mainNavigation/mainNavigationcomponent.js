import React from 'react';
//import Sidebar from "react-sidebar";
import './mainNavigationComponent.scss'
import { Route, Switch, BrowserRouter } from 'react-router-dom';
import SideBar from './menuLinks/menuLinkComponent'
import OrderEntry from '../orderEntry/container/orderEntry';
import ManageGame from '../manageGame/manageGameComponent';
import ExecOrderList from '../executedOrders/executedOrdersComponent';
// import CreateGame from '../manageGame/createGame/createGameComponent';
import JoinGame from '../joinGame/joinGameComponent';
import PrivateRoute from '../common/component/privateRouteComponent';
import NewsFeed from '../orderEntry/component/newsFeed/newsFeedComponent';
import DataUpload from '../uploadDataAdmin/dataUploadComponent';

class MainNavigation extends React.Component {

    componentDidMount() {
        //   console.log('urlddcomponentDidMount', this.props.match.url)
    }
    state = {
        sidebarOpen: false
    };

    menuToggle(e) {
        e.stopPropagation();
        this.setState({
            sidebarOpen: !this.state.sidebarOpen
        });
    }

    render() {
        //  console.log('url', this.props.match.url)
        return (
            <div className="outer-container">
                <div className="header-div" >
                    <SideBar pageWrapId={"page-wrap"} {...this.props} />
                    {/* <NewsFeed></NewsFeed> */}
                </div>
              
                <Switch>
                    <PrivateRoute exact  path="/mainNav" component={OrderEntry}></PrivateRoute>
                    <PrivateRoute exact strict path="/mainNav/orderEntry" component={OrderEntry}></PrivateRoute>
                    <PrivateRoute exact strict path="/mainNav/manageGame" component={ManageGame}></PrivateRoute>
                    <PrivateRoute exact strict path="/mainNav/joinGame" component={JoinGame}></PrivateRoute>
                    <PrivateRoute exact strict path="/mainNav/uploadDataAdmin" component={DataUpload}></PrivateRoute>
                </Switch>

            </div>
        )
    }
}
export default MainNavigation