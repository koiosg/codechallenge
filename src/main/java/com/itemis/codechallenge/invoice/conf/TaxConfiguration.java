package com.itemis.codechallenge.invoice.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.itemis.codechallenge.invoice.entity.Good;
import com.itemis.codechallenge.invoice.entity.GoodCategory;
import com.itemis.codechallenge.invoice.entity.TaxCategory;
import com.itemis.codechallenge.invoice.entity.TaxGroup;

@ApplicationScoped
public class TaxConfiguration {

	public List<TaxGroup> getTaxGroups() throws JsonParseException, JsonMappingException, IOException {
		// read json file data to String
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("staticdata/taxgroups.json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			final List<TaxGroup> taxGroups = Arrays.asList(objectMapper.readValue(inputStream, TaxGroup[].class));
			return taxGroups;
		}
	}
	
	public List<TaxCategory> getTaxCategories() throws JsonParseException, JsonMappingException, IOException {
		// read json file data to String
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("staticdata/taxcategories.json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			final List<TaxCategory> taxCategories = Arrays.asList(objectMapper.readValue(inputStream, TaxCategory[].class));
			return taxCategories;
		}
	}
	
	public List<GoodCategory> getGoodsCategories() throws JsonParseException, JsonMappingException, IOException {
		// read json file data to String
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("staticdata/goodscategories.json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			final List<GoodCategory> goodCategories = Arrays.asList(objectMapper.readValue(inputStream, GoodCategory[].class));
			return goodCategories;
		}
	}
	
	public List<Good> getGoods() throws JsonParseException, JsonMappingException, IOException {
		// read json file data to String
		try (InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("staticdata/goods.json")) {
			ObjectMapper objectMapper = new ObjectMapper();
			final List<Good> goods = Arrays.asList(objectMapper.readValue(inputStream, Good[].class));
			return goods;
		}
	}
}