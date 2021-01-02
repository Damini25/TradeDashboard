
export const validateField = (fieldName, value) => {

    switch (fieldName) {
        case 'email': {
            return value.match() ? true : false;
        }
        case 'password': {
            return value.length > 4 ? true : false;
        }
        case 'quantity': {
            return value.match(/^[+]?([0-9]+(?:[\.][0-9]*)?|\.[0-9]+)$/) ? true : false
        }
        case 'price': {
            return value.match(/^[+]?([0-9]+(?:[\.][0-9]*)?|\.[0-9]+)$/) ? true : false
        }
        default:
            return true;
    }
}


