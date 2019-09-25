package frc.team1699.subsystems;

public class Elevator implements ClosedLoopSubsystem {

    private static Elevator instance;

    public static Elevator getInstance(){
        if(instance == null){
            instance = new Elevator();
        }
        return instance;
    }

    enum State {
        UNINITIALIZED,
        ZEROING,
        RUNNING,
        ESTOPPED
    }

    static double kDt = 0.05;

    //Zeroing velocity in m/sec
    static double kZeroingVelocity = 0.01;

    //Max Height
    static double kMaxHeight = 2.0;

    //Min Height
    static double kMinHeight = 0.0;

    //Max voltage to be applied
    static double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static double Kp = 30.0;
    static double Kv = 70.0;

    double goal_ = 0.0;
    private State state = State.UNINITIALIZED;
    double offset = 0.0;
    double lastError = 0.0;
    double filteredGoal = 0.0;

    private Elevator(){}

    @Override
    public double update(double encoder, boolean limitTriggered) {
        return 0;
    }

    @Override
    public void setGoal(double goal) {

    }

    public double getGoal() {
        return goal_;
    }

    public double getFilteredGoal() {
        return filteredGoal;
    }
}
