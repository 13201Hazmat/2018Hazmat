package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

public class IntakeCommand implements ICommand {
    private Intake intake;
    private double power;
    private double leftPosition;
    private double rightPosition;
    public IntakeCommand(Intake intake, double power, double leftPosition, double rightPosition) {
        this.intake = intake;
        this.power = power;
        this.leftPosition = leftPosition;
        this.rightPosition = rightPosition;
    }

    @Override
    public boolean runCommand() {
        intake.setIntakePosition(leftPosition,rightPosition);
        intake.setIntakeSpeed(power);
        return true;
    }
}
