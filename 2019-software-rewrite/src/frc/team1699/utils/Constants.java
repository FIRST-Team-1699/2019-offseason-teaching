package frc.team1699.utils;

public class Constants {

    //Motor constants
    public class Motor775Pro{
        //Stall Torque in N*m
        public static final double kStallTorque = 0.71;
        //Stall Current in Amps
        public static final double kStallCurrent = 134.0;
        //Free Speed in RPMs
        public static final double kFreeSpeed = 18730.0;
        //Free Current in Amps
        public static final double kFreeCurrent = 0.7;
        //Resistance of the motor
        public static final double kResistance = 12.0 / kStallTorque;
        //Motor Velocity Constant
        public static final double Kv = ((kFreeSpeed / 60.0 * 2.0 * Math.PI) / (12.0 - kResistance * kFreeCurrent));
        //Torque constant
        public static final double Kt = kStallTorque / kStallCurrent;
    }

    //Joysticks
    public static final int kDriveJoystickPort = 0;
    public static final int kOperatorJoystickPort = 1;

    //Joystick Buttons
    //Wrist
    public static final int kClawUpButton = 0;
    public static final int kClawHatchPickupButton = 0;
    public static final int kClawBallPickupButton = 0;

    //CLaw
    public static final int kClawIntakeButton = 0;
    public static final int kClawOuttakeButton = 0;

    //Elevator
    public static final int kElevatorBallIntakeButton = 0;
    public static final int kElevatorHatchIntakeButton = 0;
    public static final int kLowHatchButton = 0;
    public static final int kMedHatchButton = 0;
    public static final int kHighHatchButton = 0;
    public static final int kCargoButton = 0;
    public static final int kBallOffsetButton = 0;

    //Motor Ports
    //Drive Train
    public static final int kPortMasterPort = 0;
    public static final int kPortSlavePort = 1;
    public static final int kStarMasterPort = 2;
    public static final int kStarSlavePort = 3;

    //Wrist
    public static final int kClawRotateMotorPort = 0;

    //Claw
    public static final int kClawIntakeMotorPortPort = 0;
    public static final int kClawIntakeMotorStarPort = 0;

    //Elevator
    public static final int kElevatorMasterPort = 0;
    public static final int kElevatorSlavePort = 0;

    //Setpoints
    //Wrist setpoints
    public static final double kClawUp = 0;
    public static final double kClawHatchPickup = 0;
    public static final double kClawBallPickup = 0;

    //Elevator setpoints
    public static final double kElevatorBallIntake = 0;
    public static final double kElevatorHatchIntake = 0;
    public static final double kLowHatch = 0;
    public static final double kMedHatch = 0;
    public static final double kHighHatch = 0;
    public static final double kBallCargoShip = 0;
    public static final double kBallOffset = 0;

    //Power constants
    //Claw power constants
    public static final double kBallIntakePower = 0.0;
    public static final double kBallOuttakePower = 0.0;
}
