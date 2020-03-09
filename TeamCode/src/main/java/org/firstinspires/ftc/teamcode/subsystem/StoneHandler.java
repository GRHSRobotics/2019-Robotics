package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StoneHandler implements Subsystem {

    Telemetry telemetry;

    public Servo grabber;
    CRServo extender;

    DigitalChannel extendedLimit;
    DigitalChannel retractedLimit;


    //CONSTANTS
    public final double GRABBER_CLOSED = 0;
    public final double GRABBER_OPEN = 1;

    public enum ExtensionState {
        EXTENDING,
        RETRACTING,
        STOPPED
    }

    public ExtensionState extensionState;

    public void initialize(HardwareMap hardwareMap, Telemetry telemery, boolean moveServos){

        this.telemetry = telemery;


        grabber = hardwareMap.get(Servo.class, "stoneGrabber");
        extender = hardwareMap.get(CRServo.class, "stoneExtender");

        extendedLimit = hardwareMap.get(DigitalChannel.class, "extendedLimit");
        retractedLimit = hardwareMap.get(DigitalChannel.class, "retractedLimit");

        extendedLimit.setMode(DigitalChannel.Mode.INPUT);
        retractedLimit.setMode(DigitalChannel.Mode.INPUT);

        extensionState = ExtensionState.STOPPED;

    }

    public void setGrabberOpen(){
        grabber.setPosition(GRABBER_OPEN);
    }

    public void setGrabberClosed(){
        grabber.setPosition(GRABBER_CLOSED);
    }

    public void setExtended(){
        extensionState = ExtensionState.EXTENDING;
    }

    public void setRetracted(){
        extensionState = ExtensionState.RETRACTING;
    }

    public void update(){

        switch(extensionState){
            case EXTENDING:
                extender.setPower(1);
                telemetry.addData("Extending", "");
                break;

            case RETRACTING:
                if(!retractedLimit.getState()) { //inner limit is not reached
                    extender.setPower(-1);
                    telemetry.addData("Retracting", "");
                } else {
                    extender.setPower(0);
                    telemetry.addData("Stopped", "");
                    extensionState = ExtensionState.STOPPED;
                }
                break;

            case STOPPED:
                extender.setPower(0);
                telemetry.addData("Stopped", "");

        }


        switch(extensionState){
            case EXTENDING:
                if(!extendedLimit.getState()){ //extender is not at limit
                    extender.setPower(1);
                } else {
                    extender.setPower(0);
                    extensionState = ExtensionState.STOPPED;
                }
                break;

            case RETRACTING:
                if(!retractedLimit.getState()){ //extender is not at limit
                    extender.setPower(-1);
                } else {
                    extender.setPower(0);
                    extensionState = ExtensionState.STOPPED;
                }
                break;

            case STOPPED:
                extender.setPower(0);
                break;
        }



    }
}
