package org.firstinspires.ftc.teamcode.auto.commands;

import com.qualcomm.robotcore.hardware.GyroSensor;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.auto.ICommand;

public class TurnCommand implements ICommand {
    private Drive drive;
    private double power;
    private double angle;
    private GyroSensor gyro;

    public TurnCommand(Drive driveIn, double power, double angle, GyroSensor gyro) {
        drive = driveIn;
        this.power = power;
        this.angle = angle;
        this.gyro = gyro;
    }

    @Override
    public boolean runCommand() {
        double currentAngle = gyro.getRotationFraction();
        currentAngle *= 360;
        currentAngle %= 360;
        if (currentAngle < angle) {
            if (angle < 180) {
                drive.SetLeftMotors(-power);
                drive.SetRightMotors(power);
            } else if (angle > 180) {
                drive.SetLeftMotors(power);
                drive.SetRightMotors(-power);
            }
            return false;
        } else {
            drive.SetLeftMotors(0);
            drive.SetRightMotors(0);
            return true;
        }
    }
}
