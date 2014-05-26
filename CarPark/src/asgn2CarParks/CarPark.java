/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2CarParks 
 * 21/04/2014
 * 
 */
package asgn2CarParks;

import java.util.ArrayList;

import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import asgn2Simulators.Constants;
import asgn2Simulators.Simulator;
import asgn2Vehicles.Car;
import asgn2Vehicles.MotorCycle;
import asgn2Vehicles.Vehicle;

/**
 * The CarPark class provides a range of facilities for working with a car park in support 
 * of the simulator. In particular, it maintains a collection of currently parked vehicles, 
 * a queue of vehicles wishing to enter the car park, and an historical list of vehicles which 
 * have left or were never able to gain entry. 
 * 
 * The class maintains a wide variety of constraints on small cars, normal cars and motorcycles 
 * and their access to the car park. See the method javadoc for details. 
 * 
 * The class relies heavily on the asgn2.Vehicle hierarchy, and provides a series of reports 
 * used by the logger. 
 * 
 * @author hogan
 *
 */
public class CarPark {
	
	private int maxCarSpaces;
	private int maxSmallCarSpaces;
	private int maxMotorCycleSpaces;
	private int maxQueueSize;
	private int numCars;
	private int numSmallCars;
	private int numMotorCycles;
	private int numDissatisfied;
	private int remainCarSpaces;
	private int remainSmallCarSpaces;
	private int remainMotorCycleSpaces;
	private int count;
	private String status;
	private ArrayList<Vehicle> queue;
	private ArrayList<Vehicle> carSpaces;
	private ArrayList<Vehicle> smallCarSpaces;
	private ArrayList<Vehicle> motorCycleSpaces;
	private ArrayList<Vehicle> past;
	private ArrayList<Vehicle> temp;

	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * Uses default parameters
	 */
	public CarPark() {
		this(Constants.DEFAULT_MAX_CAR_SPACES,Constants.DEFAULT_MAX_SMALL_CAR_SPACES,
				Constants.DEFAULT_MAX_MOTORCYCLE_SPACES,Constants.DEFAULT_MAX_QUEUE_SIZE);
	}
	
	/**
	 * CarPark constructor sets the basic size parameters. 
	 * @param maxCarSpaces maximum number of spaces allocated to cars in the car park 
	 * @param maxSmallCarSpaces maximum number of spaces (a component of maxCarSpaces) 
	 * 						 restricted to small cars
	 * @param maxMotorCycleSpaces maximum number of spaces allocated to MotorCycles
	 * @param maxQueueSize maximum number of vehicles allowed to queue
	 */
	public CarPark(int maxCarSpaces,int maxSmallCarSpaces, int maxMotorCycleSpaces, int maxQueueSize) {
		this.maxCarSpaces = maxCarSpaces;
		this.maxSmallCarSpaces = maxSmallCarSpaces;
		this.maxMotorCycleSpaces = maxMotorCycleSpaces;
		this.maxQueueSize = maxQueueSize;
		numCars = 0;
		numSmallCars = 0;
		numMotorCycles = 0;
		numDissatisfied = 0;
		remainCarSpaces = maxCarSpaces - maxSmallCarSpaces;
		remainSmallCarSpaces = maxSmallCarSpaces;
		remainMotorCycleSpaces = maxMotorCycleSpaces;
		count = 0;
		queue = new ArrayList<Vehicle>();
		carSpaces = new ArrayList<Vehicle>();
		smallCarSpaces = new ArrayList<Vehicle>();
		motorCycleSpaces = new ArrayList<Vehicle>();
		past = new ArrayList<Vehicle>();
		temp = new ArrayList<Vehicle>();
	}

