package frc.team1699.subsystems;

import frc.team1699.utils.Utils;

public class Wrist implements ClosedLoopSubsystem {

    //Vars and methods to make this class a singleton
    private static Wrist instance;

    public static Wrist getInstance(){
        if(instance == null){
            instance = new Wrist();
        }
        return instance;
    }

    //The states that the system can be in
    enum State {
        UNINITIALIZED,
        ZEROING,
        RUNNING,
        ESTOPPED
    }

    //The rate at which the system is updated
    static double kDt = 0.05;

    //Zeroing velocity in rpm TODO Check units
    static double kZeroingAVelocity = 0.5;

    //Max Angle
    static double kMaxAngle = 60.0; //TODO Modify

    //Min Angle
    static double kMinAngle = 0.0; //TODO Modify

    //Max voltage to be applied
    static double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static double Kp = 7.0;
    static double Kv = 21.0;

    private Wrist(){}

    //The current goal
    double goal_ = 0.0;
    //The current system state
    private State state = State.UNINITIALIZED;
    //Encoder offset
    double offset = 0.0;
    //The last error in the system
    double lastError = 0.0;
    //A filtered goal. Used when zeroing
    double filteredGoal = 0.0;

    @Override
    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        double position = encoder + offset;
        switch (state){
            case UNINITIALIZED: //Uninitialized
                if (enabled){
                    state = State.ZEROING;
                    filteredGoal = position;
                }
                break;
            case ZEROING: //Zeroing
                filteredGoal -= kDt * kZeroingAVelocity;
                if (limitTriggered) {
                    state = State.RUNNING;
                    offset = -encoder;
                    position = 0.0;
                }
                if (!enabled) {
                    state = State.UNINITIALIZED;
                }
                break;
            case RUNNING: //Running
                filteredGoal = goal_;
                break;
            case ESTOPPED: //EStopped

                break;
        }

        final double error = filteredGoal - position;
        final double vel = (error - lastError) / kDt;
        lastError = error;
        double voltage = Kp * error + Kv * vel;

        final double maxVoltage = state == State.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            return maxVoltage;
        }else return Math.max(voltage, -maxVoltage);
    }

    @Override
    public void setGoal(double goal) {
        if(goal > kMaxAngle) {
            goal_ = kMaxAngle;
        }else goal_ = Math.max(goal, kMinAngle);
    }

    @Override
    public boolean atGoal() {
        return Utils.epsilonEquals(lastError, 0, 5); //TODO Check epsilon
    }

    public double getGoal() {
        return goal_;
    }

    public double getFilteredGoal() {
        return filteredGoal;
    }
}
