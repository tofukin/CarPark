/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Vehicles 
 * 20/04/2014
 * 
 */
package asgn2Vehicles;

import asgn2Exceptions.VehicleException;

/**
 * The Car class is a specialisation of the Vehicle class to cater for production cars
 * This version of the class does not cater for model types, but records whether or not the 
 * vehicle can use a small parking space. 
 * 
 * @author hogan
 *
 */
public class Car extends Vehicle {
	
	private boolean small;
	private String vehID;
	private int arrivalTime;

	/**
	 * The Car Constructor - small set at creation, not mutable. 
	 * @param vehID - identification number or plate of the vehicle
	 * @param arrivalTime - time (minutes) at which the vehicle arrives and is 
	 *        either queued or given entry to the carpark 
	 * @param small - indicator whether car is regarded as small or not
	 * @throws VehicleException if arrivalTime is <= 0  
	 */
	public Car(String vehID, int arrivalTime, boolean small) throws VehicleException {
		super(vehID, arrivalTime);
		this.small = small;
		this.vehID = vehID;
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Boolean status indicating whether car is small enough for small 
	 * car parking spaces  
	 * @return true if small parking space, false otherwise
	 */
	public boolean isSmall() {
		return small;
	}

	/* (non-Javadoc)
	 * @see asgn2Vehicles.Vehicle#toString()
	 */
	@Override
	public String toString() {
		String wasQueue;
		String wasParked;
		String wasSatisfied;
		String isSmall;
		
		if (super.wasQueued()) {
			wasQueue = "Exit from Queue: " + super.getParkingTime()
					+ "\n" + "Queuing Time: " + (super.getParkingTime() - super.getArrivalTime())
					+ "\n";
		} else {
			wasQueue = "Vehicle was not queued \n";
		}
		
		if (super.wasParked()) {
			wasParked = "Entry to Car Park: " + super.getParkingTime()
					+ "\n" + "Exit from Car Park: " + super.getDepartureTime()
					+ "\n" + "Parking Time: " + (super.getDepartureTime() - super.getParkingTime());
		} else {
			wasParked = "Vehicle was not parked \n";
		}
		
		if (super.isSatisfied()) {
			wasSatisfied = "Customer was satisfied";
		} else {
			wasSatisfied = "Customer was not satisfied";
		}
		
		if (isSmall()) {
			isSmall = "Car can use small parking space";
		} else {
			isSmall = "Car cannot use small parking space";
		}
		
		return "Vehicle vehID: " + vehID + "\n"
				+ "Arrival Time: " +  arrivalTime + "\n"
				+ wasQueue + wasParked + wasSatisfied + isSmall;
	}
}
