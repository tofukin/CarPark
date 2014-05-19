/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 22/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

/**
 * @author hogan
 *
 */
public class CarTests {
	
	private String CarID = "C0";
	private String MotorCycleID = "M0";
	private int arrivalTime = 1;
	private int zeroArrivalTime = 0;
	private int negativeArrivalTime = -1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#Car(java.lang.String, int, boolean)}.
	 */
	@Test
	public void testCar() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsSmall() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, true);
		assertTrue(car.isSmall());
		//fail("Not yet implemented"); // TODO
	}
	
	@Test
	public void testNotSmall() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		assertFalse(car.isSmall());
	}
	
	@Test (expected = VehicleException.class)
	public void testCarNegativeArrival() throws VehicleException {
		Car car = new Car(CarID, negativeArrivalTime, false);
	}
	
	@Test (expected = VehicleException.class)
	public void testCarZeroArrival() throws VehicleException {
		Car car = new Car(CarID, zeroArrivalTime, false);
	}

}
