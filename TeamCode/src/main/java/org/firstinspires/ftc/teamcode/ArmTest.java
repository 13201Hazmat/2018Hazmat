package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad.*;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ArmTest")
public class ArmTest extends LinearOpMode {
    private DcMotor armMotor;
    private DcMotor intakeMotor1;
    private DcMotor intakeMotor2;
    private Gamepad controller;
    public void runOpMode(Gamepad c){
        armMotor = hardwareMap.dcMotor.get("armMotor");
        intakeMotor1 = hardwareMap.dcMotor.get("intakeMotor1");
        intakeMotor2 = hardwareMap.dcMotor.get("intakeMotor2");
        controller = c;

        waitForStart();
        while(opModeIsActive()){
        if (controller.x()) {
            intakeMotor1.setPower(0.7);
            intakeMotor2.setPower(-0.7);
        } else {
            intakeMotor1.setPower(0);
            intakeMotor2.setPower(0);
        }
        }
        sleep(500);

        //intakeMotor1.setPower(0);
        //intakeMotor2.setPower(0);
        //Reset Encoder
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setPower(-0.3);
        while (opModeIsActive()) {
            if (armMotor.getCurrentPosition() <= -500) {
                armMotor.setPower(0);
            }
        }
    }
}
