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
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;

/**
 * @author hogan
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
	
	@Test (expected = VehicleException.class)
	public void testNegativeArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, negativeArrivalTime);
	}
	
	@Test
	public void testArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
	}
	
	@Test (expected = VehicleException.class)
	public void testZeroArrival() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, zeroArrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getVehID()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetVehID() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertEquals(MCID, mc.getVehID());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getArrivalTime()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetArrivalTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertEquals(mc.getArrivalTime(), arrivalTime);
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterQueuedState()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testEnterQueuedState() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		assertTrue(mc.isQueued());
	}
	
	@Test (expected = VehicleException.class)
	public void testDoubleQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.enterQueuedState();
	}
	
	@Test (expected = VehicleException.class)
	public void testParkedAndQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.enterQueuedState();
	}
	
	@Test (expected = VehicleException.class)
	public void textExitQueuedStateWhileNotInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.exitQueuedState(exitTime);
	}
	
	@Test (expected = VehicleException.class)
	public void textExitQueuedStateWhileParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitQueuedState(exitTime);
	}
	
	@Test (expected = VehicleException.class)
	public void testExitQueuedStateWhileExitTimeNotLaterThenArrivalTime() throws VehicleException {
		int arrivalTimeTen = 10;
		MotorCycle mc = new MotorCycle(MCID, arrivalTimeTen);
		mc.exitQueuedState(exitTime);
	}
	
	@Test
	public void testExitQueuedStateGetIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitQueuedState(exitTime);
		assertFalse(mc.isParked());
	}
	
	@Test
	public void testExitQueuedStateGetWasParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitQueuedState(exitTime);
		assertTrue(mc.wasQueued());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#enterParkedState(int, int)}.
	 * @throws VehicleException 
	 */
	@Test
	public void testEnterParkedState() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
	}
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileParked() throws VehicleException {
		int parkT = 4;
		int intend = 20; // for park the second time
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.enterParkedState(parkT, intend);
	}
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.enterParkedState(parkingTime, intendedDuration);
	}
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileParkingTimeLessThanZero() throws VehicleException {
		int parkT = -1;
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkT, intendedDuration);
	}
	
	@Test (expected = VehicleException.class)
	public void testEnterParkedStateWhileDurationLessThanMinimun() throws VehicleException {
		int intend = 10;
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intend);
	}
	
	@Test
	public void testEnterParkedStateGetDepartureTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertEquals(parkingTime + intendedDuration, mc.getDepartureTime());
	}
	
	@Test
	public void testEnterParkedStateIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertTrue(mc.isParked());
	}

	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.exitParkedState(departureTime);
	}
	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereInQueue() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		mc.exitParkedState(departureTime);
	}
	
	@Test (expected = VehicleException.class)
	public void testExitParkedStateWhereDepartureTimeEarlierThenParkingTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		int parkT = 50; // a value for parking time later then departure time
		mc.enterParkedState(parkT, intendedDuration);
		mc.exitParkedState(departureTime);
	}
	
	@Test
	public void testExitParkedStateGetIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertFalse(mc.isParked());
	}
	
	@Test
	public void testExitParkedStateGetWasParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertTrue(mc.wasParked());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isParked()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertTrue(mc.isParked());
	}
	
	@Test
	public void testIsNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isParked());
	}
	

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isQueued()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testIsQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterQueuedState();
		assertTrue(mc.isQueued());
	}
	
	@Test
	public void testIsNotQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isQueued());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getParkingTime()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetParkingTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		assertEquals(parkingTime, mc.getParkingTime());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#getDepartureTime()}.
	 * @throws VehicleException 
	 */
	@Test
	public void testGetDepartureTime() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertEquals(departureTime, mc.getDepartureTime());
	}

	/**
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
	
	@Test
	public void testWasNotQueued() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.wasQueued());
	}

	/**
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
	
	@Test
	public void testWasNotParked() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.wasParked());
	}

	/**
	 * Test method for {@link asgn2Vehicles.Vehicle#isSatisfied()}.
	 */
	@Test
	public void testIsSatisfied() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		mc.enterParkedState(parkingTime, intendedDuration);
		mc.exitParkedState(departureTime);
		assertTrue(mc.isSatisfied());
	}
	
	@Test
	public void testIsNotSatisfied() throws VehicleException {
		MotorCycle mc = new MotorCycle(MCID, arrivalTime);
		assertFalse(mc.isSatisfied());
	}

}
