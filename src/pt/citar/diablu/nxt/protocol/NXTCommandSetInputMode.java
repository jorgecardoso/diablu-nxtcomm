/*
 * NXTCommandSetInputMode.java
 *
 * Created on 20 de Janeiro de 2007, 16:19
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
 * The <code>NXTCommandSetInputMode</code> allows you to set the input mode for the sensors.
 *
 * @author Jorge Cardoso
 */
public class NXTCommandSetInputMode extends NXTCommand {

    /**
     * The response to this command.
     */
    private NXTResponseStatus response;

    /**
     * Constructs a new <code>NXTCommandSetInputMode</code> object with input port = 0, no sensor type, raw sensor mode and
     * with no response requirement.
     */
    public NXTCommandSetInputMode() {
        this((byte)0, NXTResponseInputValues.NO_SENSOR_TYPE, NXTResponseInputValues.RAW_MODE);
    }

    /**
     * Constructs a new <code>NXTCommandSetInputMode</code> object with the input port, sensor type, sensor mode and
     * with no response requirement.
     */
    public  NXTCommandSetInputMode(byte inputPort, byte sensorType, byte sensorMode) {
        this(inputPort, sensorType, sensorMode, false);
    }

    /**
     * Constructs a new <code>NXTCommandSetInputMode</code> object with the input port, sensor type, sensor mode and
     * response requirement specified.
     */
    public NXTCommandSetInputMode(byte inputPort, byte sensorType, byte sensorMode, boolean responseRequired) {
        this.responseRequired = responseRequired;
        buffer = new byte[] {responseRequired ? DIRECT_COMMAND_RESPONSE_REQUIRED : DIRECT_COMMAND_NO_RESPONSE, 0x05, inputPort, sensorType, sensorMode};
        response = new NXTResponseStatus();
    }

    protected NXTResponse getResponse() {
        return this.response;
    }

    /**
     * TODO: getter/setter for input port, sensor type and sensor mode
     */
}
