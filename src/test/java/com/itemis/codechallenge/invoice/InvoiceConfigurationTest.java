package com.itemis.codechallenge.invoice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;

import com.itemis.codechallenge.invoice.conf.TaxConfiguration;
import com.itemis.codechallenge.invoice.entity.Good;
import com.itemis.codechallenge.invoice.entity.GoodCategory;
import com.itemis.codechallenge.invoice.entity.TaxCategory;
import com.itemis.codechallenge.invoice.entity.TaxGroup;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceConfigurationTest {
	
    @Inject
    Validator validator;

    @Test
    public void testGoodCreationNOK() {
    	// creating a good
    	final Good good= new Good();
    	good.name = "eqwqtwqwefewfewfwfew";
    	good.goodCategory = "ABCDC";
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testGoodCreationOK() {
    	// creating a good
    	final Good good= new Good();
    	good.name = "eqwqtwqt";
    	good.goodCategory = "bookcategory";
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void taxGroupsTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<TaxGroup> taxGroups = TaxConfiguration.getTaxGroups();
        assertNotNull(taxGroups);
    }
    
    @Test
    public void taxCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<TaxCategory> taxCategories = TaxConfiguration.getTaxCategories();
        assertNotNull(taxCategories);
    }
    
    @Test
    public void goodsCategoriesTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<GoodCategory> goodCategories = TaxConfiguration.getGoodsCategories();
        assertNotNull(goodCategories);
    }
    
    @Test
    public void goodsTestOK() throws JsonParseException, JsonMappingException, IOException {
    	// creating a tax group
    	final List<Good> goods = TaxConfiguration.getGoods();
        assertNotNull(goods);
    }
}