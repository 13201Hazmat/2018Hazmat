package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

public class Vision extends LinearOpMode {
    // Licence key in order to utilize the Vuforia methods and objects
    private static final String VUFORIA_KEY = "AfH1Zl//////AAABmR491lMHykEBobd9/V5Ni4yNrRLaQsIdeGQ4B8qbKvPivDl2OuKmWe78D8/ZtKpaUqH8DbY4Z0uaKkxQVKinzPM7WrCpEKyV7ujG97N2Stb+nRAZ37IYIn67v1ol79c9rUcM/4JGy3sicrICs8WiEIhs/lnWhwKRZWnyi8cBxHddBv13O8UxzIhnzZsuHBYJ78e5V+kPXg5xbly/b24LPxxyt01ZZq7vvP0ipO759SbJlp8XO8Apn/V5jJT/W9YSQoaPY1Xpys+ka4e/LA0ONVNNE+8dQbvsx23OIOcZCoZaX62TRCj+sMUJ8pxjQUqEu8QOAkw87ZFkjBdGfKuCKovTpo89ziOs3z9ccZ4cbzAu";
    // Constants to keep measurements: one to convert inches to millimeters, one to keep track of field width, and one to keep track of target height
    private static final double mmPerInch = 25.4;
    private static final double FIELDWIDTH = (12*6) * mmPerInch;
    private static final double TARGETHEIGHT = 6*mmPerInch;
    // VuforiaLoclizer allows us to call and use Vuforia methods and classes
    private static VuforiaLocalizer vuforia;
    // Initializes the camera direction
    private static final VuforiaLocalizer.CameraDirection cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;

    public Vision() {
        super();
    }

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = cameraDirection;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects and stores them in the 'assets' part of our application.
        VuforiaTrackables roverRuckusTargets = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        VuforiaTrackable blueRover = roverRuckusTargets.get(0);
        VuforiaTrackable redFootprint = roverRuckusTargets.get(1);
        VuforiaTrackable frontCraters = roverRuckusTargets.get(2);
        VuforiaTrackable backSpace = roverRuckusTargets.get(3);

        // Gathers together all the trackable objects in one easily-iterable collection
        ArrayList<VuforiaTrackable> RoverRuckusTrackables = new ArrayList<VuforiaTrackable>(4);
        RoverRuckusTrackables.addAll(roverRuckusTargets);

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0));
        blueRover.setLocation(blueRoverLocationOnField);

        /**
         * To place the RedFootprint target in the middle of the red perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 180 around the field's Z axis so the image is flat against the red perimeter wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative Y axis to the red perimeter wall.
         */
        OpenGLMatrix redFootprintLocationOnField = OpenGLMatrix
                .translation(0, -mmFTCFieldWidth, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180));
        redFootprint.setLocation(redFootprintLocationOnField);

        /**
         * To place the FrontCraters target in the middle of the front perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it 90 around the field's Z axis so the image is flat against the front wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the negative X axis to the front perimeter wall.
         */
        OpenGLMatrix frontCratersLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(mmFTCFieldWidth, 0, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);
    }
}
