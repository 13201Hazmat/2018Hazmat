package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="ChassisTest", group="Teleop")
public class TeleOpMode extends LinearOpMode {
    public void runOpMode () {
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        Servo phantomServo = null;

        Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        TeleDrive teleDrive = new TeleDrive(drive, gamepad1);

        Intake intake = new Intake(intakeMotor, phantomServo);
        TeleIntake teleIntake = new TeleIntake(intake, gamepad1);
        waitForStart();
        while(opModeIsActive()){
            teleDrive.Update();
            teleIntake.update();
        }
    }
}
