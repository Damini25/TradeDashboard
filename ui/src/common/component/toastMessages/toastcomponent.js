import React from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './toastComponent.css';

export const MessageContainer = (position) => {
    return (
        <ToastContainer position={toast.POSITION.TOP_CENTER}
            autoClose={4000} hideProgressBar={true}></ToastContainer>
    )
}

export const showToast = (type, msg,toastId) => {
    switch (type) {
        case 'success':
            return toast.success(msg, {
                className: 'msgDiv',
                // bodyClassName: "grow-font-size",
                // progressClassName: 'fancy-progress-bar'
            });
        case 'error':
            // if (!toast.isActive(toastId)) {
            //     return toast.error(msg, {
            //         className: 'error-toast'
            //     });
            // }
            return  toast.error(msg, {
                className: 'error-toast',
                id:toastId
            });
        case 'info':
            return toast.info(msg);
        default:
            return toast.success(msg);
    }
}