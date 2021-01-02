export const convertToDateTime = (val) => {
    if (val) {
        const date = new Date(val);
        const dd = date.getDate();
        const yy = date.getFullYear();
        const mm = date.getMonth();
        const hh = date.getHours();
        const min = date.getMinutes();
        const sec = date.getSeconds();
        return dd + '/' + mm + '/' + yy + '  ' + hh + ':' + min;
    }
}

export const onlyPositiveNumber = (evt) => {
    evt = (evt) ? evt : window.event;
    const charCode = (evt.which) ? evt.which : evt.keyCode;
    const isDecimalAlreadyExist = evt && evt.target && evt.target.value ? evt.target.value.includes('.') : false;
    if (!isDecimalAlreadyExist && charCode === 46) {
        return true;
    }
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

export const convertTimeToDecimal = (val) => {
    if (val && val.indexOf(':') > -1) {
        val = val.split(':');
        val = parseFloat(parseInt(val[0], 10) + parseInt(val[1], 10) / 60 + parseInt(val[2], 10) / 3600);
        return Math.round(val * 1000) / 1000;
    } else {
        const v = parseInt(val, 10);
        return Math.round(v * 1000) / 1000;
    }
}