package frc.team1699.subsystems;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WristTest {

    private WristSim simWrist = WristSim.getInstance();
    final static double goal = 20.0;

    @Test
    void testWristModel(){
        Wrist wrist = Wrist.getInstance();
        wrist.setGoal(goal);

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("dump.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write("# time, angle, voltage, aVel, aAcceleration, goal, limitSensor, lastError\n");

        double currentTime = 0.0;
        while(currentTime < 30.0) {
            final double voltage = wrist.update(simWrist.encoder(), simWrist.limitTriggered());
            pw.write(String.format("%f, %f, %f, %f, %f, %f, %f, %f\n", currentTime, simWrist.angle, voltage, simWrist.aVel, simWrist.getAcceleration(voltage), wrist.getFilteredGoal(), simWrist.limitTriggered() ? 1.0 : 0.0, wrist.lastError));
            simulateTime(voltage, WristSim.kDt);
            currentTime += WristSim.kDt;
            pw.flush();
        }

        pw.close();

        assertEquals(goal, simWrist.angle, 0.5);
    }

    void simulateTime(final double voltage, final double time){
        assertTrue(voltage <= 12.0 && voltage >= -12.0, String.format("System asked for: %f volts which is greater than 12.0", voltage));
        final double kSimTime = 0.0001;

        double currentTime = 0.0;
        while(currentTime < time){
            final double acceleration = simWrist.getAcceleration(voltage);
            simWrist.angle += simWrist.aVel * kSimTime;
            simWrist.aVel += acceleration * kSimTime;
            currentTime += kSimTime;
            if(simWrist.limitTriggered()){
                assertTrue(simWrist.aVel > -8.0, String.format("System running at %f rpm which is less than -8.0", simWrist.aVel));
            }
            assertTrue(simWrist.angle >= Wrist.kMinAngle - 1.0, String.format("System is at %f meters which is less than minimum angle of %f", simWrist.angle, Wrist.kMinAngle));
            assertTrue(simWrist.angle <= Wrist.kMaxAngle + 1.0, String.format("System is at %f meters which is greater than the maximum angle of %f", simWrist.angle, Wrist.kMaxAngle));
        }
    }
}
