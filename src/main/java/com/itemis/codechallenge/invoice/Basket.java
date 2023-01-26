package com.itemis.codechallenge.invoice;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.itemis.codechallenge.invoice.entity.Good;

public class Basket {
	
	@NotNull
	private List<Good> goods;
	
	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(final List<Good> goods) {
		this.goods = goods;
	}
}
