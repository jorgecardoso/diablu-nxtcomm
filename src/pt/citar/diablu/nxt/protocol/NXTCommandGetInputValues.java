/*
 * NXTCommandGetInputValues.java
 *
 * Created on 19 de Janeiro de 2007, 21:53
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




/**
 * Represents the GETINPUTVALUES Command.
 * The response to this command (if required) is a <code>NXTResponseInputValues</code>.
 *
 * @author Jorge Cardoso
 * @see NXTResponseInputValues
 */
public class NXTCommandGetInputValues extends NXTCommand {
    /**
     * Output port index on buffer;
     */
    private final static int INPUT_PORT_INDEX = 2;


    /**
     * The input port to read values from.
     */
    private byte inputPort;

    /**
     * The response to this command.
     */
    private NXTResponseInputValues response;

    /**
     * Constructs a new <code>NXTCommandGetInputValues</code> object which will read values from input port 0.
     *
     */
    public NXTCommandGetInputValues() {
        // just to override the default constructor from the base class.
        this((byte)0);
    }


    /**
     *
     * Constructs a new <code>NXTCommandGetInputValues</code> object with a given input port.
     *
     *
     * @param inputPort The input port to read values from. Range: (0 - 3).
     */
    public NXTCommandGetInputValues(byte inputPort) {
        super(true);
        this.setInputPort(inputPort);
        buffer = new byte[] {DIRECT_COMMAND_RESPONSE_REQUIRED, 0x07, inputPort};
        response = new NXTResponseInputValues();
    }




    protected NXTResponse getResponse() {
        return this.response;
    }

    /**
     * Returns the input port on which we want to read values.
     * 
     * @return The input port.
     */
    public byte getInputPort() {
        return inputPort;
    }
    
    /**
     * Sets the input port on which we want to read values.
     * 
     * @param inputPort The input port.
     */
    public void setInputPort(byte inputPort) {
        this.inputPort = inputPort;
    }






}
