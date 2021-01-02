import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Snackbar from '@material-ui/core/Snackbar';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';
import { connect } from 'react-redux';
import * as actiontypes from '../../store/actions/actionIndex';
import './showSnackBarComponent.scss';
import SnackbarContent from '@material-ui/core/SnackbarContent';
import { red } from '@material-ui/core/colors';

const useStyles = makeStyles(theme => ({
    root: {
        backgroundColor:'red'
    },
    snackbar: {
      margin: theme.spacing(1),
    },
  }));
class ShowSnackbar extends React.Component {
    processQueue = () => {
        if (this.props.latestNewsList.length > 0) {
            this.props.showNewsSnackBar({
                msg: this.props.latestNewsList.shift(), duration: 10000, direction: {
                    vertical: 'top',
                    horizontal: 'center',
                }
            })
        }
    };

    closeSnackBar = () => {
        this.props.onCloseSnackBar();
    }

    componentDidUpdate(prevProps) {
        if (prevProps['latestNewsList'] !== this.props['latestNewsList']) {
            this.processQueue();
        }
    }

    handleExited = () => {
        this.processQueue();
    };

    render() {
        return (
            <div>
                <Snackbar className='Snackbar'
                    key={this.props.snackBarInfo['msg']}
                    onExited={this.handleExited}
                    anchorOrigin={{ ...this.props.snackBarInfo['direction'] }}
                    open={this.props.snackBarInfo['open']}
                    autoHideDuration={this.props.snackBarInfo['duration']}
                    onClose={this.closeSnackBar}
                    ContentProps={{
                        classes: {
                            root: 'msg-span'
                        },
                        'aria-describedby': 'message-id',
                    }}
                >
                    <SnackbarContent 
                        message={<span id="message-id" >{this.props.snackBarInfo['msg']}</span>}
                        // action={[
                        //     <IconButton key="close" aria-label="close" color="inherit" onClick={this.closeSnackBar} >
                        //         <CloseIcon />
                        //     </IconButton>,
                        // ]}
                        >
                       
                    </SnackbarContent>
                </Snackbar>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        snackBarInfo: state.requestStatusReducer['snackBarInfo'],
        newsList: state.fetchDataReducer.newsFeed,
        latestNewsList: state.orderListReducer['ordersToShow']['latestNewsFeed'],
        playbackOrdersFlow: state.orderListReducer['ordersToShow']['playbackOrdersFlow']
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        onCloseSnackBar: () => {
            dispatch(actiontypes.CloseSnackbar());
        },
        showNewsSnackBar: (data) => {
            dispatch(actiontypes.ShowSnackbar(data));
        }
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(ShowSnackbar)