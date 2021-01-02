import React from 'react';
import './login.scss';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import * as actiontypes from '../common/store/actions/actionIndex';
import { validateField } from '../common/component/validation/validationComponent';
import { getLocalStorage, clearLocalStorage } from '../common/localStorageService';

class Logincomponent extends React.Component {

    componentDidMount() {
        clearLocalStorage();
    }

    login(e) {
        /** API call */
        console.log('vv', this.props.loginFormValues);
        const payload = {
            username: this.props.loginFormValues['name'],
            password: this.props.loginFormValues['password']
        }
      //  this.props.history.push("/mainNav/manageGame");
        this.props.onLogin(payload)
        e.preventDefault();
    }

    // componentDidUpdate(prevProps) {
    //     if (prevProps.loginFormValues['traderId'] !== this.props.loginFormValues['traderId'] && getLocalStorage('userTypeId')) {
    //         if( parseInt(getLocalStorage('userTypeId')) === 0){
    //             this.props.history.push("/mainNav/manageGame");
    //         }else{
    //             this.props.history.push("/mainNav/joinGame");
    //         }
    //     }
    // }

    handleChange = (event) => {
        if (!validateField(event.target.name, event.target.value)) {
            this.props.onValidatingFormValues({
                fieldName: event.target.name,
                formError: true
            })
        } else {
            this.props.onValidatingFormValues({
                fieldName: event.target.name,
                formError: false
            })
        }
        this.props.onSettingUserDetails({ [event.target.name]: event.target.value });

        // if (event.target.name === 'name') {
        //     this.props.onSettingUserDetails({ 'name': event.target.value });
        // }
    }

    render() {
        return (
            <div className="parent-login-div">
                <h1>Account Login</h1>
                <hr></hr>
                <form onSubmit={(e) => { this.login(e) }}>
                    <div className="input-div">
                        <div >
                            <label htmlFor="name">Username</label>
                            <input type="text" autoComplete="off" name="name" onChange={(e) => { this.handleChange(e) }} />
                        </div>
                        <div>
                            <label htmlFor="password">Password</label>
                            <input type="password" autoComplete="off"
                                name="password" onChange={(e) => { this.handleChange(e) }} />
                            {/* {this.props.loginFormError['passwordInvalid'] ?
                                <span className="error-span">Password incorrect</span> : ''} */}
                        </div>
                        <div className="input-div-checkbox">
                            <input type="checkbox" id="remember_me_check" />
                            <label htmlFor="remember_me_check">Remember Me</label>
                        </div>
                        <button className="signin-btn primary-color button" type="submit">Sign in</button>
                        <div className="link-div">
                            <Link to="/signup">Create new account</Link> <span>|</span>
                            <Link to="/forgotPassword">Forgot your password?</Link>
                        </div>
                    </div>
                </form>

            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        loginFormError: state.fetchDataReducer.loginFormError,
        loginFormValues: state.fetchDataReducer.userDetails
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        onSettingUserDetails: (data) => {
            dispatch(actiontypes.SetUserDetails(data))
        },
        onValidatingFormValues: (data) => {
            dispatch(actiontypes.SetLoginFormValidity(data))
        },
        onLogin: (payload) => {
            dispatch(actiontypes.CallLoginApi(payload))
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Logincomponent);