import React from 'react';
import './newsFeedComponent.scss';
import { connect } from 'react-redux';
import Ticker from 'react-ticker';
import * as actiontypes from '../../../common/store/actions/actionIndex';

class NewsFeed extends React.Component {

    componentDidMount() {
        // this.props.loadNewsList();
    }

    showNews(list) {
        this.props.showNewsSnackBar({
            msg: list.join('  ~  '),
            open: true,
            duration: 3000,
            direction: {
                vertical: 'top',
                horizontal: 'right',
            }
        })
    }

    render() {
        if (this.props.newsFeed && this.props.newsFeed.length) {

            console.log('news', this.props.newsFeed);
        }

        /* const news = this.props.newsList && this.props.newsList.length ?
             this.props.newsList.map((elem, index) => {
                 //  this.props.showNewsSnackBar({msg:elem,open:true,duration:3000})
             }
             ) : '';*/
        return (
            <div className="show-news-div">
                <label onClick={() => this.showNews(this.props.newsFeed)}>
                    <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                </label>
            </div>


            // <div>
            //     <Ticker>{
            //         () => (
            //             this.props.newsList && this.props.newsList.length ?
            //                 <p className="news-list">{this.props.newsList.join('  ~  ')}</p> :
            //                 <h5 className="news-else-placeholder">Not forun</h5>)
            //     }</Ticker>
            // </div>
        );
    }
}
const mapDispatchToProps = (dispatch) => {
    return {
        loadNewsList: () => {
            dispatch(actiontypes.LoadNewsList());
        },
        showNewsSnackBar: (data) => {
            dispatch(actiontypes.ShowSnackbar(data));
        }
    }
}
const mapStateToProps = (state) => {
    return {
        newsFeed: state.fetchDataReducer.newsFeed
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(NewsFeed);