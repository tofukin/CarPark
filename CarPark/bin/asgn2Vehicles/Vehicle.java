/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 19/04/2014
 * 
 */
package asgn2Vehicles;

import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;



/**
 * Vehicle is an abstract class specifying the basic state of a vehicle and the methods used to 
 * set and access that state. A vehicle is created upon arrival, at which point it must either 
 * enter the car park to take a vacant space or become part of the queue. If the queue is full, then 
 * the vehicle must leave and never enters the car park. The vehicle cannot be both parked and queued 
 * at once and both the constructor and the parking and queuing state transition methods must 
 * respect this constraint. 
 * 
 * Vehicles are created in a neutral state. If the vehicle is unable to park or queue, then no changes 
 * are needed if the vehicle leaves the carpark immediately.
 * Vehicles that remain and can't park enter a queued state via {@link #enterQueuedState() enterQueuedState} 
 * and leave the queued state via {@link #exitQueuedState(int) exitQueuedState}. 
 * Note that an exception is thrown if an attempt is made to join a queue when the vehicle is already 
 * in the queued state, or to leave a queue when it is not. 
 * 
 * Vehicles are parked using the {@link #enterParkedState(int, int) enterParkedState} method and depart using 
 * {@link #exitParkedState(int) exitParkedState}
 * 
 * Note again that exceptions are thrown if the state is inappropriate: vehicles cannot be parked or exit 
 * the car park from a queued state. 
 * 
 * The method javadoc below indicates the constraints on the time and other parameters. Other time parameters may 
 * vary from simulation to simulation and so are not constrained here.  
 * 
 * @author Kou  Cheng Fan
 *
 */
public abstract class Vehicle {
	
	private String vehID;
	private int arrivalTime;
	private int departureTime;
	private int parkingTime;
	private int intendedDuration;
	private int exitTime;
	private boolean isParked = false;
	private boolean isQueued = false;
	private boolean isSatisfied = false;
	private boolean wasParked = false;
	private boolean wasQueued = false;
	
