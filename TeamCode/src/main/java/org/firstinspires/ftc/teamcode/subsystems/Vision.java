package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

//@Autonomous(name = "Vuforia-Test")
public class Vision {
    // Licence key in order to utilize the Vuforia methods and objects
    private static final String VUFORIA_KEY = "AfH1Zl//////AAABmR491lMHykEBobd9/V5Ni4yNrRLaQsIdeGQ4B8qbKvPivDl2OuKmWe78D8/ZtKpaUqH8DbY4Z0uaKkxQVKinzPM7WrCpEKyV7ujG97N2Stb+nRAZ37IYIn67v1ol79c9rUcM/4JGy3sicrICs8WiEIhs/lnWhwKRZWnyi8cBxHddBv13O8UxzIhnzZsuHBYJ78e5V+kPXg5xbly/b24LPxxyt01ZZq7vvP0ipO759SbJlp8XO8Apn/V5jJT/W9YSQoaPY1Xpys+ka4e/LA0ONVNNE+8dQbvsx23OIOcZCoZaX62TRCj+sMUJ8pxjQUqEu8QOAkw87ZFkjBdGfKuCKovTpo89ziOs3z9ccZ4cbzAu";
    // Constants to keep measurements: one to convert inches to millimeters, one to keep track of field width, and one to keep track of target height
    private static final float mmPerInch = 25.4f;
    private static final float FIELDWIDTH = (12 * 6) * mmPerInch;
    private static final float TARGETHEIGHT = 6 * mmPerInch;
    // VuforiaLoclizer allows us to call and use Vuforia methods and classes
    VuforiaLocalizer vuforia;
    // Initializes the camera direction
    private static final VuforiaLocalizer.CameraDirection cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
    private OpenGLMatrix lastLocation = null;
    private boolean targetVisible = false;
    private VuforiaTrackables allTargets;
    private ArrayList<VuforiaTrackable> allTrackables;
    private OpenGLMatrix phoneLocation;
    private VectorF robotPosition;
    private Orientation robotRotation;
    private String visibleTarget;

    public Vision() {
        super();
    }

    private void setUpVuforia(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = cameraDirection;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        setUpVuforiaTrackables("RoverRuckus");

        String[] trackableNames = {"Blue-Rover", "Red-Footprint", "Front-Craters", "Back-Space"};
        nameTrackables(trackableNames);
        // Load the data sets for the trackable objects and stores them in the 'assets' part of our application.
        VuforiaTrackable blueRover = allTargets.get(0); // Blue Rover
        VuforiaTrackable redFootprint = allTargets.get(1); // Red Footprint
        VuforiaTrackable frontCraters = allTargets.get(2); // Front Craters
        VuforiaTrackable backSpace = allTargets.get(3); // Back Space

        /**
         * To place the BlueRover target in the middle of the blue perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Then, we translate it along the Y axis to the blue perimeter wall.
         */
        OpenGLMatrix blueRoverLocationOnField = OpenGLMatrix
                .translation(0, FIELDWIDTH, TARGETHEIGHT)
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
                .translation(0, -FIELDWIDTH, TARGETHEIGHT)
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
                .translation(-FIELDWIDTH, 0, TARGETHEIGHT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90));
        frontCraters.setLocation(frontCratersLocationOnField);

        /**
         * To place the BackSpace target in the middle of the back perimeter wall:
         * - First we rotate it 90 around the field's X axis to flip it upright.
         * - Second, we rotate it -90 around the field's Z axis so the image is flat against the back wall
         *   and facing inwards to the center of the field.
         * - Then, we translate it along the X axis to the back perimeter wall.
         */
        OpenGLMatrix backSpaceLocationOnField = OpenGLMatrix
                .translation(FIELDWIDTH, 0, TARGETHEIGHT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90));
        backSpace.setLocation(backSpaceLocationOnField);

        setUpPhoneMatrix(0, 0, 0);

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocation, parameters.cameraDirection);
        }

    }

    private void setUpVuforiaTrackables(String compName) {
        allTargets = this.vuforia.loadTrackablesFromAsset(compName);

        // Gathers together all the trackable objects in one easily-iterable collection
        allTrackables = new ArrayList<VuforiaTrackable>(4);
        allTrackables.addAll(allTargets);
    }

    private void setUpPhoneMatrix(int forwardDisplacement, int leftDisplacement, int verticalDisplacement) {
        phoneLocation = OpenGLMatrix
                .translation(forwardDisplacement, leftDisplacement, verticalDisplacement)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES,
                        cameraDirection == FRONT ? 90 : -90, 0, 0));
    }

    private void nameTrackables(String[] names) {
        if (names.length != allTrackables.size()) {
            System.out.println("Number of names does not  match number of vision targets");
        } else {
            for (int i = 0; i < names.length; i++) {
                allTrackables.get(i).setName(names[i]);
            }
        }
    }

    public void init(HardwareMap hmap) {
        setUpVuforia(hmap);

        // Start tracking the data sets we care about
        allTargets.activate();


    }

    public void scan() {
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                visibleTarget = trackable.getName();
                targetVisible = true;

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }

        if (targetVisible) {
            VectorF translation = lastLocation.getTranslation();
            robotPosition = translation;
            System.out.printf("Pos (in)", "(X,Y,Z) = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(0) / mmPerInch, translation.get(0) / mmPerInch);

            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            robotRotation = rotation;
            System.out.printf("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
        } else {
            System.out.println("No Visible Target");
        }
    }

    public double getRobotAngle() {
        return robotRotation.thirdAngle;
    }

    public double getRobotX() {
        return robotPosition.get(0);
    }

    public double getRobotY() {
        return robotPosition.get(1);
    }

    public String getVisibleTarget() { return visibleTarget; }
}
