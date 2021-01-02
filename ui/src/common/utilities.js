export const convertToDateTime = (val) => {
    if (val) {
        const date = new Date(val);
        const dd = date.getDate();
        const yy = date.getFullYear();
        const mm = date.getMonth();
        const hh = date.getHours();
        const min = date.getMinutes();
        const sec = date.getSeconds();
        return dd + '/' + mm + '/' + yy+ '  '+ hh +':'+ min;
    }
}
