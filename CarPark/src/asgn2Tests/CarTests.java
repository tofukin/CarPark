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

import org.junit.Test;

import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

/**
 * @author hogan
 *
 */
public class CarTests {
	
	private String CarID = "C0";
	private int arrivalTime = 1;
	private int zeroArrivalTime = 0;
	private int negativeArrivalTime = -1;


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
	
	@Test
	public void testCarPositiveArrival() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
	}
	
	@Test
	public void testCarVehID() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		assertTrue(car.getVehID() == CarID);
	}

}
