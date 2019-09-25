package frc.team1699.subsystems;

public class Claw {

    private static Claw instance;

    public static Claw getInstance(){
        if(instance == null){
            instance = new Claw();
        }
        return instance;
    }

    private Claw(){

    }
}
