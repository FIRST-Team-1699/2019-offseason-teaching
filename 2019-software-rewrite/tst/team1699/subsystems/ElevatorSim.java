package team1699.subsystems;

import static team1699.utils.Constants.Motor775Pro.*;

public class ElevatorSim {

    //Vars and methods that make this a singleton
    private static ElevatorSim instance;

    public static ElevatorSim getInstance() {
        if(instance == null){
            instance = new ElevatorSim();
        }
        return instance;
    }

    //Distance from center of rotation in meters
    static final double cg = 0.0;
    //Mass of Barrel Assembly in Kilograms
    static final double kMass = 20.0;
    //Gear Ratio
    static final double kG = 100.0 * 60/12;
    //Radius of pulley
    static final double kr = 0.25 * 0.0254 * 22.0 / Math.PI / 2.0;

    //Sample time
    public static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    ElevatorSim(){}

    //The current position of the simulation
    double position = 0.1;
    //The velocity of the simulation in m/s
    double velocity = 0.0;
    //The offset
    double offset = 0.1;

    //Returns the acceleration based on the voltage applied to the system
    double getAcceleration(final double voltage){
        return -Kt * kG * kG / (Kv * kResistance * kr * kr * kMass) * velocity + kG * Kt / (kResistance * kr * kMass) * voltage;
    }

    //Returns true if the system would trigger a limit
    boolean limitTriggered(){
        return position > -0.04 && position < 0.0;
    }

    //Returns the theoretical encoder position of the system
    double encoder(){
        return position + offset;
    }
}
