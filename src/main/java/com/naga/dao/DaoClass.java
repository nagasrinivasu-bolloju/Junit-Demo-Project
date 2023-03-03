package com.naga.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.naga.model.Customer;

//Table name is customer2 in postgresql.
public class DaoClass
{
	private final String url="jdbc:postgresql://localhost:5432/DemoDB";
	private final String username="postgres";
	private final String password="srinu534";
	private Connection connection =null;
	private Statement statement=null;
	private PreparedStatement prepStatementCust=null;
	private PreparedStatement prepStatementUpdate=null;

	public DaoClass()
	{
		super();
	}
	public DaoClass(PreparedStatement prep,PreparedStatement prepUpdate,Statement stat)
	{
		super();
		this.prepStatementCust=prep;
		this.statement=stat;
		this.prepStatementUpdate=prepUpdate;
//		boolean t1=connectTemperaryDataBase();
//		boolean t2=createStatement();
//		if(!t1)
//			System.out.println("db not connected.");
//		if(!t2)
//			System.out.println("statement not created.");
	}
	
	public boolean connect() throws SQLException
	{
		return connectDataBase() && createStatement();
	}
	
	//connect to in memory database.
	private boolean connectTemperaryDataBase()
	{
		//String url="jdbc:h2:tcp://localhost/~/test";
		String url="jdbc:h2:mem:test";
		String username="sa";
		String password="";
		try
		{
			Class.forName("org.h2.Driver");
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null)
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	private boolean connectDataBase()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
			connection=DriverManager.getConnection(url,username,password);
			if(connection!=null)
				return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	private boolean createStatement()
	{
		try
		{
			statement = connection.createStatement();
			String sql1="insert into customer2(cus_id,name) values(?,?);";
			String sql2="update customer2 set name=? where cus_id=?";
			prepStatementCust=connection.prepareStatement(sql1);
			prepStatementUpdate=connection.prepareStatement(sql2);
			
//			String tableCreation="create table customer2(cus_id int primary key,name varchar(30));";
//			statement.executeQuery(tableCreation);
			
			if(statement!=null && prepStatementCust!=null && prepStatementUpdate!=null)
			{
				System.out.println("Statements created successfully.");
				return true;
			}
			else
			{
				System.out.println("Statements  creation failed.");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Customer readData(int cus_id) {
		Customer customer=null;
		System.out.println("reading employee record with empId: "+cus_id);
		try
    	{
	    	ResultSet rs=statement.executeQuery("select * from customer2 where cus_id="+cus_id+";");
	    	if(rs!=null && rs.next())
	    	{
	    		customer=new Customer();
	    		customer.setCus_id(rs.getInt(1));
	    		customer.setName(rs.getString(2));
	    	}
	    	else
	    		System.out.println("Inside readData!!!!.............emp is nullll");
	    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return customer;
	}
	
	
	public int insert(Customer cust)
	{
		int rows=0;
		try
		{
			prepStatementCust.setInt(1,cust.getCus_id());
			prepStatementCust.setString(2,cust.getName());
			rows=prepStatementCust.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(rows<=0)
			System.out.println("insertion failed at employee table.");
		else
			System.out.println("insertion successfull at employee table.");
		return rows;
	}
	public int delete(int cus_id)
	{
		int rows=0;
		try
    	{
    		rows=statement.executeUpdate("delete from customer2 where cus_id="+cus_id);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		return rows;
	}
	public int update(Customer cust)
	{
		int rows=0;
		try
		{
			prepStatementUpdate.setString(1,cust.getName());
			prepStatementUpdate.setInt(2,cust.getCus_id());
			rows=prepStatementUpdate.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return rows;
	}
	
	
	 
	
	
}
