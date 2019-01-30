package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.*;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;

public class Vision {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AeZ0lyb/////AAABmR6FITE0NUyhk5ZOti1IBIJ3pIRPfIwcxUDngtgoPdAcQRq+mIIDpzP2P5dKIghdz9n0UFlZ3VRd0Bfdhzbk8FoP/aouesthpT0RG9oxteRIjLYPqMGd5CuKSzyTv7kkFWnb4X8vXmWEiu6jljfmZz1ReoV7orgI8LLslbW2XcSvRZMo6sVv1ahpdTJ9nUjhtxn26+EDUFfc9jDay12jGJFj97TLoCXv645WsnAQtc777IaYjTN/DbQXwR2aKptID98EI5iqioJkG6KZqove3Ft124KSnkqrMgEP8bmA0CoFmDQ324pz8VhFflJOb6me+r9K0Sd+amuv8PxRmT5UFEVnSZz9ZiW0Qu05F7bDWfHN";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private int locationOfGoldMineral = -1;
    public ElapsedTime RunTime = new ElapsedTime();

    public int Vision() {
        tfod.activate();
        RunTime.reset();
        while (RunTime.time() < 5) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                //If Gold is on the left
                                locationOfGoldMineral = 0;
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                //If Gold is on the right
                                locationOfGoldMineral = 2;
                            } else {
                                //If Gold is on the center
                                locationOfGoldMineral = 1;
                            }
                        }
                    }
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
        return locationOfGoldMineral;
    }
}