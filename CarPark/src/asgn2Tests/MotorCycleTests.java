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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Vehicles.MotorCycle;

/**
 * @author Kou Cheng Fan 
 *
 */
public class MotorCycleTests {
	
	private String MCID = "M0";
	private int arrivalTime = 1;
	private int zeroArrivalTime = 0;
	private int negativeArrivalTime = -1;
	private int parkingTime = 2;
	private int intendedDuration = 20;
	private int exitTime = 3;
	private int departureTime = 22;
	
	/**
	 * Throws if that comes with a negtaive arrival Time.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * 
	 */
	
	@Test (expected = VehicleException.class)
	public void testNegativeArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, negativeArrivalTime);
	}
	
	/**
	 * test is it no problem with correct values.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * 
	 */
	
	@Test
	public void testArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
	}
	
	
	/**
	 * Throws if that comes with a zero arrival Time.
	 * @throws VehicleException 
	 * @throws SimulationException 
	 * 
	 */
	@Test (expected = VehicleException.class)
	public void testZeroArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, zeroArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getVehID()}.
	 * Test is it able to return a correct Vehicle ID.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetVehID() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertEquals(MCID, mc.getVehID());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getArrivalTime()}.
	 * Test is it able to return a correct arrival time.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetArrivalTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertEquals(mc.getArrivalTime(), arrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterQueuedState()}.
	 * Test is it able to enter the queue.
	 * @throws VehicleException 
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		assertTrue(mc.isQueued());
	}
	
	/**
	 * Throws if a queued motorCycle enter queue again.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testDoubleQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.enterQueuedState();
	}
	
	/**
	 * Throws exception if a parked motorCycle enter queue
	 * @throws VehicleException 
	 */
	
	
	@Test (expected = VehicleException.class)
	public void testParkedAndQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.enterQueuedState();
	}
	
	
	/**
	 * Throws exception if a not queued motorCycle going to exit queue 
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void textExitQueuedStateWhileNotInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.exitQueuedState(exitTime);
	}
	
	
	/**
	 * Throws exception if a parked motorCycle going to exit queue 
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void textExitQueuedStateWhileParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitQueuedState(exitTime);
	}
	
	/**
	 * Throws exception if exit Time less than arrivalTime.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testExitQueuedStateWhileExitTimeNotLaterThenArrivalTime() throws VehicleException {
		int arrivalTimeTen = 10;
		MotorCycle mc = new MotorCycle(MCID, arrivalTimeTen);
		mc.exitQueuedState(exitTime);
	}
	
	/** Return false if the motorCycle is not parked.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testExitQueuedStateGetIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitQueuedState(exitTime);
		assertFalse(mc.isParked());
	}
	
	/** Return true if the motorCycle was queued.
	 * @throws VehicleException 
	 */
	
	
	@Test
	public void testExitQueuedStateGetWasParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitQueuedState(exitTime);
		assertTrue(mc.wasQueued());
	}

	/** Test if is it works that the new motorcycle is able to enter the parked state with 
	 * correct values.
	 * Test method for {@link asgn2Vehicles.Vehicle#enterParkedState(int, int)}.
	 * @throws VehicleException 
	 */
	@Test
	public void testEnterParkedState() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
	}
	
	/**Throws exception if a parked motorcycle going park again.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileParked() throws VehicleException {
		int parkT = 4;
		int intend = 20; // for park the second time
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.enterParkedState(parkT, intend);
	}
	/**Throws exception if a queued motorcycle going park again.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.enterParkedState(parkingTime, intendedDuration);
	}
	/**Throws exception if a motorcycle going to park but with negative arrival time.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileParkingTimeLessThanZero() throws VehicleException {
		int parkT = -1;
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkT, intendedDuration);
	}
	
	/**Throws exception if a motorcycle going to park but duration 
	 * time less that minimum.
	 * @throws VehicleException 
	 */
	
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileDurationLessThanMinimun() throws VehicleException {
		int intend = 10;
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intend);
	}
	
	/** return true if the expected departure time is equal the departure time.
	 * @throws VehicleException 
	 */
	
	
	@Test
	public void testEnterParkedStateGetDepartureTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertEquals(parkingTime + intendedDuration, mc.getDepartureTime());
	}
	
	/**Return true if the motorcycle is able to parked with correct values.
	 * @throws VehicleException 
	 */
	
	
	@Test
	public void testEnterParkedStateIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertTrue(mc.isParked());
	}

	/**Throws exception if a not parked motorcycle going to exit.
	 * @throws VehicleException 
	 */
	
	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.exitParkedState(departureTime);
	}
	
	/**Throws exception if a queued motorcycle going to exit.
	 * @throws VehicleException 
	 */
	
	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitParkedState(departureTime);
	}
	
	/**Throws exception if a motorcycle going to exit but departure time earlier that parking 
	 * time.
	 * @throws VehicleException 
	 */
	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereDepartureTimeEarlierThenParkingTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		int parkT = 50; // a value for parking time later then departure time
		mc.enterParkedState(parkT, intendedDuration);
		mc.exitParkedState(departureTime);
	}
	
	
	/**test is it works well about the isParked method.
	 * @throws VehicleException 
	 */
	@Test
	public void testExitParkedStateGetIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertFalse(mc.isParked());
	}
	
	/**Test exit parked State get Was Parked.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testExitParkedStateGetWasParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertTrue(mc.wasParked());
	}

	/**Test the isParked method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertTrue(mc.isParked());
	}
	
	/**Test the isParked method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testIsNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isParked());
	}
	

	/**Test the isQueued method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		assertTrue(mc.isQueued());
	}
	
	/**Test the isQueued method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 * @throws VehicleException 
	 */
	
	@Test
	public void testIsNotQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isQueued());
	}

	/**Test the get parking time method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetParkingTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertEquals(parkingTime, mc.getParkingTime());
	}

	/**Test the get parking time method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetDepartureTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertEquals(departureTime, mc.getDepartureTime());
	}

	/**Test the  wasQueued method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#wasQueued()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testWasQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitQueuedState(exitTime);
		assertTrue(mc.wasQueued());
	}
	
	/**Test the  wasQueued method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#wasQueued()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testWasNotQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.wasQueued());
	}

	/**Test the  wasParked method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#wasParked()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testWasParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertTrue(mc.wasParked());
	}
	
	/**Test the  wasParked method is it work well.
	 * Test method for {@link asgn2Vehicles.Vehicle#wasParked()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testWasNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.wasParked());
	}

	/**Test the  isSatisfied method is it work well.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsNotSatisfied() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isSatisfied());
	}

}
