package frc.team1699.subsystems;

//Represents a subsystem that will use closed loop control
public interface ClosedLoopSubsystem {
    //Returns the voltage needed to update the subsystem
    double update(double encoder, boolean limitTriggered, boolean enabled);

    //Sets the goal of the subsystem
    void setGoal(double goal);

    //Returns true if the subsystem is at its goal
    boolean atGoal();
}
