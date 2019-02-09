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
    private boolean lastDirection;
    private double p;
    private double e;

    public TurnCommand(Drive driveIn, double power, double angle, BNO055IMU imu) {
        drive = driveIn;
        this.power = power;
        this.angle = angle;
        this.imu = imu;
        p = .5;
        e = .1;
    }

    @Override
    public boolean runCommand() {
        Orientation location = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        float currentAngle = location.secondAngle;
        if (power < e) {
            drive.SetLeftMotors(0);
            drive.SetRightMotors(0);
            return true;
        }
        if (currentAngle > angle) {
            drive.SetLeftMotors(power);
            drive.SetRightMotors(power);
            if (!lastDirection) {
                power *= p;
            }
            lastDirection = true;
            return false;
        } else {
            drive.SetLeftMotors(-power);
            drive.SetRightMotors(-power);
            if (lastDirection) {
                power *= p;
            }
            lastDirection = false;
            return false;
        }
    }
}
