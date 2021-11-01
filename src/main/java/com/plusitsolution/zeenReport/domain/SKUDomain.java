package com.plusitsolution.zeenReport.domain;

import java.util.Map;

public class SKUDomain {
	
	private Map<String, Map<String, Integer>> skuCounts;

	public Map<String, Map<String, Integer>> getSkuCounts() {
		return skuCounts;
	}

	public void setSkuCounts(Map<String, Map<String, Integer>> skuCounts) {
		this.skuCounts = skuCounts;
	}
	
	public String toString() {
		return new StringBuilder().append("skuCounts:").append(skuCounts).toString();
	}
}
