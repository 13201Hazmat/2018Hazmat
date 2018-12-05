package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.teleOp.TeleDrive;
import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.auto.commands.DriveCommand;

@Autonomous

public class AutoOpMode extends OpMode {
    private Drive drive;
    private TeleDrive teleDrive;
    private Intake intake;
    private DcMotor intakeR;
    private DcMotor intakeL;
    private ICommand[] commands = new ICommand[1];
    private int indexCommand = 0;

    public void init() {
        DcMotor fl = hardwareMap.dcMotor.get("fl");
        DcMotor bl = hardwareMap.dcMotor.get("fl");
        DcMotor br = hardwareMap.dcMotor.get("fr");
        DcMotor fr = hardwareMap.dcMotor.get("fr");

        /* intakeR = hardwareMap.dcMotor.get("intakeR");
        intakeL = hardwareMap.dcMotor.get("intakeL"); */

        drive = new Drive(fl, bl, br, fr);
        DriveCommand temp = new DriveCommand(drive, 1000, 1);
        commands[0] = temp;
        // intake = new Intake(drive, gamepad1, intakeR, intakeL);
    }

    public void loop() {
        if (indexCommand < commands.length) {
            if (commands[indexCommand].runCommand()) {
                indexCommand += 1;
            }
        }
    }
}
