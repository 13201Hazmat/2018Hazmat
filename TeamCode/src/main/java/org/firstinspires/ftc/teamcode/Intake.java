package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;


public class Intake{
    private Drive drive;
    private Gamepad controller;
    private DcMotor intakeR;
    private DcMotor intakeL;

    public Intake(Drive d, Gamepad c, DcMotor rIntake, DcMotor lIntake){
        drive = d;
        controller = c;
        intakeR = rIntake;
        intakeL = lIntake;
    }

    public void Update(){
        boolean intake = controller.a;

        if (intake) {
            intakeL.setPower(0.5);
            intakeR.setPower(-0.5);
        } else {
            intakeR.setPower(0.0);
            intakeL.setPower(0.0);
        }
    }
}