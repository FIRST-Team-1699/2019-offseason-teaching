package frc.team1699.subsystems;

public class Elevator implements ClosedLoopSubsystem {

    private static Elevator instance;

    public static Elevator getInstance(){
        if(instance == null){
            instance = new Elevator();
        }
        return instance;
    }

    private Elevator(){

    }

    @Override
    public double update(double encoder, boolean limitTriggered) {
        return 0;
    }

    @Override
    public void setGoal(double goal) {

    }
}
