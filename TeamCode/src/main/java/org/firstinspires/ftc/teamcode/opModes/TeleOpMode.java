package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Climb;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.teleOp.TeleArm;
import org.firstinspires.ftc.teamcode.teleOp.TeleClimb;
import org.firstinspires.ftc.teamcode.teleOp.TeleDrive;
import org.firstinspires.ftc.teamcode.teleOp.TeleIntake;

@TeleOp(name = "TeleOpMode", group = "Teleop")
public class TeleOpMode extends LinearOpMode {
    public void runOpMode() {

        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        DcMotor armMotor = hardwareMap.dcMotor.get("arm_motor");
        Servo intakeServo = hardwareMap.servo.get("right_intake_servo");
        Servo intakeServo2 = hardwareMap.servo.get("left_intake_servo");
        TouchSensor sensor = hardwareMap.touchSensor.get("touch_sensor");
        //DcMotor climber = hardwareMap.dcMotor.get("lift_motor");

        Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        TeleDrive teleDrive = new TeleDrive(drive, gamepad1);


        Intake intake = new Intake(intakeMotor, intakeServo, intakeServo2);
        TeleIntake teleIntake = new TeleIntake(intake, gamepad1);

        Arm arm = new Arm(armMotor, sensor);
        TeleArm teleArm = new TeleArm(arm, gamepad1);

        //Climb climb = new Climb(climber);
        //TeleClimb teleClimb = new TeleClimb(climb, gamepad1);

        waitForStart();
        while (opModeIsActive()) {
            teleDrive.update();
            teleIntake.update();
            teleArm.update();
            //teleClimb.update();
            telemetry.addData("servo 1", intakeServo.getPosition());
            telemetry.addData("servo 2", intakeServo2.getPosition());
            telemetry.addData("touch sensor", sensor.isPressed());
            telemetry.update();
        }
    }
}
