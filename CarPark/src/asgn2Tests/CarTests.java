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

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;

/**
 * @author Kou Cheng Fan
 *
 */
public class CarTests {
	
	private String CarID = "C0";
	private int arrivalTime = 1;
	private int zeroArrivalTime = 0;
	private int negativeArrivalTime = -1;

	/**
	 * Test if archive departing vehicle is success when
	 * the vehicle has stayed its intended duration but not forced to depart.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * 
	 */

	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 * Return true if it is small car.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsSmall() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, true);
		assertTrue(car.isSmall());
	}
	
	/**
	 * Test method for {@link asgn2Vehicles.Car#isSmall()}.
	 * Return true if it is not small car.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testNotSmall() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		assertFalse(car.isSmall());
	}
	
	/**
	 * Throw VehicleException if it comes with negativeArrival time.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testCarNegativeArrival() throws VehicleException {
		Car car = new Car(CarID, negativeArrivalTime, false);
	}
	
	/**
	 * Throw VehicleException if it comes with Zero Arrival time.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testCarZeroArrival() throws VehicleException {
		Car car = new Car(CarID, zeroArrivalTime, false);
	}
	
	/**
	 * Test is it no problem with correct values.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testCarPositiveArrival() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
	}
	
	/**
	 * Test getVehID that comes the same Vehicle ID.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testCarVehID() throws VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		assertTrue(car.getVehID() == CarID);
	}

}
