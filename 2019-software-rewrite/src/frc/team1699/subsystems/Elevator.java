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

    static final double kDt = 0.05;

    //Zeroing velocity in m/sec
    static final double kZeroingVelocity = 0.01;

    //Max Height
    static final double kMaxHeight = 2.0;

    //Min Height
    static final double kMinHeight = 0.0;

    //Max voltage to be applied
    static final double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static final double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static final double Kp = 30.0;
    static final double Kv = 70.0;

    double goal_ = 0.0;
    private State state = State.UNINITIALIZED;
    double offset = 0.0;
    double lastError = 0.0;
    double filteredGoal = 0.0;

    private Elevator(){}

    @Override
    public double update(double encoder, boolean limitTriggered, boolean enabled) {
        double position = encoder + offset;

        switch (state){
            case UNINITIALIZED:
                if(enabled){
                    state = State.ZEROING;
                    filteredGoal = position;
                }
                break;
            case ZEROING:
                filteredGoal -= kDt * kZeroingVelocity;
                if(limitTriggered){
                    state = State.RUNNING;
                    offset -= encoder;
                    position = 0.0;
                }
                if(!enabled){
                    state = State.UNINITIALIZED;
                }
                break;
            case RUNNING:
                filteredGoal = goal_;
                break;
            case ESTOPPED:
                break;
        }

        final double error = filteredGoal - position;
        final double vel = (error - lastError) / kDt;
        lastError = error;
        double voltage = Kp * error + Kv * vel;

        final double maxVoltage = state == State.RUNNING ? kMaxVoltage : kMaxZeroingVoltage;

        if(voltage >= maxVoltage){
            return maxVoltage;
        }else {
            return Math.max(voltage, -maxVoltage);
        }
    }

    @Override
    public void setGoal(double goal) {
        if(goal > kMaxHeight) {
            goal_ = kMaxHeight;
        }else if(goal < kMinHeight){
            goal_ = kMinHeight;
        }else{
            goal_ = goal;
        }
    }

    public double getGoal() {
        return goal_;
    }

    public double getFilteredGoal() {
        return filteredGoal;
    }
}
