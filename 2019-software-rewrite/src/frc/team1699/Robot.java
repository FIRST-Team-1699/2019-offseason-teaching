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

    private TalonSRX portMaster, portSlave, starMaster, starSlave;
    private Joystick driveJoystick, operatorJoystick;

    //Called when the robot first starts up
    @Override
    public void robotInit() {
        driveJoystick = new Joystick(Constants.kDriveJoystickPort);
        operatorJoystick = new Joystick(Constants.kOperatorJoystickPort);

        portMaster = new TalonSRX(Constants.kPortMasterPort);
        portSlave = new TalonSRX(Constants.kPortSlavePort);
        portSlave.follow(portMaster);
        starMaster = new TalonSRX(Constants.kStarMasterPort);
        starSlave = new TalonSRX(Constants.kStarSlavePort);
        starSlave.follow(starMaster);
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
        Elevator.getInstance().update(0.0, false, true);

        //Control Wrist
        if(operatorJoystick.getRawButton(Constants.kClawUpButton)){
            Wrist.getInstance().setGoal(Constants.kClawUp);
        }else if(operatorJoystick.getRawButton(Constants.kClawHatchPickupButton)){
            Wrist.getInstance().setGoal(Constants.kClawHatchPickup);
        }else if(operatorJoystick.getRawButton(Constants.kClawBallPickupButton)){
            Wrist.getInstance().setGoal(Constants.kClawBallPickup);
        }

        //Updated position of the wrist
        Wrist.getInstance().update(0.0, false, true);

        //Control Claw
    }
}
