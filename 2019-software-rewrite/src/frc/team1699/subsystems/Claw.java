package frc.team1699.subsystems;

public class Claw {

    private static Claw instance;

    public static Claw getInstance(){
        if(instance == null){
            instance = new Claw();
        }
        return instance;
    }

    enum State{
        WAITING,
        INTAKING,
        OUTTAKING
    }

    private State currentState;
    private State demandedState;

    private Claw(){
        currentState = State.WAITING;
    }

    public void update(){

    }

    public void setDemandedState(final State demandedState){
        this.demandedState = demandedState;
    }

    public State getCurrentState(){
        return currentState;
    }
}
