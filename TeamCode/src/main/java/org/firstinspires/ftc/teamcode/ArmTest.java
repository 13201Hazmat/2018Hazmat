package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ArmTest")
public class ArmTest extends LinearOpMode {
    private DcMotor armMotor;
    public void runOpMode() {
        armMotor = hardwareMap.dcMotor.get("armMotor");
        waitForStart();
        //Reset Encoder
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setTargetPosition(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setPower(-0.2);
        while (opModeIsActive()) {
            if (armMotor.getCurrentPosition() <= -300) {
                armMotor.setPower(0);
            }
        }
    }
}
