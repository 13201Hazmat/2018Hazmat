package org.firstinspires.ftc.teamcode.opModes;

import java.util.ArrayList;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.auto.commands.ArmCommand;
import org.firstinspires.ftc.teamcode.auto.commands.ClimbCommand;
import org.firstinspires.ftc.teamcode.auto.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Climb;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.auto.commands.TurnCommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Autonomous
public class AutoPath1 extends OpMode {
    private ArrayList<ICommand> commands;
    private int currentIndex;
    private Drive drive;
    private Climb climber;
    private Arm arm;
    private Intake intake;
    private Servo intakeServo1;
    private Servo intakeServo2;
    private boolean climbed;
    private ClimbCommand climbingCommand;
    private ArmCommand initArmCommand;
    private IntakeCommand intakeCommand;
    public String version;

    @Override
    public void init() {
        version = "1.0";
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        intakeServo1 = hardwareMap.servo.get("right_intake_servo");
        intakeServo2 = hardwareMap.servo.get("left_intake_servo");
        DcMotor climberMotor = hardwareMap.dcMotor.get("lift_motor");
        DcMotor armMotor = hardwareMap.dcMotor.get("arm_motor");
        TouchSensor sensor = hardwareMap.touchSensor.get("touch_sensor");

        //
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        Drive.reset(drive);
        climber = new Climb(climberMotor);
        arm = new Arm(armMotor, sensor);
        intake = new Intake(intakeMotor, intakeServo1, intakeServo2);

        commands = new ArrayList<ICommand>();
        commands.add(new ClimbCommand(climber, true));
        commands.add(new DriveCommand(drive, 2650, 1));
        commands.add(new IntakeCommand(intake, -1.0, true));
        commands.add(new DriveCommand(drive, 2650, 1));
        commands.add(new IntakeCommand(intake, 0,false));
        commands.add(new DriveCommand(drive, 10, -1));
        commands.add(new DriveCommand(drive, 10, 1));
        commands.add(new TurnCommand(drive, 1, -48, imu));
        commands.add(new DriveCommand(drive, 8050, -1));

        climbingCommand = new ClimbCommand(climber, false);
        currentIndex = 0;
        climbed = false;
        /*armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armMotor.setTargetPosition(-60);
        armMotor.setPower(.5);*/
        telemetry.addData("Status", "Init");
        telemetry.update();
    }

    @Override
    public void loop() {
        arm.update();
        telemetry.addData("Left Encoders: ", drive.GetLeftEncoders());
        telemetry.addData("Right Encoders: ", drive.GetRightEncoders());
        telemetry.addData("Servo 1:", intakeServo1.getPosition());
        telemetry.addData("Servo 2:", intakeServo2.getPosition());
        telemetry.update();
        if (currentIndex > 0 && !climbed) {
            climbed = !climbingCommand.runCommand();
        }
        if (currentIndex < commands.size()) {
            if (commands.get(currentIndex).runCommand()) {
                currentIndex++;
                Drive.reset(drive);

            }
        }
    }
}
