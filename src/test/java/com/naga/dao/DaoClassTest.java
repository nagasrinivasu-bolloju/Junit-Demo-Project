package com.naga.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.naga.model.Customer;

//@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class DaoClassTest {

	//using Mockito
	//------------------------------------------------------
	@Mock
	PreparedStatement prepStatementCustomer;     //=Mockito.mock(PreparedStatement.class);
	PreparedStatement prepStatementUpdate=Mockito.mock(PreparedStatement.class);
	Statement statement=Mockito.mock(Statement.class);
	ResultSet rs=Mockito.mock(ResultSet.class);
	DaoClass dao;
	
	
	
	
	@BeforeEach
	public void beforeTest() throws SQLException
	{
		dao=new DaoClass(prepStatementCustomer,prepStatementUpdate,statement);
	}
	
	@Nested
	@DisplayName("executing insertion tests ")
	@Tag("InsertTests")
	class InsertTests
	{
		@Test
		public void insertCustomerTest() throws SQLException
		{
			when(prepStatementCustomer.executeUpdate()).thenReturn(1);
			assertEquals(1,dao.insert(new Customer(101,"naga")));
			verify(prepStatementCustomer).executeUpdate();
		}
	}
	
	@Nested
	@DisplayName("executing read tests ")
	@Tag("ReadTests")
	class ReadTests
	{
		@Test
		public void readCustomerTest1() throws SQLException
		{	
			Customer cust1=new Customer(101,"naga");
			when(statement.executeQuery("select * from customer2 where cus_id="+101+";")).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getInt(1)).thenReturn(cust1.getCus_id());
			when(rs.getString(2)).thenReturn(cust1.getName());
			
			Customer cust=dao.readData(101);
			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
			assertAll(
			()->assertEquals(101,cust.getCus_id()),
			()->assertEquals("naga",cust.getName())
			);
			verify(statement).executeQuery("select * from customer2 where cus_id=101;");
		}
		
		@Test
		public void readCustomerTest2() throws SQLException
		{	
			when(statement.executeQuery("select * from customer2 where cus_id=102;")).thenReturn(rs);
			when(rs.next()).thenReturn(true);
			when(rs.getInt(1)).thenReturn(102);
			when(rs.getString(2)).thenReturn("sravan");
			
			Customer cust=dao.readData(102);
			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
			assertAll(
			()->assertEquals(102,cust.getCus_id()),
			()->assertEquals("sravan",cust.getName())
			);
			verify(statement).executeQuery("select * from customer2 where cus_id=102;");
		}
	}
	
	@Nested
	@DisplayName("executing update tests ")
	@Tag("UpdateTests")
	class UpdateTests
	{
		@Test
		public void updateCustomerTest1() throws SQLException
		{
			when(prepStatementUpdate.executeUpdate()).thenReturn(1);
			assertEquals(1,dao.update(new Customer(102,"sravan kumar")));
			verify(prepStatementUpdate).executeUpdate();
		}
		
		@Test
		public void updateCustomerTest2() throws SQLException
		{
			when(prepStatementUpdate.executeUpdate()).thenReturn(1);
			assertEquals(1,dao.update(new Customer(103,"Akhil")));
		}
	}

	@Nested
	@DisplayName("executing deletion tests ")
	@Tag("DeletionTests")
	class DeletionTests
	{
		@Test
		public void deleteCustomerTest() throws SQLException
		{
			when(statement.executeUpdate("delete from customer2 where cus_id=102")).thenReturn(1);
			assertEquals(1,dao.delete(102));
			verify(statement).executeUpdate("delete from customer2 where cus_id=102");
		}
	}
	
	
	//using Map datastructure
	//---------------------------------------------------------------------------
	
