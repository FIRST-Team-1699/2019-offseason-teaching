package frc.team1699;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.team1699.subsystems.DriveTrain;

public class Robot extends TimedRobot {

    private TalonSRX portMaster, portSlave, starMaster, starSlave;

    @Override
    public void robotInit() {
        portMaster = new TalonSRX(0);
        portSlave = new TalonSRX(1);
        portSlave.follow(portMaster);
        starMaster = new TalonSRX(2);
        starSlave = new TalonSRX(3);
        starSlave.follow(starMaster);
    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopPeriodic() {
        //Control DriveTrain
        DriveTrain.DriveSignal currentDriveSignal = DriveTrain.getInstance().update(0, 0);
        portMaster.set(ControlMode.PercentOutput, currentDriveSignal.getPortVoltage());
        starMaster.set(ControlMode.PercentOutput, currentDriveSignal.getStarVoltage());

        //Control Elevator

        //Control Wrist

        //Control Claw
    }

    @Override
    public void testPeriodic() {
        
    }
}