	/**
	 * Archives vehicles exiting the car park after a successful stay. Includes transition via 
	 * Vehicle.exitParkedState(). 
	 * @param time int holding time at which vehicle leaves
	 * @param force boolean forcing departure to clear car park 
	 * @throws VehicleException if vehicle to be archived is not in the correct state 
	 * @throws SimulationException if one or more departing vehicles are not in the car park when operation applied
	 */
	public void archiveDepartingVehicles(int time,boolean force) throws VehicleException, SimulationException {
		if (force) {
			temp.clear();
			temp.addAll(carSpaces);
			for (Vehicle v : temp) {
				unparkVehicle(v, time);
				past.add(v);
				status += setVehicleMsg(v, "P", "A");
			}
			
			temp.clear();
			temp.addAll(smallCarSpaces);
			for (Vehicle v: temp) {
				unparkVehicle(v, time);
				past.add(v);
				status += setVehicleMsg(v, "P", "A");
			}
			
			temp.clear();
			temp.addAll(motorCycleSpaces);
			for (Vehicle v: temp) {
				unparkVehicle(v, time);
				past.add(v);
				status += setVehicleMsg(v, "P", "A");
			}
		} else {
			
			temp.clear();
			temp.addAll(carSpaces);
			for (Vehicle v : temp) {
				if (v.getDepartureTime() <= time) {
					unparkVehicle(v, time);
					past.add(v);
					status += setVehicleMsg(v, "P", "A");
				}
			}
			
			temp.clear();
			temp.addAll(smallCarSpaces);
			for (Vehicle v: temp) {
				if (v.getDepartureTime() <= time) {
					unparkVehicle(v, time);
					past.add(v);
					status += setVehicleMsg(v, "P", "A");
				}
			}
			
			temp.clear();
			temp.addAll(motorCycleSpaces);
			for (Vehicle v: temp) {
				if (v.getDepartureTime() <= time) {
					unparkVehicle(v, time);
					past.add(v);
					status += setVehicleMsg(v, "P", "A");
				}
			}
		}
	}
		
	/**
	 * Method to archive new vehicles that don't get parked or queued and are turned 
	 * away
	 * @param v Vehicle to be archived
	 * @throws SimulationException if vehicle is currently queued or parked
	 */
	public void archiveNewVehicle(Vehicle v) throws SimulationException {
		if (v.isParked()) {
			throw new SimulationException ("Vehicle is currently parked.");
		}
		if (v.isQueued()) {
			throw new SimulationException ("Vehicle is currently queued.");
		}
		past.add(v);
		numDissatisfied++;
	}
	
	/**
	 * Archive vehicles which have stayed in the queue too long 
	 * @param time int holding current simulation time 
	 * @throws VehicleException if one or more vehicles not in the correct state or if timing constraints are violated
	 */
	public void archiveQueueFailures(int time) throws VehicleException {
		temp.clear();
		temp.addAll(queue);
		for (Vehicle v : temp) {
			if (v.getArrivalTime() + Constants.MAXIMUM_QUEUE_TIME <= time) {
				v.exitQueuedState(time);
				queue.remove(queue.indexOf(v));				
				past.add(v);
				numDissatisfied++;
				status += setVehicleMsg(v, "Q", "A");
			}
		}
	}
	
	/**
	 * Simple status showing whether carPark is empty
	 * @return true if car park empty, false otherwise
	 */
	public boolean carParkEmpty() {
		return (carSpaces.size() == 0 && smallCarSpaces.size() == 0 && motorCycleSpaces.size() == 0);				
	}
	
	/**
	 * Simple status showing whether carPark is full
	 * @return true if car park full, false otherwise
	 */
	public boolean carParkFull() {
		return (remainCarSpaces == 0 && remainSmallCarSpaces == 0 && remainMotorCycleSpaces == 0);
	}
	
	/**
	 * Method to add vehicle successfully to the queue
	 * Precondition is a test that spaces are available
	 * Includes transition through Vehicle.enterQueuedState 
	 * @param v Vehicle to be added 
	 * @throws SimulationException if queue is full  
	 * @throws VehicleException if vehicle not in the correct state 
	 */
	public void enterQueue(Vehicle v) throws SimulationException, VehicleException {
		if (queueFull()) {
			throw new SimulationException ("Queue is full.");
		}
		v.enterQueuedState();
		queue.add(v);
	}
	
	
	
	/**
	 * Method to remove vehicle from the queue after which it will be parked or 
	 * removed altogether. Includes transition through Vehicle.exitQueuedState.  
	 * @param v Vehicle to be removed from the queue 
	 * @param exitTime int time at which vehicle exits queue
	 * @throws SimulationException if vehicle is not in queue 
	 * @throws VehicleException if the vehicle is in an incorrect state or timing 
	 * constraints are violated
	 */
	public void exitQueue(Vehicle v,int exitTime) throws SimulationException, VehicleException {
		if (!queue.contains(v)) {
			throw new SimulationException ("Vehiche is not in queue");
		}
		v.exitQueuedState(exitTime);
		queue.remove(queue.indexOf(v));
	}
	
