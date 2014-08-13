/*
 * LegoNXT.java version 0.99
 *
 * Created on 22 de Janeiro de 2007, 0:21
 *
 *  LegoNXT: A Processing library to control the NXT Brick.
 *  This is part a of the DiABlu Project (http://diablu.jorgecardoso.org)
 *
 *  Copyright (C) 2007  Jorge Cardoso
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *  You can reach me by
 *  email: jorgecardoso <> ieee org
 *  web: http://jorgecardoso.org
 */

package pt.citar.diablu.processing.nxt;

import processing.core.*;
import pt.citar.diablu.nxt.brick.*;
import pt.citar.diablu.nxt.protocol.NXTCommBluetoothSerialChannelJSSC;
//import pt.citar.diablu.nxt.protocol.NXTCommBluetoothSerialChannelRXTX;
//import gnu.io.NoSuchPortException;
//import gnu.io.PortInUseException;
//import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

/**
 * The LegoNXT class allows you to control the Mindstorms NXT robotic system.
 * The NXT must have been paired using Bluetooth and you must know the name
 * of the virtual COM port assigned (COM1, COM2, or something like that).
 *
 * @author Jorge Cardoso http://jorgecardoso.org
 */
public class LegoNXT {
    /**
     * The motor port A.
     */
    public static final int MOTOR_A = 0;
    /**
     * The motor port B.
     */
    public static final int MOTOR_B = 1;
    /**
     * The motor port C.
     */
    public static final int MOTOR_C = 2;
    
    /**
     * The sensor port 1.
     */
    public static final int PORT_1 = 0;
    /**
     * The sensor port 2.
     */
    public static final int PORT_2 = 1;
    /**
     * The sensor port 3.
     */
    public static final int PORT_3 = 2;
    /**
     * The sensor port 4.
     */
    public static final int PORT_4 = 3;
    
    /**
     * The active light sensor type.
     */
    public static final int ACTIVE = NXTLightSensor.ACTIVE;
    
    /**
     * The inactive light sensor type.
     */
    public static final int INACTIVE = NXTLightSensor.INACTIVE;
    
    /**
     * The full color sensor type.
     */
    public static final int FULL = NXTColorSensor.FULL;
    
    /**
     * The red color sensor type.
     */
    public static final int RED = NXTColorSensor.RED;
    
    /**
     * The green color sensor type.
     */
    public static final int GREEN = NXTColorSensor.GREEN;
    
    /**
     * The blue color sensor type.
     */
    public static final int BLUE = NXTColorSensor.BLUE;
    
    /**
     * The none color sensor type.
     */
    public static final int NONE = NXTColorSensor.NONE;
    
    /**
     * The parent PApplet.
     */
    private PApplet parent;
    
    /**
     * The bluetooth nxt channel.
     */
    //private NXTCommBluetoothSerialChannelRXTX btChannel;
    private NXTCommBluetoothSerialChannelJSSC btChannel;
    
    /**
     * The NXTBrick object.
     */
    private NXTBrick brick;
    
    /**
     * The NXT Speaker.
     */
    private NXTSpeaker speaker;
    
    /**
     * The NXT Message Box.
     */
    private NXTMsgBox msgbox;
    
    /**
     * The NXT motors.
     */
    private NXTMotor motor[];
    
    /**
     * The button sensor.
     */
    private NXTButtonSensor buttonSensor;
    
    /**
     * The light sensor.
     */
    private NXTLightSensor lightSensor;
    
    /**
     * The proximity sensor
     */
    private NXTProximitySensor proximitySensor;
    
    /**
     * The sensor objects for each port
     */
    private Object sensorPorts[];
    
