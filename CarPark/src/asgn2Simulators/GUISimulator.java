/**
 * 
 * This file is part of the CarParkSimulator Project, written as 
 * part of the assessment for INB370, semester 1, 2014. 
 *
 * CarParkSimulator
 * asgn2Simulators 
 * 20/04/2014
 * 
 */
package asgn2Simulators;

import asgn2CarParks.CarPark;
import asgn2Exceptions.SimulationException;
import asgn2Exceptions.VehicleException;
import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * @author hogan
 *
 */
@SuppressWarnings("serial")
public class GUISimulator extends JFrame implements Runnable {
    
    private static GUIRunner gui;
    private static CarPark carPark;
    private static Simulator sim;
    private static Log log;
    private static SimulationRunner runner;

	/**
	 * @param arg0
	 * @throws HeadlessException
	 */
	public GUISimulator(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SimulationException, IOException, VehicleException {
            gui = new GUIRunner();
            if (args.length == 10) { // accept the command line arguments
                int maxCarSpaces = Integer.parseInt(args[0]);
                gui.setTxtMaxCarSpaces(maxCarSpaces);
                int maxSmallCarSpaces = Integer.parseInt(args[1]);
                gui.setTxtMaxSmallSpaces(maxSmallCarSpaces);
                int maxMotorCycleSpaces = Integer.parseInt(args[2]);
                gui.setTxtMaxMCSpaces(maxMotorCycleSpaces);
                int maxQueueSize = Integer.parseInt(args[3]);
                gui.setTxtMaxQueueSize(maxQueueSize);
                int seed = Integer.parseInt(args[4]);
                gui.setTxtSeed(seed);
                double meanStay = Double.parseDouble(args[5]);
                gui.setTxtIntendedStayMean(meanStay);
                double sdStay = Double.parseDouble(args[6]);
                gui.setTxtIntendedStaySD(sdStay);
                double carProb = Double.parseDouble(args[7]);
                gui.setTxtCarProb(carProb);
                double smallCarProb = Double.parseDouble(args[8]);
                gui.settxtSmallCarProb(smallCarProb);
                double mcProb =Double.parseDouble(args[9]);
                gui.setTxtMCProb(mcProb);
            } else if (args.length > 0 && args.length < 10) {
                carPark = new CarPark();
                sim = new Simulator();
                log = new Log();
                runner = new SimulationRunner(carPark, sim, log);
                runner.runGUISimulation(gui);
                gui.btnStartSim.setEnabled(false);
            }
            gui.setVisible(true);
	}

}
