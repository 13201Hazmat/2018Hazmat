public class CommandFactory{
    public static ICommand createCommand(String[] args, CommandEnum command){

    }
}

private class DriveCommand implements ICommand{
    private Drive drive;
    private int distance;
    private double power;
    private int currentDistance;
    public DriveCommnand(Drive drive, int distance, double power){
        this.drive = drive;
        this.distance = distance;
        this.power = power;
    }
    public boolean runCommand(){
        if (currentDistance < distance){
            drive.SetLeftMotors(power);
            drive.SetRightMotors(power);
            int LeftDistance = drive.GetLeftEncoders();
            int RightDistance = drive.GetRightEncoders();
            currentDistance = Math.abs((LeftDistance+RightDistance)/2);
            return false;
        }else {
            drive.SetLeftMotors(0);
            drive.SetRightMotors(0);
            return true;
        }
    }
}