    /**
     * Creates a new instance of LegoNXT
     *
     * @param parent The PApplet. Use <code>this</code>.
     * @param commPort The COM port where you connected the NXT. This is the name
     * of the virtual COM port that was assigned to the NXT.
     */
    public LegoNXT(PApplet parent, String commPort) {
        this.parent = parent;
        
        /* register calls */
        parent.registerMethod("dispose", this);
        /* open bt channel */
        //try {
            //btChannel = new NXTCommBluetoothSerialChannelRXTX(commPort);
            btChannel = new NXTCommBluetoothSerialChannelJSSC(commPort);
        //}  catch (RuntimeException ex) {
        //    ex.printStackTrace();
        //}
        
        // make the brick
        brick = new NXTBrick(btChannel);
        
        // make the motors
        motor = new NXTMotor[3];
        motor[0] = new NXTMotor(brick, (byte)0);
        motor[1] = new NXTMotor(brick, (byte)1);
        motor[2] = new NXTMotor(brick, (byte)2);
        
        // make the sensors
        sensorPorts = new Object[4];
        
        // make the speaker
        speaker = new NXTSpeaker(brick);
        
        // make the messagebox
        msgbox = new NXTMsgBox(brick);
        
        // nxtcomm version info
        System.out.println("NXTComm for Processing, beta version 0.99 by Jorge Cardoso");
    }
    
    
    /**
     * Plays a tone on the NXT.
     *
     * @param frequency The frequency of the tone.
     * @param duration The duration, in milliseconds, of the tone.
     * @return True if all went ok. False otherwise.
     */
    public boolean playTone(int frequency, int duration) {
        return speaker.playTone(frequency, duration);
    }
    
    /**
     * Sends a BT message to the NXT.
     *
     * @param mailbox number
     * @param message The message string
     * @return True if all went ok. False otherwise.
     */
    public boolean sendMsg(int mailbox, String message) {
        return msgbox.sendMsg(mailbox, message);
    }

