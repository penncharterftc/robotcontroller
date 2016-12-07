package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Driver Control", group="PCFTC")
public class MainOpMode extends OpMode{
    double increment,servo_1, servo1init;
    boolean buttonA,buttonB,y1,forward=true;
    Servo /*arm,*/ lFlip, rFlip/*, release*/;
    DcMotor left_motor, right_motor;
    float lpower, rpower;
//    File xmlConfig = new File("C:\\test.xml");
//    private XmlPullParser myparser = Xml.newPullParser();

//    public void parse(){
//        try {
//            InputStream xml = new InputStream() {
//                @Override
//                public int read() throws IOException {
//                    return 0;
//                }
//            };
//            InputStreamReader xmlReader = new InputStreamReader(xml);
//            try {
//                myparser.setInput(xmlReader);
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void init(){
        increment = 0.01; servo_1=0; buttonA=false; buttonB=false;
        /*arm = hardwareMap.servo.get("arm");*/
        /*release = hardwareMap.servo.get("release");*/
        lFlip = hardwareMap.servo.get("lFlip");
        rFlip = hardwareMap.servo.get("rFlip");
        left_motor = hardwareMap.dcMotor.get("right_drive");
        right_motor = hardwareMap.dcMotor.get("left_drive");
/*        arm.setPosition(0);
        release.setPosition(1);*/
        lFlip.setPosition(servo1init);
        rFlip.setPosition(.2);
        right_motor.setDirection(DcMotor.Direction.FORWARD);
        left_motor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop(){
        if(forward) {
            lpower = -gamepad1.left_stick_y;
            rpower = -gamepad1.right_stick_y;
        }
        if(!forward){
            lpower = -gamepad1.right_stick_y;
            rpower = -gamepad1.left_stick_y;
        }

        right_motor.setPower(lpower);
        left_motor.setPower(rpower);

        /*if(gamepad1.x && release.getPosition()!=0)
            release.setPosition(0);*/
        /*if(!gamepad1.x && release.getPosition()!=1)
            release.setPosition(1);*/
        if(gamepad1.left_bumper && gamepad1.left_trigger==0)
            if(lFlip.getPosition() + increment <.4)
                lFlip.setPosition(lFlip.getPosition()+increment);
        if(!gamepad1.left_bumper && gamepad1.left_trigger!=0)
            if(lFlip.getPosition() - increment >0)
                lFlip.setPosition(lFlip.getPosition() - increment);
        if(!gamepad1.right_bumper && gamepad1.right_trigger!=0)
            if(rFlip.getPosition() + increment <1)
                rFlip.setPosition(rFlip.getPosition()+increment);
        if(gamepad1.right_bumper && gamepad1.right_trigger==0)
            if(rFlip.getPosition() - increment >.2)
                rFlip.setPosition(rFlip.getPosition() - increment);

        telemetry.addData("Left Motor Power: ", lpower);
        telemetry.addData("Right Motor Power: ", rpower);
        /*telemetry.addData("Servo Position: ", arm.getPosition());*/

        if(gamepad1.a && !buttonA){
            if(servo_1+.01<=1)
                servo_1+=increment;
            /*arm.setPosition(servo_1);*/
        }
        else if(!gamepad1.a && buttonA)
            buttonA=false;

        if(gamepad1.b && !buttonB){
            if(servo_1-.01>=0)
                servo_1-=increment;
            /*arm.setPosition(servo_1);*/
        }

        else if(!gamepad1.b && buttonB)
            buttonB = false;


        if(gamepad1.y && !y1){
            if (right_motor.getDirection() == DcMotor.Direction.FORWARD)
                right_motor.setDirection(DcMotor.Direction.REVERSE);
            else if (right_motor.getDirection() == DcMotor.Direction.REVERSE)
                right_motor.setDirection(DcMotor.Direction.FORWARD);
            if (left_motor.getDirection() == DcMotor.Direction.REVERSE)
                left_motor.setDirection(DcMotor.Direction.FORWARD);
            else if (left_motor.getDirection() == DcMotor.Direction.FORWARD)
                left_motor.setDirection(DcMotor.Direction.REVERSE);
            if(forward)
                forward = false;
            else if(!forward)
                forward=true;
            y1=true;
        }

        if(!gamepad1.y && y1){
            y1=false;
        }
    }

}
