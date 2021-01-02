import * as ActionTypes from '../actions/actionTypes';

const initialState = {
    bookOrderFormValue: {
        stockSymbol: 1,
        transaction: 'Bid',
        orderType: '',
        price: '',
        quantity: ''
    },
    bookOrderFormError: {
        priceInvalid: false,
        quantityInvalid: false
    },
}

const BookNewOrderReducer = (state = initialState, action) => {

    switch (action.type) {
        case ActionTypes.Update_BookOrderForm_Values:
            const updatedOrderFormValue = { ...state.bookOrderFormValue, ...action.element };
            return {
                ...state, bookOrderFormValue: updatedOrderFormValue
            }

        case ActionTypes.Clear_BookOrderForm_Values:
            const clearData = { ...state['bookOrderFormValue'] }
            clearData['transaction'] = 'Bid';
            clearData['orderType'] = ''
            clearData['price'] = '';
            clearData['quantity'] = ''
            return {
                ...state, bookOrderFormValue: clearData
            }
        case ActionTypes.Set_BookOrder_ValidityState:
            const bookOrderFormValidity = { ...state.bookOrderFormError }
            if (action.fieldName === 'price') {
                bookOrderFormValidity.priceInvalid = action.value;
            } else if (action.fieldName === 'quantity') {
                bookOrderFormValidity.quantityInvalid = action.value;
            }
            return {
                ...state,
                bookOrderFormError: { ...state.bookOrderFormError, ...bookOrderFormValidity }
            }
        default:
            return state;
    }
}

export default BookNewOrderReducer;