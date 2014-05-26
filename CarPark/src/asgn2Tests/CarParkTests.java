/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Tests 
 * 29/04/2014
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Vehicles.Car;
import asgn2Vehicles.Vehicle;
import asgn2CarParks.CarPark;

/**
 * @author Kou Cheng Fan(Fan)
 *
 */
public class CarParkTests {
	
	private String CarID = "C0";
	private int arrivalTime = 1;
	private int exitTime = 10;
	private int zeroArrivalTime = 0;
	private int negativeArrivalTime = -1;
	private int intendedDuration = 120;
	CarPark newCarPark = new CarPark(100,30,20,10);
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		Car car = new Car(CarID, arrivalTime, false);
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveDepartingVehicles(int, boolean)}.
	 */
	@Test
	public void testArchiveDepartingVehicles() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveNewVehicle(asgn2Vehicles.Vehicle)}.
	 * @throws SimulationException 
	 * @throws VehicleException 
	 */
	@Test (expected = SimulationException.class)
	public void testArchiveNewVehicle() throws SimulationException, VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		
		car.enterQueuedState();
	
		
		newCarPark.archiveNewVehicle(car);
		
		
	}



	/**
	 * Test method for {@link asgn2CarParks.CarPark#archiveQueueFailures(int)}.
	 */
	@Test
	public void testArchiveQueueFailures() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkEmpty()}.
	 */
	@Test
	public void testCarParkEmpty() {
		assertFalse(newCarPark.carParkEmpty());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#carParkFull()}.
	 */
	@Test
	public void testCarParkFull() {
		assertTrue(newCarPark.carParkFull());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#enterQueue(asgn2Vehicles.Vehicle)}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test (expected = SimulationException.class)
	public void testEnterQueue() throws VehicleException, SimulationException {
		
		Car car = new Car(CarID, arrivalTime, false);

			
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
			newCarPark.enterQueue(car);
		

	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#exitQueue(asgn2Vehicles.Vehicle, int)}.
	 */
	@Test (expected = SimulationException.class)
	public void testExitQueue() throws SimulationException, VehicleException {
		Car car = new Car(CarID, arrivalTime, false);
		
		newCarPark.exitQueue(car, exitTime);
		
	}


	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumCars()}.
	 */
	@Test
	public void testGetNumCars() {
		assertEquals(0,newCarPark.getNumCars());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumMotorCycles()}.
	 */
	@Test
	public void testGetNumMotorCycles() {
		assertEquals(0,newCarPark.getNumMotorCycles());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#getNumSmallCars()}.
	 */
	@Test
	public void testGetNumSmallCars() {
		assertEquals(0,newCarPark.getNumSmallCars());
	}


	/**
	 * Test method for {@link asgn2CarParks.CarPark#numVehiclesInQueue()}.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 */
	@Test
	public void testNumVehiclesInQueue() throws VehicleException, SimulationException {
		
		Car car = new Car(CarID, arrivalTime, false);
		
			newCarPark.enterQueue(car);

		assertEquals(1,newCarPark.numVehiclesInQueue());
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#parkVehicle(asgn2Vehicles.Vehicle, int, int)}.
	 */
	@Test
	public void testParkVehicle() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#processQueue(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testProcessQueue() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueEmpty()}.
	 */
	@Test
	public void testQueueEmpty() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#queueFull()}.
	 */
	@Test
	public void testQueueFull() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#spacesAvailable(asgn2Vehicles.Vehicle)}.
	 */
	@Test
	public void testSpacesAvailable() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#tryProcessNewVehicles(int, asgn2Simulators.Simulator)}.
	 */
	@Test
	public void testTryProcessNewVehicles() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link asgn2CarParks.CarPark#unparkVehicle(asgn2Vehicles.Vehicle, int)}.
	 */
	@Test
	public void testUnparkVehicle() {
		fail("Not yet implemented"); // TODO
	}

}
