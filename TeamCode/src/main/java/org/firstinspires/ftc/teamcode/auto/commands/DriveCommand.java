package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class DriveCommand implements ICommand {
    private Drive drive;
    private int distance;
    private double power;
    private int currentDistance;
    public DriveCommand(Drive driveIn, int distance, double power){
        drive = driveIn;
        this.distance = distance;
        this.power = power;
    }
    @Override
    public boolean runCommand(){
        if (currentDistance < distance){
            drive.SetLeftMotors(power);
            drive.SetRightMotors(power);
            int LeftDistance = drive.GetLeftEncoders();
            int RightDistance = drive.GetRightEncoders();
            currentDistance = Math.abs((LeftDistance+RightDistance)/2);
            return false;
        }else {
            drive.SetLeftMotors(0);
            drive.SetRightMotors(0);
            return true;
        }
    }
}