//	private static Map<Integer,Customer> custRecords=new HashMap<>();
//	
//	@Mock
//	PreparedStatement prepStatementCustomer;
//	PreparedStatement prepStatementUpdate=Mockito.mock(PreparedStatement.class);
//	Statement statement=Mockito.mock(Statement.class);
//	ResultSet rs=Mockito.mock(ResultSet.class);
//	DaoClass dao;
//	@BeforeAll
//	public static void beforeAllTests()
//	{
//		custRecords.put(102,new Customer(102,"sravan"));
//		custRecords.put(103,new Customer(103,"nagesh"));
//	}
//	@BeforeEach
//	public void beforeTest() throws SQLException
//	{
//		dao=new DaoClass(prepStatementCustomer,prepStatementUpdate,statement);
//	}
//	
//	@Nested
//	@Tag("InsertTests")
//	@DisplayName("executing insertion tests")
//	class OrderAInsertTests
//	{
//		public int insertIntoMap(int cus_id,String name)
//		{
//			custRecords.put(cus_id,new Customer(cus_id,name));
//			return 1;
//		}
//		
//		@Test
//		public void insertCustomerTest() throws SQLException
//		{
//			when(prepStatementCustomer.executeUpdate()).thenReturn(insertIntoMap(101,"naga"));
//			assertEquals(1,dao.insert(new Customer(101,"naga")));
//			verify(prepStatementCustomer).executeUpdate();
//		}
//	}
//	
//	@Nested
//	@Tag("ReadTests")
//	@DisplayName("executing read tests")
//	class OrderBReadTests
//	{
//		@Test
//		public void readCustomerTest1() throws SQLException
//		{	
//			when(statement.executeQuery("select * from customer2 where cus_id="+101+";")).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getInt(1)).thenReturn(101);
//			when(rs.getString(2)).thenReturn(custRecords.get(101).getName());
//			
//			Customer cust=dao.readData(101);
//			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
//			assertAll(
//			()->assertEquals(101,cust.getCus_id()),
//			()->assertEquals("naga",cust.getName())
//			);
//			verify(statement).executeQuery("select * from customer2 where cus_id=101;");
//		}
//		
//		@Test
//		public void readCustomerTest2() throws SQLException
//		{	
//			when(statement.executeQuery("select * from customer2 where cus_id=102;")).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getInt(1)).thenReturn(102);
//			when(rs.getString(2)).thenReturn(custRecords.get(102).getName());
//			
//			Customer cust=dao.readData(102);
//			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
//			assertAll(
//			()->assertEquals(102,cust.getCus_id()),
//			()->assertEquals("sravan",cust.getName())
//			);
//			verify(statement).executeQuery("select * from customer2 where cus_id=102;");
//		}
//	}
//	
//	@Nested
//	@Tag("UpdateTests")
//	@DisplayName("executing updation tests")
//	class OrderCUpdateTests
//	{
//		public int updateMap(int cus_id,String name)
//		{
//			if(custRecords.containsKey(cus_id))
//				custRecords.get(cus_id).setName(name);
//			else
//				custRecords.put(cus_id,new Customer(cus_id,name));
//			return 1;
//		}
//		@Test
//		public void updateCustomerTest1() throws SQLException
//		{
//			when(prepStatementUpdate.executeUpdate()).thenReturn(updateMap(102,"sravan kumar"));
//			assertEquals(1,dao.update(new Customer(102,"sravan kumar")));
//			verify(prepStatementUpdate).executeUpdate();
//		}
//		
//		@Test
//		public void updateCustomerTest2() throws SQLException
//		{
//			when(prepStatementUpdate.executeUpdate()).thenReturn(updateMap(103,"sravan kumar"));
//			assertEquals(1,dao.update(new Customer(103,"Akhil")));
//			verify(prepStatementUpdate).executeUpdate();
//		}
//	}
//	
//	@Nested
//	@Tag("DeletionTests")
//	@DisplayName("executing deletion tests")
//	class OrderDDeletionTests
//	{
//		public int deleteFromMap(int cus_id)
//		{
//			if(custRecords.containsKey(cus_id))
//			{
//				custRecords.remove(cus_id);
//				return 1;
//			}
//			return 0;
//		}
//		
//		@Test
//		public void deleteCustomerTest() throws SQLException
//		{
//			when(statement.executeUpdate("delete from customer2 where cus_id=102")).thenReturn(deleteFromMap(102));
//			verify(statement).executeUpdate("delete from customer2 where cus_id=102");
//			assertEquals(1,dao.delete(102));
//			
//		}
//	}
//	@Test
//	public void getRecords()
//	{
//		Iterator<Entry<Integer,Customer>> itr =  custRecords.entrySet().iterator();
//		if(!itr.hasNext())
//			System.out.println("map is empty");
//		while(itr.hasNext())
//		{
//		  Map.Entry<Integer,Customer> entry = itr.next();
//	      System.out.println("Key is " + entry.getKey() + " Value is [" + entry.getValue().getCus_id()+","+entry.getValue().getName()+"]");
//	    }
//	}
	
	/*
	//using inmemory database
	//------------------------------------------------------------------------------
	PreparedStatement prepStatementCustomer;
	PreparedStatement prepStatementUpdate;
	Statement statement;
	ResultSet rs;
	DaoClass dao;
	
	
	
	
	@BeforeEach
	public void beforeTest() throws SQLException
	{
		dao=new DaoClass(prepStatementCustomer,prepStatementUpdate,statement);
	}
	
	@Nested
	@DisplayName("executing insertion tests ")
	@Tag("InsertTests")
	class InsertTests
	{
		@Test
		public void insertCustomerTest() throws SQLException
		{
//			when(prepStatementCustomer.executeUpdate()).thenReturn(1);
			assertEquals(1,dao.insert(new Customer(101,"naga")));
//			verify(prepStatementCustomer).executeUpdate();
		}
	}
	*/
