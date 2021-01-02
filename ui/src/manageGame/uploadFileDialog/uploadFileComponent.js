import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';


class UploadFileComponent extends React.Component {

    render() {
        return (
            <div>
                <Dialog onClose={this.props.uploadFileDialogClose} aria-labelledby="simple-dialog-title"
                    open={this.props.uploadFileDialogOpen}>
                    <DialogTitle id="simple-dialog-title">Edit Game
                    <IconButton aria-label="close"  onClose={this.props.uploadFileDialogClose} onClick={this.props.uploadFileDialogClose}>
                            <CloseIcon />
                        </IconButton>
                    </DialogTitle>
                </Dialog>
            </div>
        );
    }
}

export default UploadFileComponent;

