package org.firstinspires.ftc.teamcode.opModes;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.auto.commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;
import org.firstinspires.ftc.teamcode.subsystems.Climb;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Vision;

import java.util.ArrayList;
import java.util.HashMap;

@Autonomous(name = "Good Auto")
public class VisionAutoOpMode extends LinearOpMode {
    private Drive drive;
    private Intake intake;
    private Climb climb;
    private GyroSensor gyro;

    @Override
    public void runOpMode() throws InterruptedException {
        Vision vision = new Vision();
        vision.init(hardwareMap);

        //Motors and stuff
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        Servo intakeServo = hardwareMap.servo.get("right_intake_servo");
        Servo intakeServo2 = hardwareMap.servo.get("left_intake_servo");
        TouchSensor sensor = hardwareMap.touchSensor.get("touch_sensor");
        DcMotor climber = hardwareMap.dcMotor.get("lift_motor");
        gyro = hardwareMap.gyroSensor.get("gyro");

        //Subsystems
        drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        intake = new Intake(intakeMotor, intakeServo, intakeServo2);
        climb = new Climb(climber);

        //Get down from the lander
        ClimbCommand climbCommand = new ClimbCommand(climb, true);
        climbCommand.runCommand();

        //Associating orientation angles to the targets on the field
        HashMap<String, Integer> targetToAngle = new HashMap<String, Integer>();
        targetToAngle.put("Front-Craters", 0);
        targetToAngle.put("Red-Footprint", 90);
        targetToAngle.put("Back-Space", 180);
        targetToAngle.put("Blue-Rover", 270);

        //Find the target angle using the visible target
        vision.scan();
        String target = vision.getVisibleTarget();
        double targetAngle = targetToAngle.get(target);

        telemetry.addData("Visible Target", target);
        telemetry.addData("Rot (deg)", vision.getRobotAngle());

        /**Turn to the correct angle
        double angle = vision.getRobotAngle();
        TurnCommand turnCommand = new TurnCommand(drive, 0.7, targetAngle - angle, gyro);
        turnCommand.runCommand();

        //Run one of two paths now that the robot is aligned
        if (target.equals("Blue-Rover") || target.equals("Red-Footprint")) { //If camera is looking 45 degrees to the right, flip if to the left
            runPath1();
        } else {
            runPath2();
        }
         **/
    }

    //Closest to our own depot
    private void runPath1() {
        ArrayList<ICommand> commands = new ArrayList<ICommand>();
        int commandIndex = 0;



        commands.add(new DriveCommand(drive, 69, 0.7)); //Drive into depot
        //Dump marker
        /**
         commands.add(new TurnCommand(drive, 0.7, 45, gyro)); //Turn towards opponent side crater
         commands.add(new DriveCommand(drive, 169, 0.7)); //Drive
         */
        while (commandIndex < commands.size()) {
            if (commands.get(commandIndex).runCommand()) {
                commandIndex++;
            }
        }
    }

    //Closest to crater
    private void runPath2() {
        ArrayList<ICommand> commands = new ArrayList<ICommand>();
        int commandIndex = 0;

        /**
        commands.add(new TurnCommand(drive, 0.7, 90, gyro)); //Turn toward depot
        commands.add(new DriveCommand(drive, 69, 0.7)); //Drive into depot
        //Drop marker
        commands.add(new TurnCommand(drive, 0.7, 45, gyro)); //Turn towards own team crater
        commands.add(new DriveCommand(drive, 169, 0.7)); //Drive into own team crater
        */
        commands.add(new DriveCommand(drive, 69, 0.7)); //Drive into crater
        while (commandIndex < commands.size()) {
            if (commands.get(commandIndex).runCommand()) {
                commandIndex++;
            }
        }
    }
}