//	@Nested
//	@DisplayName("executing read tests ")
//	@Tag("ReadTests")
//	class ReadTests
//	{
//		@Test
//		public void readCustomerTest1() throws SQLException
//		{	
//			when(statement.executeQuery("select * from customer2 where cus_id="+101+";")).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getInt(1)).thenReturn(101);
//			when(rs.getString(2)).thenReturn("naga");
//			
//			Customer cust=dao.readData(101);
//			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
//			assertAll(
//			()->assertEquals(101,cust.getCus_id()),
//			()->assertEquals("naga",cust.getName())
//			);
//			verify(statement).executeQuery("select * from customer2 where cus_id=101;");
//		}
//		
//		@Test
//		public void readCustomerTest2() throws SQLException
//		{	
//			when(statement.executeQuery("select * from customer2 where cus_id=102;")).thenReturn(rs);
//			when(rs.next()).thenReturn(true);
//			when(rs.getInt(1)).thenReturn(102);
//			when(rs.getString(2)).thenReturn("sravan");
//			
//			Customer cust=dao.readData(102);
//			System.out.println("id:"+cust.getCus_id()+"   name:"+cust.getName());
//			assertAll(
//			()->assertEquals(102,cust.getCus_id()),
//			()->assertEquals("sravan",cust.getName())
//			);
//			verify(statement).executeQuery("select * from customer2 where cus_id=102;");
//		}
//	}
//	
//	@Nested
//	@DisplayName("executing update tests ")
//	@Tag("UpdateTests")
//	class UpdateTests
//	{
//		@Test
//		public void updateCustomerTest1() throws SQLException
//		{
//			when(prepStatementUpdate.executeUpdate()).thenReturn(1);
//			assertEquals(1,dao.update(new Customer(102,"sravan kumar")));
//			verify(prepStatementUpdate).executeUpdate();
//			
////			assertEquals(1,dao.update(new Customer(102,"sravan kumar")));
//		}
//		
//		@Test
//		public void updateCustomerTest2() throws SQLException
//		{
//			when(prepStatementUpdate.executeUpdate()).thenReturn(1);
//			assertEquals(1,dao.update(new Customer(103,"Akhil")));
//			
////			assertEquals(1,dao.update(new Customer(102,"Akhil")));
//		}
//	}
//
//	@Nested
//	@DisplayName("executing deletion tests ")
//	@Tag("DeletionTests")
//	class DeletionTests
//	{
//		@Test
//		public void deleteCustomerTest() throws SQLException
//		{
//			when(statement.executeUpdate("delete from customer2 where cus_id=102")).thenReturn(1);
//			assertEquals(1,dao.delete(102));
//			verify(statement).executeUpdate("delete from customer2 where cus_id=102");
//		}
//	}
}
