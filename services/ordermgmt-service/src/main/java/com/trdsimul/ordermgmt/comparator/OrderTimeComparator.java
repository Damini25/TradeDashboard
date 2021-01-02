package com.trdsimul.ordermgmt.comparator;

import java.util.Comparator;

import com.trdsimul.ordermgmt.model.entity.OrderDetails;

public class OrderTimeComparator implements Comparator<OrderDetails> {

	@Override
	public int compare(OrderDetails o1, OrderDetails o2) {
		if (o1.getOrderTime().compareTo(o2.getOrderTime()) > 0) {
			return -1;
		} else if (o1.getOrderTime().compareTo(o2.getOrderTime()) < 0){
			return 1;
		} else {
			return 0;
		}
	}

}
