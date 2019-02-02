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

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
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

@Autonomous (name = "AutoPath1:From_Depot_to_Opponent_Crater")
public class AutoPath1 extends OpMode {

    private ArrayList<ICommand> commands;
    private int currentIndex;
    private Drive drive;
    private Climb climber;
    private Arm arm;
    private Intake intake;
    private boolean climbed;
    private ClimbCommand climbingCommand;
    private ArmCommand initArmCommand;
    private IntakeCommand intakeCommand;
    BNO055IMU imu;
    public String version;

    @Override
    public void init() {
        version = "2.0";
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
        intake = new Intake(intakeMotor,intakeServo, intakeServo2);
        arm = new Arm(armMotor, sensor);

        commands = new ArrayList<ICommand>();
        commands.add(new ClimbCommand(climber, true));
        commands.add(new IntakeCommand(intake, -1, .6,.4));
        commands.add(new DriveCommand(drive, 2400, 1));
        commands.add(new DriveCommand(drive, 2400, 1));
        commands.add(new IntakeCommand(intake, 0,-1,1));
        commands.add(new DriveCommand(drive, 3250, -1));
        commands.add(new TurnCommand(drive, 1, 95, imu));
        //commands.add(new DriveCommand(drive,4300,1));
        commands.add(new DriveCommand(drive, 3250, 1));
        //commands.add(new TurnCommand(drive, 1 , , imu));
        //commands.add(new DriveCommand(drive, 2150, 1));
        commands.add(new IntakeCommand(intake, .2,.6,.4));

        climbingCommand = new ClimbCommand(climber, false);
        currentIndex = 0;
        climbed = false;
        telemetry.addData("Status", "v:1.5");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("Version", version);
        telemetry.addData("Left Encoders: ", drive.GetLeftEncoders());
        telemetry.addData("Right Encoders: ", drive.GetRightEncoders());
        telemetry.addData("Angle: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES));
        telemetry.update();
        if (currentIndex < commands.size()) {
            if (commands.get(currentIndex).runCommand()) {
                currentIndex++;
                Drive.reset(drive);

            }
        }
    }
}
