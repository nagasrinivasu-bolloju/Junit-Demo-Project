package com.naga.dao;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.naga.dao.DaoClassTest.OrderAInsertTests;
import com.naga.dao.DaoClassTest.OrderBReadTests;
import com.naga.dao.DaoClassTest.OrderCUpdateTests;
import com.naga.dao.DaoClassTest.OrderDDeletionTests;

@Suite
@SelectClasses({ DaoClassTest.class, OrderAInsertTests.class, OrderBReadTests.class, OrderCUpdateTests.class,
		OrderDDeletionTests.class })
public class AllTests {

}
