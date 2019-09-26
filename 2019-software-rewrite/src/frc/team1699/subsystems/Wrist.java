package frc.team1699.subsystems;

public class Wrist implements ClosedLoopSubsystem {

    private static Wrist instance;

    public static Wrist getInstance(){
        if(instance == null){
            instance = new Wrist();
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

    //Zeroing velocity in rpm
    static double kZeroingAVelocity = 1.0;

    //Max Angle
    static double kMaxAngle = 60.0;

    //Min Angle
    static double kMinAngle = 0.0;

    //Max voltage to be applied
    static double kMaxVoltage = 12.0;

    //Max voltage when zeroing
    static double kMaxZeroingVoltage = 4.0;

    //Control loop constants
    static double Kp = 6.0;
    static double Kv = 14.0;

    private Wrist(){}

    double goal_ = 0.0;
    private State state = State.UNINITIALIZED;
    double offset = 0.0;
    double lastError = 0.0;
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

    public double getGoal() {
        return goal_;
    }

    public double getFilteredGoal() {
        return filteredGoal;
    }
}
