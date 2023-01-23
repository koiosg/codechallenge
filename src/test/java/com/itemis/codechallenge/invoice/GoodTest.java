package com.itemis.codechallenge.invoice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GoodTest {
	
    @Inject
    Validator validator;

    @Test
    public void testGoodCreationNOK() {
    	// creating a hero
    	Good good= new Good();
    	good.name = "eqwqtwqwefewfewfwfew";
    	good.goodCategory = "ABCDC";
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testGoodCreationOK() {
    	// creating a hero
    	Good good= new Good();
    	good.name = "eqwqtwqt";
    	good.goodCategory = "bookcategory";
        Set<ConstraintViolation<Good>> violations = validator.validate(good);
        assertTrue(violations.isEmpty());
    }
}