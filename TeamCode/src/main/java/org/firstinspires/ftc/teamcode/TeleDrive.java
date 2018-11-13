package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class TeleDrive {
    private Drive drive;
    private Gamepad controller;
    public TeleDrive(Drive d, Gamepad c){
        drive = d;
        controller = c;
    }
    public void Update(){
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
