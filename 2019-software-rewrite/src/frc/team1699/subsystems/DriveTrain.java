package frc.team1699.subsystems;

public class DriveTrain {

    private static DriveTrain instance;

    public static DriveTrain getInstance(){
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }

    private DriveTrain(){}

    public DriveSignal update(double joystickX, double joystickY){
        return null;
    }

    public static class DriveSignal{
        private double portVoltage;
        private double starVoltage;

        DriveSignal(final double portVoltage, final double starVoltage){
            this.portVoltage = portVoltage;
            this.starVoltage = starVoltage;
        }

        public double getPortVoltage(){
            return portVoltage;
        }

        public double getStarVoltage(){
            return starVoltage;
        }
    }
}
