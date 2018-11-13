package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="ChassisIntakeTest", group="Teleop")
public class ChassisIntakeTest extends OpMode{
    private Drive drive;

    public void init() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("fl");
        DcMotor br = hardwareMap.dcMotor.get("fr");
        DcMotor fr = hardwareMap.dcMotor.get("fr");
        drive = new Drive(fl,bl,br,fr);
    }
    public void loop() {
        double throttle = gamepad1.left_stick_y;
        double direction = gamepad1.left_stick_x;
        double leftPower = 0;
        double rightPower = 0;

        if(Math.abs(throttle) > 0.1 || Math.abs(direction) > 0.1) {
            leftPower = throttle+direction;
            rightPower = throttle-direction;

            drive.SetLeftMotors(leftPower);
            drive.SetRightMotors(rightPower);
        } else {
            drive.SetLeftMotors(0.0);
            drive.SetRightMotors(0.0);
        }

        boolean intake = gamepad1.a;

        if (intake) {
            hardwareMap.dcMotor.get("intakeL").setPower(0.5);
            hardwareMap.dcMotor.get("intakeR").setPower(-0.5);
        } else {
            hardwareMap.dcMotor.get("intakeL").setPower(0.0);
            hardwareMap.dcMotor.get("intakeR").setPower(0.0);
        }
    }
}
