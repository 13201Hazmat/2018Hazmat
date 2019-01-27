package org.firstinspires.ftc.teamcode.auto.commands;

import org.firstinspires.ftc.teamcode.auto.ICommand;

public class WaitCommand implements ICommand {
    private long toTime;
    public WaitCommand(long addTime){
        toTime=System.currentTimeMillis()+addTime;
    }

    public boolean runCommand(){
        return toTime-System.currentTimeMillis()<0;
    }
}
