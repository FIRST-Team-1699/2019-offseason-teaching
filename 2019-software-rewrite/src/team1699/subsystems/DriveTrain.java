package team1699.subsystems;

public class DriveTrain {

    //Vars and methods to make this a singleton
    private static DriveTrain instance;

    public static DriveTrain getInstance(){
        if(instance == null){
            instance = new DriveTrain();
        }
        return instance;
    }

    private DriveTrain(){}

    //WPILib Differential Drive
    public DriveSignal update(double joystickX, double joystickY){
        double portOutput = 0, starOutput = 0;

        //joystickX = applyDeadband(joystickX, m_deadband);
        //joystickY = applyDeadband(joystickY, m_deadband);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        joystickX = Math.copySign(joystickX * joystickX, joystickX);
        joystickY = Math.copySign(joystickY * joystickY, joystickY);

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(joystickX), Math.abs(joystickY)), joystickX);

        if (joystickX >= 0.0) {
            // First quadrant, else second quadrant
            if (joystickY >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = joystickX - joystickY;
            } else {
                leftMotorOutput = joystickX + joystickY;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (joystickY >= 0.0) {
                leftMotorOutput = joystickX + joystickY;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = joystickX - joystickY;
            }

        }

        return new DriveSignal(leftMotorOutput, rightMotorOutput);
    }

    //Stores the port and starboard power settings for the motors
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