    /**
     * Reads a BT message to the NXT.
     * 
     * @param mailbox Int mailbox number
     * @param remove Boolean; true clears message from remote inbox
     * 
     * @return String message
     */
    public String receiveMsg(int mailbox, boolean remove) {
        return msgbox.receiveMsg(mailbox, remove);
    }

    
    /**
     * Sets the power to the specified motor.
     *
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     * @param power The power of the motor (-100, 100). Positive values make
     * the motor fo forward, negative values make it go backwards.
     * @return True if all ok. False on error.
     */
    public boolean motorForward(int motorNumber, int power) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return false;
        }
        if (power > 100) {
            power = 100;
        } else if (power < -100) {
            power = -100;
        }
       // System.out.println("starting motor");
        return motor[motorNumber].forward((byte)power);
    }
    
    /**
     * Make the motor run for a specifiec amount of time.
     *
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     * @param power The power of the motor (-100, 100). Positive values make
     * the motor fo forward, negative values make it go backwards.
     * @param tachoLimit The tacho limit on the motor. Haven't figured this out completely...
     * @return True if all ok. False on error.
     */
    public boolean motorForwardLimit(int motorNumber, int power, int tachoLimit) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return false;
        }
        if (power > 100) {
            power = 100;
        } else if (power < -100) {
            power = -100;
        }
       // System.out.println("starting motor");
        return motor[motorNumber].forwardLimit((byte)power, tachoLimit);
    }
    
    /**
     * Stops the motor abruptely.
     *
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     * @return True if all ok. False on error.
     */
    public boolean motorHandBrake(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return false;
        }
        return motor[motorNumber].handBrake();
    }
    
    /**
     * Stops the motor smoothely.
     *
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     * @return True if all ok. False on error.
     */
    public boolean motorStop(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return false;
        }
        return motor[motorNumber].slowStop();
    }
    
    /**
     * Returns the current limit on a movement in progress.
     *
     * @return The current limit on a movement in progress, if any.
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     */
    public long getMotorTachoLimit(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return -1;
        }
        return motor[motorNumber].getTachoLimit();
    }
    
    /**
     * Returns number of counts since last reset of the motor counter.
     *
     * @return  The number of counts since last reset of the motor counter.
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     */    
    public long getMotorTachoCount(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return -1;
        }
        return motor[motorNumber].getTachoCount();
    }
    
    /**
     * Returns the current position realative to the last programmed movement.
     * @return The current position realative to the last programmed movement.
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     */
    public long getMotorBlockTachoCount(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return -1;
        }
        return motor[motorNumber].getBlockTachoCount();
    }
    
    /**
     * The current position relative to the last reset of the rotation sensor for this motor.
     *
     * @return The current position relative to the last reset of the rotation sensor for this motor.
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     */
    public long getMotorRotationCount(int motorNumber) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
            return -1;
        }
        return motor[motorNumber].getRotationCount();
    }
    
    /**
     * Resets the motor position.
     *
     * @param motorNumber The motor port (0, 1 or 2). The constants MOTOR_A,
     * MOTOR_B and MOTOR_C should be used.
     * @param motorNumber
     */
    public void resetMotorPosition(int motorNumber, boolean relative) {
        if (motorNumber > 2) {
            System.err.println("Motor number must be 0, 1 or 2.");
        }
        motor[motorNumber].resetMotorPosition(relative);
    }
    
    /**
     * Returns the state of the pressure sensor at the specifiec port.
     *
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     * @return True if sensor pressed. False if not pressed or on error.
     *
     */
    public boolean getButtonState(int portNumber) {
        
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return false;
        }
        /* Check if the correct sensor instance is associated with the requester port. If it
            is not, than create the correct sensor
         */
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTButtonSensor)) {
            sensorPorts[portNumber] = new NXTButtonSensor(brick, (byte)portNumber);
        }
        return ((NXTButtonSensor)sensorPorts[portNumber]).isButtonPressed();
    }
    
    /**
     * Gets the DB level of the sound sensor on the specified port.
     *
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     * @return The DB level (0, 100). -1 on error.
     */
    public int getDB(int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return -1;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTSoundSensor)) {
            sensorPorts[portNumber] = new NXTSoundSensor(brick, (byte)portNumber);
        }
        return ((NXTSoundSensor)sensorPorts[portNumber]).getDB();
    }
    
    /**
     * Returns the light level from the light sensor attached to the specified port.
     *
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     * @return The light level (0..100). -1 on error
     */
    public int getLight(int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return -1;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTLightSensor)) {
            sensorPorts[portNumber] = new NXTLightSensor(brick, (byte)portNumber);
        }
        return ((NXTLightSensor)sensorPorts[portNumber]).getValue();
    }
    
    /**
     * Sets the type of light sensor to be used on the specifiec port.
     *
     * @param type The type of light sensor (ACTIVE or INACTIVE).
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     */
    public void setLightSensorType(int type, int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return;
        }
        if (type != ACTIVE && type != INACTIVE) {
            System.err.println("Wrong sensor type. Must be ACTIVE or INACTIVE");
            return;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTLightSensor)) {
            sensorPorts[portNumber] = new NXTLightSensor(brick, (byte)portNumber);
        }
        
        ((NXTLightSensor)sensorPorts[portNumber]).setType(type);
    }

    /**
     * Returns the color level from the color sensor attached to the specified port.
     *
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     * @return The color level (0..100). -1 on error
     */
    public int getColor(int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return -1;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTColorSensor)) {
            sensorPorts[portNumber] = new NXTColorSensor(brick, (byte)portNumber);
        }
        return ((NXTColorSensor)sensorPorts[portNumber]).getValue();
    }
    
    /**
     * Sets the type of color sensor to be used on the specifiec port.
     *
     * @param type The type of color sensor (FULL or RED or GREEN or BLUE or NONE or RGB).
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     */
    public void setColorSensorType(int type, int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return;
        }
        if (type != FULL && type != RED && type != GREEN && type != BLUE && type != NONE) {
            System.err.println("Wrong sensor type. Must be FULL or RED or GREEN or BLUE or NONE or RGB");
            return;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTColorSensor)) {
            sensorPorts[portNumber] = new NXTColorSensor(brick, (byte)portNumber);
        }
        
        ((NXTColorSensor)sensorPorts[portNumber]).setType(type);
    }

    /**
     * Returns the proximity to an obstacle, measured by the ultrasonic sensor.
     *
     * @param portNumber The port number on which the pressure sensor is
     * connected (the constants PORT_1, PORT_2, PORT_3 or PORT_4 should be used).
     * @return The distance measured. -1 on error.
     */
    public int getDistance(int portNumber) {
        if (portNumber > sensorPorts.length-1) {
            System.err.println("Port number too large: " + portNumber + "!");
            return -1;
        }
        if(sensorPorts[portNumber] == null || !(sensorPorts[portNumber] instanceof NXTProximitySensor)) {
            sensorPorts[portNumber] = new NXTProximitySensor(brick, (byte)portNumber);
        }
        return ((NXTProximitySensor)sensorPorts[portNumber]).getDistance();
    }
    
    /**
     * Returns the battery level.
     *
     * @return The battery level in millivolts.
     */
    public int getBatteryLevel() {
    	//System.out.println(brick);
        return brick.getBatteryLevel();
    }
    
    public void dispose() {
        try {
            btChannel.closeChannel();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
