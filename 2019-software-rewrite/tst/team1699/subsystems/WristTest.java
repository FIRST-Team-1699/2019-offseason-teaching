package team1699.subsystems;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WristTest {

    //Get an instance of WristSim
    private WristSim simWrist = WristSim.getInstance();
    //Set the goal of the simulation
    final static double goal = 1.0;

    //Models the wrist over time
    @Test
    void testWristModel(){
        //Get an instance of the wrist
        Wrist wrist = Wrist.getInstance();
        //Set the goal that we want the wrist to reach
        wrist.setGoal(goal);

        //Make a writer output information about the system
        PrintWriter pw = null;
        try {
            //Make a csv file called dump that we can write to
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Write header for csv file
        pw.write("# time, angle, voltage, aVel, aAcceleration, goal, limitSensor, lastError\n");

        //Set the current time to zero
        double currentTime = 0.0;
        //Run the simulation for 30 seconds
        while(currentTime < 30.0) {
            //Get the voltage based on encoder position and limit
            final double voltage = wrist.update(simWrist.encoder(), simWrist.limitTriggered(), true);
            //Write the current system state to csv
            pw.write(String.format("%f, %f, %f, %f, %f, %f, %f, %f\n", currentTime, simWrist.angle, voltage, simWrist.aVel, simWrist.getAcceleration(voltage), wrist.getFilteredGoal(), simWrist.limitTriggered() ? 1.0 : 0.0, wrist.lastError));
            //Simulate movement of the system
            simulateTime(voltage, WristSim.kDt);
            //Add to time
            currentTime += WristSim.kDt;
            //Write information to csv
            pw.flush();
        }

        //Close the writer to the csv
        pw.close();

        //Check to make sure that we reached out goal within half a degree
        assertEquals(goal, simWrist.angle, 0.5);
    }

    //Simulates time passing
    void simulateTime(final double voltage, final double time){
        //Makes sure that we are not requesting more voltage than the system can output
        assertTrue(voltage <= 12.0 && voltage >= -12.0, String.format("System asked for: %f volts which is greater than 12.0", voltage));
        //Var that helps with integrating to find position and velocity
        final double kSimTime = 0.0001;

        //The current time of the system
        double currentTime = 0.0;
        //Runs the time step
        while(currentTime < time){
            //Calculated the current acceleration of the system
            final double acceleration = simWrist.getAcceleration(voltage);
            //Calculates the angle of the wrist
            simWrist.angle += simWrist.aVel * kSimTime;
            //Calculated the velocity of the wrist
            simWrist.aVel += acceleration * kSimTime;
            //Adds to current time
            currentTime += kSimTime;
            //Check if the limit have been triggered
            if(simWrist.limitTriggered()){
                //Make sure that we are not going too fast when we run into the limit
                assertTrue(simWrist.aVel > -0.5, String.format("System running at %f rad/sec which is less than -0.5", simWrist.aVel));
            }
            //Check that we are not less than the min angle
            assertTrue(simWrist.angle >= Wrist.kMinAngle - 0.1, String.format("System is at %f radians which is less than minimum angle of %f", simWrist.angle, Wrist.kMinAngle));
            //Check that we are not greater than the max angle
            assertTrue(simWrist.angle <= Wrist.kMaxAngle + 0.1, String.format("System is at %f radians which is greater than the maximum angle of %f", simWrist.angle, Wrist.kMaxAngle));
        }
    }
}
