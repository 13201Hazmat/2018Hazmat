package org.firstinspires.ftc.teamcode.teleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.subsystems.Drive;

public class TeleDrive {
    private Drive drive;
    private Gamepad controller;
    public TeleDrive(Drive d, Gamepad c){
        drive = d;
        controller = c;
    }
    public void update(){
        double LeftJoyStickVal = controller.left_stick_y;
        double RightJoyStickVal = controller.right_stick_x;
        double powerLeft = LeftJoyStickVal;
        double powerRight = LeftJoyStickVal;
        powerLeft += RightJoyStickVal;
        powerRight -= RightJoyStickVal;
        drive.SetLeftMotors(-powerLeft);
        drive.SetRightMotors(powerRight);
    }
}