	/**
	 * Vehicle Constructor 
	 * @param vehID String identification number or plate of the vehicle
	 * @param arrivalTime int time (minutes) at which the vehicle arrives and is 
	 *        either queued, given entry to the car park or forced to leave
	 * @throws VehicleException if arrivalTime is <= 0 
	 */
	public Vehicle(String vehID,int arrivalTime) throws VehicleException  {
		this.vehID = vehID;
		if (arrivalTime <= 0) {
			throw new VehicleException ("Arrival time cannot less then 0");
		}
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Transition vehicle to parked state (mutator)
	 * Parking starts on arrival or on exit from the queue, but time is set here
	 * @param parkingTime int time (minutes) at which the vehicle was able to park
	 * @param intendedDuration int time (minutes) for which the vehicle is intended to remain in the car park.
	 *  	  Note that the parkingTime + intendedDuration yields the departureTime
	 * @throws VehicleException if the vehicle is already in a parked or queued state, if parkingTime < 0, 
	 *         or if intendedDuration is less than the minimum prescribed in asgnSimulators.Constants
	 */
	public void enterParkedState(int parkingTime, int intendedDuration) throws VehicleException {
		if (isParked) {
			throw new VehicleException ("Already parked.");
		}
		if (isQueued) {
			throw new VehicleException ("Already queued");
		}
		if (parkingTime < 0) {
			throw new VehicleException ("Parking Time cannot less than 0.");
		}
		if (intendedDuration < Constants.MINIMUM_STAY) {
			throw new VehicleException ("Intended Duration cannot less than minimum time allowed.");
		}
		this.parkingTime = parkingTime;
		this.departureTime = parkingTime + intendedDuration;
		//this.intendedDuration = intendedDuration;
		isParked = true;
	}
	
	/**
	 * Transition vehicle to queued state (mutator) 
	 * Queuing formally starts on arrival and ceases with a call to {@link #exitQueuedState(int) exitQueuedState}
	 * @throws VehicleException if the vehicle is already in a queued or parked state
	 */
	public void enterQueuedState() throws VehicleException {
		if (isQueued) {
			throw new VehicleException ("Already queued.");
		}
		if (isParked) {
			throw new VehicleException ("Already parked.");			
		}
		isQueued = true;
	}
	
	/**
	 * Transition vehicle from parked state (mutator) 
	 * @param departureTime int holding the actual departure time 
	 * @throws VehicleException if the vehicle is not in a parked state, is in a queued 
	 * 		  state or if the revised departureTime < parkingTime
	 */
	public void exitParkedState(int departureTime) throws VehicleException {
		if (!isParked) {
			throw new VehicleException ("Not parked.");
		}
		if (isQueued) {
			throw new VehicleException ("In queue.");
		}
		if (departureTime < parkingTime) {
			throw new VehicleException ("Invalid departure time.");
		}
		this.departureTime = departureTime;
		isParked = false;		
		wasParked = true;
		isSatisfied = true;
	}

	/**
	 * Transition vehicle from queued state (mutator) 
	 * Queuing formally starts on arrival with a call to {@link #enterQueuedState() enterQueuedState}
	 * Here we exit and set the time at which the vehicle left the queue
	 * @param exitTime int holding the time at which the vehicle left the queue 
	 * @throws VehicleException if the vehicle is in a parked state or not in a queued state, or if 
	 *  exitTime is not later than arrivalTime for this vehicle
	 */
	public void exitQueuedState(int exitTime) throws VehicleException {
		if (isParked) {
			throw new VehicleException ("Already parked.");
		}
		if (!isQueued) {
			throw new VehicleException ("Not in queue");
		}
		if (exitTime <= arrivalTime) {
			throw new VehicleException ("Invalid exit time: exit time not later than arrival time.");
		}
		this.exitTime = exitTime;
		isQueued = false;
		wasQueued = true;
	}
	
	/**
	 * Simple getter for the arrival time 
	 * @return the arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * Simple getter for the departure time from the car park
	 * Note: result may be 0 before parking, show intended departure 
	 * time while parked; and actual when archived
	 * @return the departureTime
	 */
	public int getDepartureTime() {
		return departureTime;
	}
	
	/**
	 * Simple getter for the parking time
	 * Note: result may be 0 before parking
	 * @return the parkingTime
	 */
	public int getParkingTime() {
		return parkingTime;
	}

	/**
	 * Simple getter for the vehicle ID
	 * @return the vehID
	 */
	public String getVehID() {
		return vehID;
	}

	/**
	 * Boolean status indicating whether vehicle is currently parked 
	 * @return true if the vehicle is in a parked state; false otherwise
	 */
	public boolean isParked() {
		return this.isParked;
	}

	/**
	 * Boolean status indicating whether vehicle is currently queued
	 * @return true if vehicle is in a queued state, false otherwise 
	 */
	public boolean isQueued() {
		return this.isQueued;
	}
	
	/**
	 * Boolean status indicating whether customer is satisfied or not
	 * Satisfied if they park; dissatisfied if turned away, or queuing for too long 
	 * Note that calls to this method may not reflect final status 
	 * @return true if satisfied, false if never in parked state or if queuing time exceeds max allowable 
	 */
	public boolean isSatisfied() {
		return this.isSatisfied;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String wasQueue;
		String wasParked;
		String wasSatisfied;
		
		if (wasQueued()) {
			wasQueue = "Exit from Queue: " + getParkingTime()
					+ "\n" + "Queuing Time: " + (getParkingTime() - getArrivalTime())
					+ "\n";
		} else {
			wasQueue = "Vehicle was not queued \n";
		}
		
		if (wasParked()) {
			wasParked = "Entry to Car Park: " + getParkingTime()
					+ "\n" + "Exit from Car Park: " + getDepartureTime()
					+ "\n" + "Parking Time: " + (getDepartureTime() - .getParkingTime());
		} else {
			wasParked = "Vehicle was not parked \n";
		}
		
		if (isSatisfied()) {
			wasSatisfied = "Customer was satisfied";
		} else {
			wasSatisfied = "Customer was not satisfied";
		}
		
		return "Vehicle vehID: " + vehID + "\n"
				+ "Arrival Time: " +  arrivalTime + "\n"
				+ wasQueue + wasParked + wasSatisfied;		
	}

	/**
	 * Boolean status indicating whether vehicle was ever parked
	 * Will return false for vehicles in queue or turned away 
	 * @return true if vehicle was or is in a parked state, false otherwise 
	 */
	public boolean wasParked() {
		return this.wasParked;
	}

	/**
	 * Boolean status indicating whether vehicle was ever queued
	 * @return true if vehicle was or is in a queued state, false otherwise 
	 */
	public boolean wasQueued() {
		return this.wasQueued;
	}
}
