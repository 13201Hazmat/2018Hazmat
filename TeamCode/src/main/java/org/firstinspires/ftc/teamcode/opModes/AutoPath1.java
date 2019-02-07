package org.firstinspires.ftc.teamcode.opModes;

import java.util.ArrayList;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
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
import org.firstinspires.ftc.teamcode.subsystems.Vision;

@Autonomous(name = "AutoPath1:From_Depot_to_Opponent_Crater")
public class AutoPath1 extends OpMode {

    private ArrayList<ICommand> commands;
    private int currentIndex;
    private Drive drive;
    private Climb climber;
    private Arm arm;
    private Intake intake;
    private boolean climbed;
    private ClimbCommand climbCommand;
    private IntakeCommand intakeCommand;
    BNO055IMU imu;
    public String version;
    private int path;
    public Vision vision = new Vision();
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    public static final String VUFORIA_KEY = "AeZ0lyb/////AAABmR6FITE0NUyhk5ZOti1IBIJ3pIRPfIwcxUDngtgoPdAcQRq+mIIDpzP2P5dKIghdz9n0UFlZ3VRd0Bfdhzbk8FoP/aouesthpT0RG9oxteRIjLYPqMGd5CuKSzyTv7kkFWnb4X8vXmWEiu6jljfmZz1ReoV7orgI8LLslbW2XcSvRZMo6sVv1ahpdTJ9nUjhtxn26+EDUFfc9jDay12jGJFj97TLoCXv645WsnAQtc777IaYjTN/DbQXwR2aKptID98EI5iqioJkG6KZqove3Ft124KSnkqrMgEP8bmA0CoFmDQ324pz8VhFflJOb6me+r9K0Sd+amuv8PxRmT5UFEVnSZz9ZiW0Qu05F7bDWfHN";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;
    private boolean start1;
    private boolean start2;



    private void initVuforia() {

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;


        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    @Override
    public void init() {
        initVuforia();
        initTfod();
        version = "3.0";
        DcMotor FrontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor BackLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor FrontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor BackRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intake_motor");
        Servo intakeServo = hardwareMap.servo.get("right_intake_servo");
        Servo intakeServo2 = hardwareMap.servo.get("left_intake_servo");
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
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        drive = new Drive(FrontLeftMotor, BackLeftMotor, BackRightMotor, FrontRightMotor);
        Drive.reset(drive);
        climber = new Climb(climberMotor);
        intake = new Intake(intakeMotor, intakeServo, intakeServo2);
        arm = new Arm(armMotor, sensor);
        commands = new ArrayList<ICommand>();
        start1=true;
        start2=false;
        currentIndex=0;
        intakeCommand = new IntakeCommand(intake,0,.6,.4);
        climbCommand = new ClimbCommand(climber,true);
        path=-2;
    }

    @Override
    public void loop() {
        telemetry.addData("Version", version);
        telemetry.addData("Left Encoders: ", drive.GetLeftEncoders());
        telemetry.addData("Right Encoders: ", drive.GetRightEncoders());
        telemetry.addData("Angle: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES));
        path = vision.doVision(tfod);

        if(start1){
            intakeCommand.runCommand();
            if(climbCommand.runCommand()){
                start1=false;
                start2=true;
                telemetry.addData("DownDog","u know it");
                telemetry.update();
            }
        }
        if (start2) {
            //
            commands.add(new IntakeCommand(intake,-1,.6,.4));
            switch (path) {
                case 2:
                    commands.add(new DriveCommand(drive,200,1));
                    commands.add(new TurnCommand(drive, 1, -30, imu));
                    commands.add(new DriveCommand(drive, 2500, 1));
                    commands.add(new TurnCommand(drive, 1, 30, imu));
                    commands.add(new DriveCommand(drive, 2500, 1));
                    commands.add(new DriveCommand(drive, 2500, -1));
                    commands.add(new TurnCommand(drive, 1, -30, imu));
                    commands.add(new DriveCommand(drive, 2500, -1));
                    commands.add(new TurnCommand(drive,1,0,imu));
                    commands.add(new DriveCommand(drive,1500,1));
                    break;
                case 1:
                    commands.add(new DriveCommand(drive, 4800, 1));
                    commands.add(new IntakeCommand(intake, 0, -1, 1));
                    commands.add(new DriveCommand(drive, 3250, -1));
                    commands.add(new TurnCommand(drive,1,95,imu));
                    commands.add(new DriveCommand(drive, 3250, 1));
                    break;
                case -1:
                    commands.add(new DriveCommand(drive, 4800, 1));
                    commands.add(new IntakeCommand(intake, 0, -1, 1));
                    commands.add(new DriveCommand(drive, 3250, -1));
                    commands.add(new TurnCommand(drive,1,95,imu));
                    commands.add(new DriveCommand(drive, 3250, 1));
                    break;
                case 0:
                    commands.add(new DriveCommand(drive,200,1));
                    commands.add(new TurnCommand(drive, 1, 30, imu));
                    commands.add(new DriveCommand(drive, 3500, 1));
                    commands.add(new TurnCommand(drive, 1,-45, imu));
                    commands.add(new DriveCommand(drive, 200, 1));
                    commands.add(new DriveCommand(drive, 300, -1));
                    commands.add(new TurnCommand(drive, 1, 135, imu));
                    commands.add(new DriveCommand(drive, 4500, 1));
                    commands.add(new IntakeCommand(intake,.2,.6,.4));
                    commands.add(new TurnCommand(drive,1,115,imu));
                    break;
            }

            commands.add(new IntakeCommand(intake, .2, .6, .4));
            start2=false;
        }

        if (currentIndex < commands.size()) {
            if (commands.get(currentIndex).runCommand()) {
                telemetry.addData("Command Status", "finished command " + currentIndex);
                currentIndex++;
                Drive.reset(drive);
            }
        }
        telemetry.addData("PATH: ", path);
        telemetry.update();
    }
}
