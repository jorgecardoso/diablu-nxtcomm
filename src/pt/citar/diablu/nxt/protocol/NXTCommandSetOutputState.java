/*
 * NXTCommandSetOutputState.java
 *
 * Created on 21 de Janeiro de 2007, 14:26
 *
 *  NXTComm: A java library to control the NXT Brick.
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

package pt.citar.diablu.nxt.protocol;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * This command allows you to set the output state, i.e., to control the NXT motors.
 *
 * @author Jorge Cardoso
 */
public class NXTCommandSetOutputState extends NXTCommand {
    
    /**
     * Power set point index on packet.
     */
    private static final int POWER_SET_POINT_INDEX = 3;
    
    /**
     * The mode index on packet.
     */
    private static final int MODE_INDEX = 4;
    
    
    /**
     * The regulation mode index.
     */
    private static final int REGULATION_MODE_INDEX = 5;
    
    /**
     * The run state index on packet.
     */
    private static final int RUN_STATE_INDEX = 7;
    
    /**
     * The tacho limit index on packet. 5 bytes.
     */
    private static final int TACHO_LIMIT_INDEX = 8;
        
    /**
     * The motor on mode (bit field - can be combined with other modes).
     */
    public static final byte MODE_MOTOR_ON = 0x01;

    /**
     * The brake mode (bit field - can be combined with other modes).
     */
    public static final byte MODE_BRAKE = 0x02;

    /**
     * The regulated mode (bit field - can be combined with other modes).
     */
    public static final byte MODE_REGULATED = 0x04;

    /**
     * The idle regulation mode mode. No regulation will be enabled.
     */
    public static final byte REGULATION_MODE_IDLE = 0x00;

    /**
     * The motor speed regulation mode mode. Power control will be enabled on specific output.
     */
    public static final byte REGULATION_MODE_MOTOR_SPEED = 0x01;

    /**
     * The motor sync regulation mode mode. Synchronization will be enabled. (Needs enabled on two outputs).
     */
    public static final byte REGULATION_MODE_MOTOR_SYNC = 0x02;

    /**
     * The idle run state.
     */
    public static final byte RUN_STATE_IDLE = 0x00;

    /**
     * The ramp up run state.
     */
    public static final byte RUN_STATE_RAMP_UP = 0x10;

    /**
     * The running run state.
     */
    public static final byte RUN_STATE_RUNNING = 0x20;

    /**
     * The ramp down run state.
     */
    public static final byte RUN_STATE_RAMP_DOWN = 0x40;

    
    
    /**
     * The response to this command.
     */
    private NXTResponseStatus response;

    /** Creates a new instance of NXTCommandSetOutputState */
    public NXTCommandSetOutputState() {
    }

    /**
     * Constructs a new <code>NXTCommandSetOutputState</code> object with output port, power set point,
     * mode byte, regulation mode, turn ration, run state and tacho limit and with no response requirement.
     *
     * @param outputPort The output port on which the motor is connected. Range: 0 - 2; 0xff is a special value
     * that means ALL.
     * @param powerSetPoint The power set point (-100 to 100).
     * @param modeByte The mode byte (bit field). See MODE constants.
     * @param regulationMode The regulation mode. See REGULATION_MODE constants.
     * @param turnRatio The turn ratio (-100 to 100).
     * @param runState The run state. See RUN_STATE constants.
     * @param tachoLimit The tacho limit. 0 means forever.
     */
    public NXTCommandSetOutputState(byte outputPort, byte powerSetPoint, byte modeByte,
            byte regulationMode, byte turnRatio, byte runState, long tachoLimit) {
        buffer = new byte[] {NXTCommand.DIRECT_COMMAND_NO_RESPONSE, 0x04, outputPort,
            powerSetPoint, modeByte, regulationMode, turnRatio, runState, (byte) (0xff & tachoLimit),
            (byte) (tachoLimit >>> 8), (byte)(tachoLimit >>> 16), (byte)(tachoLimit >>> 20),
            (byte)(tachoLimit >>> 24)};
        response = new NXTResponseStatus();
    }


    protected NXTResponse getResponse() {
        return this.response;
    }
    
    /**
     * Sets the power set point.
     * 
     * @param power The power set point. Range: -100; 100.
     */
    public void setPowerSetPoint(byte power) {
        buffer[POWER_SET_POINT_INDEX] = power;
    }
    
    /**
     * @return The current power set point.
     */
    public byte getPowerSetPoint() {
        return  buffer[POWER_SET_POINT_INDEX];
    }
    
    /**
     * Sets the run state of the motor.
     * 
     * @param runState The run state. See RUN_STATE constants.
     */
    public void setRunState(byte runState) {
        buffer[RUN_STATE_INDEX] = runState;
    }
    
    /**
     * @return The current run state.
     */
    public byte getRunState() {
        return buffer[RUN_STATE_INDEX];
    }
    
    /**
     * Sets the regulation mode of the motor.
     * 
     * @param regulationMode The regulation mode. See REGULATION_MODE constants.
     */
    public void setRegulationMode(byte regulationMode) {
        buffer[REGULATION_MODE_INDEX] = regulationMode;
    }
    
    /**
     * Sets the mode.
     *
     * @param mode The mode. See MODE constants.
     */
    public void setMode(byte mode) {
        buffer[MODE_INDEX] = mode;
    }
    
    /**
     * @return The current mode.
     */
    public byte getMode() {
        return  buffer[MODE_INDEX];
    }
    
    /**
     * Sets the tacho limit.
     *
     * @param tachoLimit The tacho limit to set.
     */
    public void setTachoLimit(long tachoLimit) {
        buffer[TACHO_LIMIT_INDEX] = (byte) (0xff & tachoLimit);
        buffer[TACHO_LIMIT_INDEX + 1] = (byte) (tachoLimit >>> 8);
        buffer[TACHO_LIMIT_INDEX + 2] = (byte)(tachoLimit >>> 16);
        buffer[TACHO_LIMIT_INDEX + 3] = (byte)(tachoLimit >>> 24);
        buffer[TACHO_LIMIT_INDEX + 4] = (byte)(tachoLimit >>> 32);        
    }
    
    /**
     * TODO: Check.
     * @return The current tacho limit.
     */
    public long getTachoLimit() {
        return buffer[TACHO_LIMIT_INDEX] + 
               (buffer[TACHO_LIMIT_INDEX + 1] << 8) +
               (buffer[TACHO_LIMIT_INDEX + 2] << 16) +
               (buffer[TACHO_LIMIT_INDEX + 3] << 24) + 
               (buffer[TACHO_LIMIT_INDEX + 4] << 32); 
    }
}


