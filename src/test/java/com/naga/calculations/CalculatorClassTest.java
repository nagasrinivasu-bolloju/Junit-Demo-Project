package com.naga.calculations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
//@TestInstance(Lifecycle.PER_METHOD) default.
//@TestInstance(Lifecycle.PER_CLASS)
@DisplayName("Running Calculator ")
class CalculatorClassTest {
	static int temp=10;
	
	@BeforeAll
	public static void classLevelSetupBeforeAllTests()
	{
		System.out.println("zerodivision 20/0.0: for float values:"+(20/0.0));
		System.out.println("Before All Methods!..temp val="+temp);
	}
	
	@AfterAll
	public static void classLevelCleanUpAfterAllTests()
	{
		System.out.println("After All Methods!..temp val="+temp);
	}
	
	@BeforeEach
	public void MethodLevelSetUpBeforeEachTest()
	{
		System.out.println("Before TestMethod..temp val="+temp);
	}
	
	@AfterEach
	public void MethodLevelCleanUpAfterEachTest()
	{
		System.out.println("After TestMethod.. temp val="+temp);
	}
	
	
	@Tag("Division")
	@Test
	@DisplayName("Performing divide operation ")
	void testDivide()
	{
		System.out.println("-----divide Test starting-----");
		CalculatorClass calcObject=new CalculatorClass();
		assertEquals(5,calcObject.divide(20,4));
		System.out.println("-----divide Test middle-----");
		assertEquals(2.5,calcObject.divide(10,4));
		assertEquals(50,calcObject.divide(200,4));
//		boolean value=false;
//		assumeTrue(value);
//		assertThrows(ArithmeticException.class,()->calcObject.divide(20,0),"can't divide with 0");
		System.out.println("-----divide Test executed-----");
		//temp=1000;
		assertAll(
				()->assertEquals(5,calcObject.divide(20,4)),
				()->assertEquals(50,calcObject.divide(200,4)),
				()->assertEquals(2.5,calcObject.divide(10,4))
				);
	}
	
	
	@Tag("Addition")
	@Nested
	class AdditionTests
	{
	
		@Test
		@DisplayName("Performing Addition Operation ")
		void test1()
		{
			CalculatorClass calcObject=new CalculatorClass();
			assertEquals(10, calcObject.add(5,5),()->"this method should add two given numbers");
			temp=30;
			System.out.println("-----add Test executed------");
		}
		
		@Test
		@DisplayName("Performing Addition Operation ")
		void test2()
		{
			CalculatorClass calcObject=new CalculatorClass();
			assertEquals(13, calcObject.add(8,5),()->"this method should add two given numbers");
			temp=30;
			System.out.println("-----add Test executed------");
		}
	}

//	@Rule public MockitoRule rule=MockitoJUnit.rule();
//	
//	@Mock     Not working i.e not creating dummy object using these annotations.
	CalculatorService service=Mockito.mock(CalculatorService.class);
	
	
	@Test
	void testForComputeEquation()
	{
		CalculatorClass calcObject=new CalculatorClass(service);
		when(service.computeResult(2,3)).thenReturn(7);
		assertEquals(7,calcObject.getEquationResult(2,3));
		assertEquals(0,calcObject.getValue(2,3));
		verify(service).computeResult(2,3);
	}
}
