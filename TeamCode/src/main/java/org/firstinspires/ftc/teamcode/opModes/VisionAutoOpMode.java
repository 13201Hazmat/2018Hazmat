package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.auto.commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;
import org.firstinspires.ftc.teamcode.subsystems.Climb;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.util.HashMap;

@Autonomous(name = "Good Auto")
public class VisionAutoOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Vision vision = new Vision();
        vision.init(hardwareMap);

        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        DcMotor armMotor = hardwareMap.dcMotor.get("arm_motor");
        Servo intakeServo = hardwareMap.servo.get("right_intake_servo");
        Servo intakeServo2 = hardwareMap.servo.get("left_intake_servo");
        TouchSensor sensor = hardwareMap.touchSensor.get("touch_sensor");
        DcMotor climber = hardwareMap.dcMotor.get("lift_motor");
        GyroSensor gyro = hardwareMap.gyroSensor.get("gyro");

        Drive drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        Intake intake = new Intake(intakeMotor, intakeServo, intakeServo2);
        Climb climb = new Climb(climber);

        ClimbCommand climbCommand = new ClimbCommand(climb, true);
        climbCommand.runCommand();

        HashMap<String, Integer> targetToAngle = new HashMap<String, Integer>();
        targetToAngle.put("Front-Craters", 0);
        targetToAngle.put("Red-Footprint", 90);
        targetToAngle.put("Back-Space", 180);
        targetToAngle.put("Blue-Rover", 270);

        vision.scan();
        String target = vision.getVisibleTarget();
        double targetAngle = targetToAngle.get(target);


        double angle = vision.getRobotAngle();
        TurnCommand turnCommand = new TurnCommand(drive, 0.7, targetAngle - angle, gyro);
        turnCommand.runCommand();

        if (target.equals("Blue-Rover") || target.equals("Red-Footprint")) {
            runPath1();
        } else {
            runPath2();
        }
    }

    private static void runPath1() {

    }

    private static void runPath2() {

    }
}
