import React from 'react';
import { slide as Menu } from "react-burger-menu";
import { Link, NavLink } from 'react-router-dom';
import { clearLocalStorage } from '../../common/localStorageService';
import { getLocalStorage } from '../../common/localStorageService';

class MenuLinks extends React.Component {
    constructor(props) {
        super(props);
        this.props = props;
        this.state = {
            menuOpen: false,
            showGameMenu: false
        }
    }

    handleMenuStateChange(state) {
        this.setState({ menuOpen: state.isOpen })
    }

    showNestedMenu(val) {
        if (val === 'game') {
            this.setState({
                ...this.state, showGameMenu: !this.state.showGameMenu
            })
        }
    }

    closeMenuOnNavClick() {
        this.setState((state) => ({
            menuOpen: !state.menuOpen
        }))
    }

    logout() {
        clearLocalStorage();
        this.props.history.push("/login");
    }

    render() {
        console.log('menulinl', getLocalStorage('userTypeId'));
        return (
            <Menu isOpen={this.state.menuOpen} onStateChange={(state) => this.handleMenuStateChange(state)}
                customBurgerIcon={
                    <i className="fa fa-bars font-i-btn" aria-hidden="true"></i>
                } customCrossIcon={<i className="fa fa-angle-double-left" ></i>}>

                <div className="menu-userimg-div">
                    <img alt="" src="https://mdbootstrap.com/img/Photos/Avatars/img%20(10).jpg" className="user-img" />
                    <div>Welcome! Damini</div>
                </div>
                {
                    parseInt(getLocalStorage('userTypeId')) === 0 ?
                        // admin menu
                        <div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' >Home</NavLink>
                            </div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' to={{
                                    pathname: this.props.match.url + "/uploadDataAdmin"
                                }}>Data Upload
                                </NavLink>
                            </div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' to={{
                                    pathname: this.props.match.url + "/manageGame"
                                }}>Manage Game
                                </NavLink>
                            </div>

                            {/* <div>
                                <label onClick={() => this.showNestedMenu('game')}>Game
                            {this.state.showGameMenu ? <i className="fa fa-caret-up font-i-nestedbtn"></i> :
                                        <i className="fa fa-caret-down font-i-nestedbtn"></i>
                                    }
                                </label>
                                {this.state.showGameMenu ? <div className="nested-menu-item-div">
                                    <NavLink className="bm-item" activeClassName='is-active' to={{
                                        pathname: this.props.match.url + "/manageGame"
                                    }}>Manage Game
                                    </NavLink>
                                </div> : ''}
                            </div> */}
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' onClick={() => { this.logout() }}>Logout</NavLink>
                            </div>
                        </div> :
                        //member menu
                        <div>
                            <div >
                                <NavLink className="bm-item" activeClassName='is-active' >Home</NavLink>
                            </div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active'>Portfolio</NavLink>
                            </div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' to={{
                                    pathname: this.props.match.url + "/joinGame"
                                }} onClick={() => { this.closeMenuOnNavClick() }}>Join Game
                                        </NavLink>
                            </div>
                            {/* <div>
                                <label onClick={() => this.showNestedMenu('game')}>
                                    Game
                                    {
                                        this.state.showGameMenu ?
                                            <i className="fa fa-caret-up font-i-nestedbtn"></i> :
                                            <i className="fa fa-caret-down font-i-nestedbtn"></i>
                                    }
                                </label>
                                {
                                    this.state.showGameMenu ?
                                        <div className="nested-menu-item-div">
                                            <NavLink className="bm-item" activeClassName='is-active' to={{
                                                pathname: this.props.match.url + "/joinGame"
                                            }} onClick={() => { this.closeMenuOnNavClick() }}>Join Game
                                        </NavLink>
                                        </div> : ''
                                }
                            </div> */}
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' to={{
                                    pathname: this.props.match.url + "/orderEntry"
                                }} onClick={() => { this.closeMenuOnNavClick() }}>Trade</NavLink>
                            </div>
                            <div>
                                <NavLink className="bm-item" activeClassName='is-active' onClick={() => { this.logout() }}>Logout</NavLink>
                            </div>
                        </div>
                }
            </Menu>
        )
    }
}
export default MenuLinks;