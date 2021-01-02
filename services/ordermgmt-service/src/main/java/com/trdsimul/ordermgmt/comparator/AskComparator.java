package com.trdsimul.ordermgmt.comparator;

import java.util.Comparator;

import com.trdsimul.ordermgmt.model.entity.OrderDetails;

/**
 * The Comparator Class for Ask orders.
 */
public class AskComparator implements Comparator<OrderDetails> {

	/**
	 * Compare Ask Offers based on price and order time. Arranges in ascending
	 * order.
	 *
	 * @param lhs the OrderDetails
	 * @param rhs the OrderDetails
	 * @return the comparison integer value
	 */
	@Override
	public int compare(OrderDetails lhs, OrderDetails rhs) {

		if (lhs.getPrice().compareTo(rhs.getPrice()) > 0) {
			return 1;
		} else if (lhs.getPrice().compareTo(rhs.getPrice()) == 0) {
			if (lhs.getOrderTime().compareTo(rhs.getOrderTime()) > 0 || lhs.getOrderTime().compareTo(rhs.getOrderTime()) == 0) {
				return 1;
			} else
				return -1;
		} else if (lhs.getPrice().compareTo(rhs.getPrice()) < 0) {
			return -1;
		} else
			return 0;
	}

}
