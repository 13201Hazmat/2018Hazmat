package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.teleOp.TeleArm;

@TeleOp(name = "ArmTest")
public class ArmTest extends OpMode {
    private TeleArm teleArm;

    @Override
    public void init() {
        DcMotor armMotor = hardwareMap.dcMotor.get("arm_motor");
        Arm arm = new Arm(armMotor);
        teleArm = new TeleArm(arm, gamepad1);
    }

    @Override
    public void loop() {
        teleArm.update();
    }
}
