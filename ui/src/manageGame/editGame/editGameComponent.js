import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import DialogTitle from '@material-ui/core/DialogTitle';
import Dialog from '@material-ui/core/Dialog';
import IconButton from '@material-ui/core/IconButton';
import CloseIcon from '@material-ui/icons/Close';


class EditGameComponent extends React.Component {

    render() {
        return (
            <div>
                <Dialog onClose={this.props.editDialogClose} aria-labelledby="simple-dialog-title"
                    open={this.props.editDialogopen}>
                    <DialogTitle id="simple-dialog-title" onClose={this.props.editDialogClose}>Edit Game
                    <IconButton aria-label="close"  onClose={this.props.editDialogClose} onClick={this.props.editDialogClose}>
                            <CloseIcon />
                        </IconButton>
                    </DialogTitle>
                </Dialog>
            </div>
        );
    }
}

export default EditGameComponent;

