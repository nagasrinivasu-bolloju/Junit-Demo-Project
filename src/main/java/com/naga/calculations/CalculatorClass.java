package com.naga.calculations;

public class CalculatorClass
{
	
	CalculatorService service;
	public CalculatorClass(CalculatorService service) {
		super();
		this.service = service;
	}
	public CalculatorClass() {
		super();
	
	}

	
	public int add(int a,int b)
	{
		return a+b;
	}
	
	public double divide(int a,int b)
	{
		return (float)a/b;
	}
	
	public int getEquationResult(int a,int b)
	{
		return service.computeResult(a,b); //may return a^2+b
		//return (a*a)+b;
	}
	public double getValue(int a,int b)
	{
		tempFun();
		return service.getValue(a,b);
		
	}
	public void  tempFun()
	{
		System.out.println("executed tempFun method");
	}
}
