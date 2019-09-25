package frc.team1699.subsystems;

public interface ClosedLoopSubsystem {
    double update(double encoder, boolean limitTriggered);
    void setGoal(double goal);
}
