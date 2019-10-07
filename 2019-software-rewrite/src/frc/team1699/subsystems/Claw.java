package frc.team1699.subsystems;

import frc.team1699.utils.Constants;

public class Claw {

    //Vars and methods to make this a singleton
    private static Claw instance;

    public static Claw getInstance(){
        if(instance == null){
            instance = new Claw();
        }
        return instance;
    }

    //Enum representing the states the system can be in
    public enum State{
        WAITING,
        INTAKING,
        OUTTAKING
    }

    //The current system state
    private State systemState;

    private Claw(){
        systemState = State.WAITING;
    }

    //Returns the voltage to be applied to the intake motors based on the system state
    public double update(){
        switch (systemState) {
            case INTAKING:
                return Constants.kBallIntakePower;
            case OUTTAKING:
                return Constants.kBallOuttakePower;
            default:
                return 0.0;
        }
    }

    //Sets the current system state
    public void setSystemState(final State systemState){
        this.systemState = systemState;
    }

    //Returns the current system state
    public State getSystemState(){
        return systemState;
    }
}
