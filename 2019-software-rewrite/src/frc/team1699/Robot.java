package frc.team1699;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.team1699.subsystems.DriveTrain;
import frc.team1699.subsystems.Elevator;
import frc.team1699.subsystems.Wrist;
import frc.team1699.utils.Constants;

public class Robot extends TimedRobot {

    //Drive motor controllers
    private TalonSRX portMaster, portSlave, starMaster, starSlave;

    //Elevator motor controllers
    private TalonSRX elevatorMaster, elevatorSlave;

    //Wrist motor controllers
    private TalonSRX wristRotate;

    //Claw motor controllers
    private TalonSRX portIntake, starIntake;

    //Joysticks
    private Joystick driveJoystick, operatorJoystick;

    //Called when the robot first starts up
    @Override
    public void robotInit() {
        //Initialize Joysticks
        driveJoystick = new Joystick(Constants.kDriveJoystickPort);
        operatorJoystick = new Joystick(Constants.kOperatorJoystickPort);

        //Initialize drive motor controllers and set slaves to follow mode
        portMaster = new TalonSRX(Constants.kPortMasterPort);
        portSlave = new TalonSRX(Constants.kPortSlavePort);
        portSlave.follow(portMaster);
        starMaster = new TalonSRX(Constants.kStarMasterPort);
        starSlave = new TalonSRX(Constants.kStarSlavePort);
        starSlave.follow(starMaster);

        //Initialize elevator motor controllers and set slave to follow mode
        elevatorMaster = new TalonSRX(Constants.kElevatorMasterPort);
        elevatorSlave = new TalonSRX(Constants.kElevatorSlavePort);
        elevatorSlave.follow(elevatorMaster);

        //Initialize wrist motor controller
        wristRotate = new TalonSRX(Constants.kClawRotateMotorPort);

        //Initialize claw motor controllers
        portIntake = new TalonSRX(Constants.kClawIntakeMotorPortPort);
        starIntake = new TalonSRX(Constants.kClawIntakeMotorStarPort);
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    //Called periodically during teleop
    @Override
    public void teleopPeriodic() {
        //Control DriveTrain
        DriveTrain.DriveSignal currentDriveSignal = DriveTrain.getInstance().update(0, 0);
        portMaster.set(ControlMode.PercentOutput, currentDriveSignal.getPortVoltage());
        starMaster.set(ControlMode.PercentOutput, currentDriveSignal.getStarVoltage());

        //Control Elevator
        if(operatorJoystick.getRawButton(Constants.kElevatorHatchIntakeButton)){
            Elevator.getInstance().setGoal(Constants.kElevatorHatchIntake);
        }else if(operatorJoystick.getRawButton(Constants.kElevatorBallIntakeButton)){
            Elevator.getInstance().setGoal(Constants.kElevatorBallIntake);
        }else if(operatorJoystick.getRawButton(Constants.kLowHatchButton)){
            Elevator.getInstance().setGoal(Constants.kLowHatch);
        }else if(operatorJoystick.getRawButton(Constants.kMedHatchButton)){
            Elevator.getInstance().setGoal(Constants.kMedHatch);
        }else if(operatorJoystick.getRawButton(Constants.kHighHatchButton)){
            Elevator.getInstance().setGoal(Constants.kHighHatch);
        }else if(operatorJoystick.getRawButton(Constants.kCargoButton)){
            Elevator.getInstance().setGoal(Constants.kBallCargoShip);
        }else if(operatorJoystick.getRawButton(Constants.kLowHatchButton) && operatorJoystick.getRawButton(Constants.kBallOffsetButton)){
            Elevator.getInstance().setGoal(Constants.kLowHatch + Constants.kBallOffset);
        }else if(operatorJoystick.getRawButton(Constants.kMedHatchButton) && operatorJoystick.getRawButton(Constants.kBallOffsetButton)){
            Elevator.getInstance().setGoal(Constants.kMedHatch + Constants.kBallOffset);
        }else if(operatorJoystick.getRawButton(Constants.kHighHatchButton) && operatorJoystick.getRawButton(Constants.kBallOffsetButton)){
            Elevator.getInstance().setGoal(Constants.kHighHatch + Constants.kBallOffset);
        }

        //Updated position of the elevator
        elevatorMaster.set(ControlMode.PercentOutput, Elevator.getInstance().update(0.0, false, true));

        //Control Wrist
        if(operatorJoystick.getRawButton(Constants.kClawUpButton)){
            Wrist.getInstance().setGoal(Constants.kClawUp);
        }else if(operatorJoystick.getRawButton(Constants.kClawHatchPickupButton)){
            Wrist.getInstance().setGoal(Constants.kClawHatchPickup);
        }else if(operatorJoystick.getRawButton(Constants.kClawBallPickupButton)){
            Wrist.getInstance().setGoal(Constants.kClawBallPickup);
        }

        //Updated position of the wrist
        wristRotate.set(ControlMode.PercentOutput, Wrist.getInstance().update(0.0, false, true));

        //Control Claw
        if(operatorJoystick.getRawButton(Constants.kClawIntakeButton)){
            portIntake.set(ControlMode.PercentOutput, Constants.kBallIntakePower);
            starIntake.set(ControlMode.PercentOutput, Constants.kBallIntakePower);
        }else if(operatorJoystick.getRawButton(Constants.kClawOuttakeButton)){
            portIntake.set(ControlMode.PercentOutput, Constants.kBallOuttakePower);
            starIntake.set(ControlMode.PercentOutput, Constants.kBallOuttakePower);
        }else{
            portIntake.set(ControlMode.PercentOutput, 0.0);
            starIntake.set(ControlMode.PercentOutput, 0.0);
        }
    }
}
