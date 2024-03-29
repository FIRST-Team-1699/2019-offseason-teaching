package frc.team1699.subsystems;

import static frc.team1699.utils.Constants.Motor775Pro.*;

//Code inspiration from: https://www.youtube.com/watch?v=uGtT8ojgSzg
public class WristSim {

    private static WristSim instance;

    public static WristSim getInstance() {
        if(instance == null){
            instance = new WristSim();
        }
        return instance;
    }

    //Rotational Inertia of Barrel Assembly in Kilograms * meters * meters
    static final double kInertia = 0.04441392; //TODO Calculate new value
    //Gear Ratio
    static final double kG = 100.0 * 60/12; //TODO Change ratio

    //Sample time
    public static final double kDt = 0.010;

    // V = I * R + ω / Kv
    // torque = Kt * I

    private WristSim(){}

    double angle = 5.0;
    double aVel = 0.0;
    double offset = 5.0;

    double getAcceleration(final double voltage){
        return (voltage - (aVel / Kv)) * ((kG * Kt) / (kInertia * kResistance));
    }

    boolean limitTriggered(){
        return angle > -1.0 && angle < 0.0;
    }

    double encoder(){
        return angle + offset;
    }
}