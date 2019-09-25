package frc.team1699.subsystems;

public class DriveTrain {

    private static DriveTrain instance;

    public static DriveTrain getInstance(){
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }

    private DriveTrain(){

    }
}
