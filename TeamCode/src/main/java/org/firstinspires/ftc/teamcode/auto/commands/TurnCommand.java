package org.firstinspires.ftc.teamcode.auto.commands;

import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.auto.ICommand;

public class TurnCommand implements ICommand {
    private Drive drive;
    private double power;
    private double angle;
    private BNO055IMU imu;

    public TurnCommand(Drive driveIn, double power, double angle, BNO055IMU imu) {
        drive = driveIn;
        this.power = power;
        this.angle = angle;
        this.imu = imu;
    }

    @Override
    public boolean runCommand() {
        Orientation location = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        float currentAngle = location.firstAngle;
        if (currentAngle < angle) {
            drive.SetLeftMotors(power);
            drive.SetRightMotors(-power);
            return false;
        } else if (currentAngle > angle) {
            drive.SetLeftMotors(-power);
            drive.SetRightMotors(power);
            return false;
        } else {
            drive.SetLeftMotors(0);
            drive.SetRightMotors(0);
            return true;
        }
    }
}
