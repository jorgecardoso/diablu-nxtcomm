/*
 * NXTColorSensor.java
 *
 * Created on 22 de Janeiro de 2007, 23:43
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

package pt.citar.diablu.nxt.brick;

import java.io.IOException;
import pt.citar.diablu.nxt.protocol.*;

/**
 * The NXTColorSensor class represents the, hmm, light sensor...
 *
 * @author Matt Cotter
 **/
public class NXTColorSensor extends NXTComponent {
    
    public static final byte FULL = NXTResponseInputValues.COLOR_FULL;
    public static final byte RED = NXTResponseInputValues.COLOR_RED;
    public static final byte GREEN = NXTResponseInputValues.COLOR_GREEN;
    public static final byte BLUE = NXTResponseInputValues.COLOR_BLUE;
    public static final byte NONE = NXTResponseInputValues.COLOR_NONE;


    private NXTCommandGetInputValues getInputValues;
    private NXTResponseInputValues inputValues;
    
    /**
     * The sensor type. ACTIVE or INACTIVE.
     */
    private byte type;
    
    /**
     * Coonstructs a new NXTColorSensor object given the NXTBrick and the port number the
     * sensor is connected to.
     *
     * @param brick The NXTBrick.
     * @param portAttached The port to which the sensor is attached. (0 - 3).
     */
    public NXTColorSensor(NXTBrick brick, byte portAttached) {
        super(brick, portAttached);
        initialize();
    }
    
    public void initialize() {
        this.type = FULL;
        NXTCommandSetInputMode inputMode = new NXTCommandSetInputMode(this.portAttached,
                this.type,
                NXTResponseInputValues.PCT_FULL_SCALE_MODE);
        try {
            this.brick.getChannel().sendCommand(inputMode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        getInputValues = new NXTCommandGetInputValues(this.portAttached);
    }
    
    public int getValue() {
        try {
            inputValues = (NXTResponseInputValues)brick.getChannel().sendCommand(getInputValues);
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
        return inputValues.getScaledValue();
    }
    
    public void setType(int type) throws IllegalArgumentException {
        if (type != FULL && type != RED && type != GREEN && type != BLUE && type != NONE) {
            throw new IllegalArgumentException("Wrong argument: " + type + ". Should be ACTIVE or INACTIVE.");
        }
        this.type = (byte)type;
        
        NXTCommandSetInputMode inputMode = new NXTCommandSetInputMode(this.portAttached,
                this.type,
                NXTResponseInputValues.PCT_FULL_SCALE_MODE);
        try {
            this.brick.getChannel().sendCommand(inputMode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