	/**
	 * State dump intended for use in logging the final state of the carpark
	 * All spaces and queue positions should be empty and so we dump the archive
	 * @return String containing dump of final carpark state 
	 */
	public String finalState() {
		String str = "Vehicles Processed: count:" + 
				this.count + ", logged: " + this.past.size() 
				+ "\nVehicle Record: \n";
		for (Vehicle v : this.past) {
			str += v.toString() + "\n\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple getter for number of cars in the car park 
	 * @return number of cars in car park, including small cars
	 */
	public int getNumCars() {
		return numCars + numSmallCars;
	}
	
	/**
	 * Simple getter for number of motorcycles in the car park 
	 * @return number of MotorCycles in car park, including those occupying 
	 * 			a small car space
	 */
	public int getNumMotorCycles() {
		return numMotorCycles;
	}
	
	/**
	 * Simple getter for number of small cars in the car park 
	 * @return number of small cars in car park, including those 
	 * 		   not occupying a small car space. 
	 */
	public int getNumSmallCars() {
		return numSmallCars;
	}
	
	/**
	 * Method used to provide the current status of the car park. 
	 * Uses private status String set whenever a transition occurs. 
	 * Example follows (using high probability for car creation). At time 262, 
	 * we have 276 vehicles existing, 91 in car park (P), 84 cars in car park (C), 
	 * of which 14 are small (S), 7 MotorCycles in car park (M), 48 dissatisfied (D),
	 * 176 archived (A), queue of size 9 (CCCCCCCCC), and on this iteration we have 
	 * seen: car C go from Parked (P) to Archived (A), C go from queued (Q) to Parked (P),
	 * and small car S arrive (new N) and go straight into the car park<br>
	 * 262::276::P:91::C:84::S:14::M:7::D:48::A:176::Q:9CCCCCCCCC|C:P>A||C:Q>P||S:N>P|
	 * @return String containing current state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.count + "::" 
		+ "P:" + (this.carSpaces.size() + this.smallCarSpaces.size() + this.motorCycleSpaces.size())
		+ "::"
		+ "C:" + this.numCars + "::S:" + this.numSmallCars 
		+ "::M:" + this.numMotorCycles 
		+ "::D:" + this.numDissatisfied 
		+ "::A:" + this.past.size()  
		+ "::Q:" + this.queue.size(); 
		for (Vehicle v : this.queue) {
			if (v instanceof Car) {
				if (((Car)v).isSmall()) {
					str += "S";
				} else {
					str += "C";
				}
			} else {
				str += "M";
			}
		}
		str += this.status;
		this.status="";
		return str+"\n";
	}
	

	/**
	 * State dump intended for use in logging the initial state of the carpark.
	 * Mainly concerned with parameters. 
	 * @return String containing dump of initial carpark state 
	 */
	public String initialState() {
		return "CarPark [maxCarSpaces: " + this.maxCarSpaces
				+ " maxSmallCarSpaces: " + this.maxSmallCarSpaces 
				+ " maxMotorCycleSpaces: " + this.maxMotorCycleSpaces 
				+ " maxQueueSize: " + this.maxQueueSize + "]";
	}

	/**
	 * Simple status showing number of vehicles in the queue 
	 * @return number of vehicles in the queue
	 */
	public int numVehiclesInQueue() {
		return queue.size();
	}
	
	/**
	 * Method to add vehicle successfully to the car park store. 
	 * Precondition is a test that spaces are available. 
	 * Includes transition via Vehicle.enterParkedState.
	 * @param v Vehicle to be added 
	 * @param time int holding current simulation time
	 * @param intendedDuration int holding intended duration of stay 
	 * @throws SimulationException if no suitable spaces are available for parking 
	 * @throws VehicleException if vehicle not in the correct state or timing constraints are violated
	 */
	public void parkVehicle(Vehicle v, int time, int intendedDuration) throws SimulationException, VehicleException {
		if (!spacesAvailable(v)) {
			throw new SimulationException ("No Spaces.");
		}
		// Calculate the spaces remaining
		if (v instanceof Car) {
			if (((Car) v).isSmall()) {
				numSmallCars++;
				if (remainSmallCarSpaces > 1) {
					smallCarSpaces.add(v);
					remainSmallCarSpaces--;
					v.enterParkedState(time, intendedDuration);
				} else {
					carSpaces.add(v);
					remainCarSpaces--;
					v.enterParkedState(time, intendedDuration);
				}
			} else {
				numCars++;
				carSpaces.add(v);
				remainCarSpaces--;
				v.enterParkedState(time, intendedDuration);
			}
		} else {
			numMotorCycles++;
			if (remainMotorCycleSpaces > 1) {
				motorCycleSpaces.add(v);
				remainMotorCycleSpaces--;
				v.enterParkedState(time, intendedDuration);
			} else {
				smallCarSpaces.add(v);
				remainSmallCarSpaces--;
				v.enterParkedState(time, intendedDuration);
			}
		}
	}

	/**
	 * Silently process elements in the queue, whether empty or not. If possible, add them to the car park. 
	 * Includes transition via exitQueuedState where appropriate
	 * Block when we reach the first element that can't be parked. 
	 * @param time int holding current simulation time 
	 * @throws SimulationException if no suitable spaces available when parking attempted
	 * @throws VehicleException if state is incorrect, or timing constraints are violated
	 */
	public void processQueue(int time, Simulator sim) throws VehicleException, SimulationException {
		temp.clear();
		temp.addAll(queue);
		for (Vehicle v : temp) {
			if (spacesAvailable(v)) {
				exitQueue(v, time);
				parkVehicle(v, time, sim.setDuration());
				status += setVehicleMsg(v, "Q", "P");
			}
		}
		archiveQueueFailures(time);
	}

	/**
	 * Simple status showing whether queue is empty
	 * @return true if queue empty, false otherwise
	 */
	public boolean queueEmpty() {
		return (queue.isEmpty());
	}

	/**
	 * Simple status showing whether queue is full
	 * @return true if queue full, false otherwise
	 */
	public boolean queueFull() {
		return (queue.size() == maxQueueSize);
	}
	
	/**
	 * Method determines, given a vehicle of a particular type, whether there are spaces available for that 
	 * type in the car park under the parking policy in the class header.  
	 * @param v Vehicle to be stored. 
	 * @return true if space available for v, false otherwise 
	 */
	public boolean spacesAvailable(Vehicle v) {
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				return (remainCarSpaces > 0 || remainSmallCarSpaces > 0);
			} else {
				return (remainCarSpaces > 0);
			}
		} else {
			return (remainSmallCarSpaces > 0 || remainMotorCycleSpaces > 0);
		}
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CarPark [count: " + count
				+ " numCars: " + numCars
				+ "numSmallCars: " + numSmallCars
				+ "numMotorCycles: " + numMotorCycles
				+ " queue: " + (queue.size())
				+ " numDissatisified: " + numDissatisfied
				+ " past: " + past.size() + "]";
				
	}

	/**
	 * Method to try to create new vehicles (one trial per vehicle type per time point) 
	 * and to then try to park or queue (or archive) any vehicles that are created 
	 * @param sim Simulation object controlling vehicle creation 
	 * @throws SimulationException if no suitable spaces available when operation attempted 
	 * @throws VehicleException if vehicle creation violates constraints 
	 */
	public void tryProcessNewVehicles(int time,Simulator sim) throws VehicleException, SimulationException {
		Car newCar = null;
		MotorCycle newMotorCycle = null;
		
		if (sim.motorCycleTrial()) {
			newMotorCycle = new MotorCycle("MC" + (++count), time);
			
			if (spacesAvailable(newMotorCycle)) {
				parkVehicle(newMotorCycle, time, sim.setDuration());
				status += setVehicleMsg(newCar, "N", "P");
			} else {
				if (queueFull()) {
					archiveNewVehicle(newMotorCycle);
					status += setVehicleMsg(newMotorCycle, "N", "A");	
				} else {
					enterQueue(newMotorCycle);
					status += setVehicleMsg(newMotorCycle, "N", "Q");
				}
			}
		}
		
		
		if(sim.newCarTrial()) {
			if (sim.smallCarTrial()) {
				newCar = new Car("C" + (++count), time, true);
			} else {
				newCar = new Car("C" + (++count), time, false);
			}
			
			if (spacesAvailable(newCar)) {
				parkVehicle(newCar, time, sim.setDuration());
				status += setVehicleMsg(newCar, "N", "P");
			} else {
				if (!queueFull()) {
					enterQueue(newCar);
					status += setVehicleMsg(newMotorCycle, "N", "Q");
				} else {
					archiveNewVehicle(newCar);
					status += setVehicleMsg(newMotorCycle, "N", "A");
				}
			}
		}		
	}

	/**
	 * Method to remove vehicle from the carpark. 
	 * For symmetry with parkVehicle, include transition via Vehicle.exitParkedState.  
	 * So vehicle should be in parked state prior to entry to this method. 
	 * @param v Vehicle to be removed from the car park 
	 * @throws VehicleException if Vehicle is not parked, is in a queue, or violates timing constraints 
	 * @throws SimulationException if vehicle is not in car park
	 */
	public void unparkVehicle(Vehicle v,int departureTime) throws VehicleException, SimulationException {
		int spaceIndex;
		// Remove object from arrayList, and release the remain space count
		if (v instanceof Car) {
			// for Small Car
			if (((Car) v).isSmall()) {
				// check the car is parked in small car space or not
				spaceIndex = smallCarSpaces.indexOf(v);
				if (spaceIndex != -1) { // particular car in small car space
					smallCarSpaces.remove(spaceIndex);
					v.exitParkedState(departureTime);
					numSmallCars--; // release the count
					remainSmallCarSpaces++;
				} else { // check the car is parked in normal car space or not
					spaceIndex = carSpaces.indexOf(v);
					if (spaceIndex != -1) { // particular car in normal car space
						carSpaces.remove(spaceIndex);
						v.exitParkedState(departureTime);
						numSmallCars--; //release the count
						remainCarSpaces++;
					} else { // not found in either small or normal car spaces
						throw new SimulationException ("Vehicle is not in car park.");
					}
				}
			} else { // for normal car
				spaceIndex = carSpaces.indexOf(v);
				if (spaceIndex != -1) { // check the car is parked in normal car space or not
					carSpaces.remove(spaceIndex);
					v.exitParkedState(departureTime);
					numCars--; // release count
					remainCarSpaces++;
				} else { // not found in normal car spaces
					throw new SimulationException ("Vehicle is not in car park.");
				}
			}
		} else { // for motorCycles
			spaceIndex = motorCycleSpaces.indexOf(v);
			if (spaceIndex != -1) { // check the motorcycle is parked in motorcycle space or not
				motorCycleSpaces.remove(spaceIndex);
				v.exitParkedState(departureTime);
				numMotorCycles--; // release count
				remainMotorCycleSpaces++;
			} else {
				spaceIndex = smallCarSpaces.indexOf(v);
				if (spaceIndex != -1) { // check the motorcycle is parked in smallCar space or not
					smallCarSpaces.remove(spaceIndex);
					v.exitParkedState(departureTime);
					numMotorCycles--; // release count
					remainSmallCarSpaces--;
				} else {// not found in either motorCycle or smallCar spaces
					throw new SimulationException ("Vehicle is not in car park.");
				}
			}
		}
	}
	
	/**
	 * Helper to set vehicle message for transitions 
	 * @param v Vehicle making a transition (uses S,C,M)
	 * @param source String holding starting state of vehicle (N,Q,P,A) 
	 * @param target String holding finishing state of vehicle (Q,P,A) 
	 * @return String containing transition in the form: |(S|C|M):(N|Q|P|A)>(Q|P|A)| 
	 */
	private String setVehicleMsg(Vehicle v,String source, String target) {
		String str="";
		if (v instanceof Car) {
			if (((Car)v).isSmall()) {
				str+="S";
			} else {
				str+="C";
			}
		} else {
			str += "M";
		}
		return "|"+str+":"+source+">"+target+"|";
	}
}
