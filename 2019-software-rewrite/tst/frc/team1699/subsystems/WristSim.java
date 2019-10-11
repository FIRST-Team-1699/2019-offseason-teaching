package frc.team1699.subsystems;

import static frc.team1699.utils.Constants.MotorDual775Pro.*;

//Code inspiration from: https://www.youtube.com/watch?v=uGtT8ojgSzg
public class WristSim {

    private static WristSim instance;

    public static WristSim getInstance() {
        if(instance == null){
            instance = new WristSim();
        }
        return instance;
    }

    //Rotational Inertia of Barrel Assembly in Newton * meters * meters
    static final double kInertia = 0.166708031 * 9.81; //TODO Calculate new value
    //Gear Ratio
    static final double kG = 5.33 * 10.71 * 2.5 * 2.5; //TODO Change ratio

    //Sample time
    public static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    private WristSim(){}

    //The current angle of the system in radians
    double angle = 0.1;
    //The angular velocity of the system in rad/sec TODO verify units
    double aVel = 0.0;
    //The offset of the system in radians
    double offset = 0.1;

    //Returns the acceleration of the system based on an applied voltage
    double getAcceleration(final double voltage){
        return (voltage - (aVel / Kv)) * ((kG * Kt) / (kInertia * kResistance));
    }

    //Returns true if the system would trigger a limit
    boolean limitTriggered(){
        return angle > -1.0 && angle < 0.0;
    }

    //Returns the encoder value of the system
    double encoder(){
        return angle + offset;
    }
}