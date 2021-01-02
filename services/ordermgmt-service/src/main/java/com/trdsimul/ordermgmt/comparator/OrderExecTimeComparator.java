package com.trdsimul.ordermgmt.comparator;

import java.util.Comparator;

import com.trdsimul.ordermgmt.model.entity.ExecutedOrdersDetails;

public class OrderExecTimeComparator implements Comparator<ExecutedOrdersDetails> {

	@Override
	public int compare(ExecutedOrdersDetails o1, ExecutedOrdersDetails o2) {
		if (o1.getOrderExecutionTime().compareTo(o2.getOrderExecutionTime()) > 0) {
			return -1;
		} else if (o1.getOrderExecutionTime().compareTo(o2.getOrderExecutionTime()) < 0){
			return 1;
		} else {
			return 0;
		}
	}

}
