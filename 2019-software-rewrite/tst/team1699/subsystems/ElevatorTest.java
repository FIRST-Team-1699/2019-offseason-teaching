package team1699.subsystems;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTest {

    private ElevatorSim simElevator = ElevatorSim.getInstance();
    final static double goal = 1.0;

    @Test
    void testElevatorModel(){
        Elevator elevator = Elevator.getInstance();
        elevator.setGoal(goal);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write("# time, position, voltage, velocity, acceleration, goal, limitSensor, lastError\n");

        double currentTime = 0.0;
        while(currentTime < 30.0) {
            final double voltage = elevator.update(simElevator.encoder(), simElevator.limitTriggered(), true);
            pw.write(String.format("%f, %f, %f, %f, %f, %f, %f, %f\n", currentTime, simElevator.position, voltage, simElevator.velocity, simElevator.getAcceleration(voltage), elevator.getFilteredGoal(), simElevator.limitTriggered() ? 1.0 : 0.0, elevator.lastError));
            simulateTime(voltage, ElevatorSim.kDt);
            currentTime += ElevatorSim.kDt;
            pw.flush();
        }

        pw.close();

        assertEquals(simElevator.position, goal, 0.01);
    }

    void simulateTime(final double voltage, final double time){
        assertTrue(voltage <= 12.0 && voltage >= -12.0, String.format("System asked for: %f volts which is greater than 12.0", voltage));
        final double kSimTime = 0.0001;

        double currentTime = 0.0;
        while(currentTime < time){
            final double acceleration = simElevator.getAcceleration(voltage);
            simElevator.position += simElevator.velocity * kSimTime;
            simElevator.velocity += acceleration * kSimTime;
            currentTime += kSimTime;
            if(simElevator.limitTriggered()){
                assertTrue(simElevator.velocity > -0.05, String.format("System running at %f m/s which is less than -0.051", simElevator.velocity));
            }
            assertTrue(simElevator.position >= Elevator.kMinHeight - 0.01, String.format("System is at %f meters which is less than minimum height of %f", simElevator.position, Elevator.kMinHeight));
            assertTrue(simElevator.position <= Elevator.kMaxHeight + 0.01, String.format("System is at %f meters which is greater than the maximum height of %f", simElevator.position, Elevator.kMaxHeight));
        }
    }
}
