package org.firstinspires.ftc.teamcode.opModes;

import java.util.ArrayList;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
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
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AeZ0lyb/////AAABmR6FITE0NUyhk5ZOti1IBIJ3pIRPfIwcxUDngtgoPdAcQRq+mIIDpzP2P5dKIghdz9n0UFlZ3VRd0Bfdhzbk8FoP/aouesthpT0RG9oxteRIjLYPqMGd5CuKSzyTv7kkFWnb4X8vXmWEiu6jljfmZz1ReoV7orgI8LLslbW2XcSvRZMo6sVv1ahpdTJ9nUjhtxn26+EDUFfc9jDay12jGJFj97TLoCXv645WsnAQtc777IaYjTN/DbQXwR2aKptID98EI5iqioJkG6KZqove3Ft124KSnkqrMgEP8bmA0CoFmDQ324pz8VhFflJOb6me+r9K0Sd+amuv8PxRmT5UFEVnSZz9ZiW0Qu05F7bDWfHN";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;


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
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
            initVuforia();
        }

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
        commands.add(new IntakeCommand(intake, 0, false));
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

    public void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    public void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}