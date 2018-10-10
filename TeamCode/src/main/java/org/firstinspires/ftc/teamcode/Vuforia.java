package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;


@Autonomous(name = "Vufroria")
public class Vuforia extends LinearOpMode{

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AfH1Zl//////AAABmR491lMHykEBobd9/V5Ni4yNrRLaQsIdeGQ4B8qbKvPivDl2OuKmWe78D8/ZtKpaUqH8DbY4Z0uaKkxQVKinzPM7WrCpEKyV7ujG97N2Stb+nRAZ37IYIn67v1ol79c9rUcM/4JGy3sicrICs8WiEIhs/lnWhwKRZWnyi8cBxHddBv13O8UxzIhnzZsuHBYJ78e5V+kPXg5xbly/b24LPxxyt01ZZq7vvP0ipO759SbJlp8XO8Apn/V5jJT/W9YSQoaPY1Xpys+ka4e/LA0ONVNNE+8dQbvsx23OIOcZCoZaX62TRCj+sMUJ8pxjQUqEu8QOAkw87ZFkjBdGfKuCKovTpo89ziOs3z9ccZ4cbzAu"; //Insert Vuforia key here

    private float robotX = 0;
    private float robotY = 0;
    private float robotAngle = 0;

    public void runOpMode() throws InterruptedException{
        setUpVuforia();

        // Don't know where the robot is, so set it to origin
        // This causes it to not be null, preventing later errors
        lastKnownLocation = createMatrix(0, 0,0,0,0, 0);

        waitForStart();

        // Starts tracking the targets
        visionTargets.activate();

        while(opModeIsActive()){
            // Asks the listener for the latest information on where the robot is
            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            // Listeners might sometimes return null, this checks to prevents erros
            if (latestLocation != null){
                lastKnownLocation = latestLocation;
            }

            float[] coordinates = lastKnownLocation.getTranslation().getData();

            robotX = coordinates[0];
            robotY = coordinates[1];
            robotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            // Sends information about whether the target is visible, and where the robot is
            telemetry.addData("Tracking" + target.getName(), listener.isVisible());
            telemetry.addData("Last Known Location", formatMatrix(lastKnownLocation));

            // Sends telemetry and idle to let hardware catch up
            telemetry.update();
            idle();
        }
    }

    public void setUpVuforia(){
        // sets up parameters to create localizer
        parameters =  new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        // These are the vision targets
        // The string needs to be the name of the .xml in the assets folder
        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("FTC_2016-17");
        //Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);


        // Sets up target to be tracked
        target = visionTargets.get(0); // 0 is the wheels target
        target.setName("Wheels Target");
        target.setLocation(createMatrix(0,500,0,90,0,90));

        // Sets phone location on robot
        phoneLocation = createMatrix(0,225,0,90,0,0);

        // Sets up listener and informs it of phone information
        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation,parameters.cameraDirection);
    }

    // Creates a matrix for determining the location and orientation of objects
    // The units are millimeters for x, y, z and degrees for u, v, w
    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w) {
        return OpenGLMatrix.translation(x,y,z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u,v,w));
    }

    public String formatMatrix(OpenGLMatrix matrix){
        return matrix.formatAsTransform();
    }

}
