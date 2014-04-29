/*
 * NXTCommandLS.java
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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


/**
 * Represents the LS Write Command.
 * The response to this command (if required) is a <code>NXTResponseStatus</code>.
 *
 *
 * @author Jorge Cardoso
 * @see NXTResponseStatus
 */
public class NXTCommandLSWrite extends NXTCommand {
    /**
     * The LS Write command ID.
     */
    private byte COMMAND_LS_WRITE = 0x0F;
    
    /**
     * The input port value index on the buffer.
     */
    private byte INPUT_PORT_INDEX = 2;
    
    /**
     * TX Data Length byte index on buffer;
     */
    private final static int TX_DATA_LENGTH_INDEX = 3;
    
    /**
     * RX Data Length byte index on buffer.
     */
    private final static int RX_DATA_LENGTH_INDEX = 4;
    
    /**
     * The start of the data.
     */
    private final static int DATA_INDEX = 5;
    
    /**
     * The response to this command.
     */
    private NXTResponseStatus response;
    
    public NXTCommandLSWrite() {
        //this(0, 0);
        
    }
    
    
    /**
     * Constructs a new <code>NXTCommandLSWrite</code> object with a given port, TX data length, RX data
     * length, data and resqponse requirement.
     *
     * For LS communication on the NXT, data lengths are limited to 16 bytes
     * per command. Rx Data Length MUST be specified in the write command since reading from the device is
     * done on a master-slave basis.
     *
     * @param inputPort The input port to send the command to.
     * @param txLength Tx Data Length.
     * @param rxLength Rx Data Length.
     * @param data The data to send.
     * @param responseRequired Indicates if a response to this command is required.
     */
    public NXTCommandLSWrite(byte inputPort, byte txLength, byte rxLength, byte []data, boolean responseRequired) {
        /* Set the response required parameter using the base class */
        super(responseRequired);
        
        buffer = new byte[21]; // Maximum is 5 + 16 bytes.
        
        buffer[COMMAND_ID_INDEX] = COMMAND_LS_WRITE;
        buffer[INPUT_PORT_INDEX] = inputPort;
        setTXLength(txLength);
        setRXLength(rxLength);
        setData(data);
        
        response = new NXTResponseStatus();
    }
    
    protected NXTResponse getResponse() {
        return this.response;
    }
    
    /**
     * Sets the TX Data Length.
     *
     * @param txLength The Tx data length.
     */
    public void setTXLength(byte txLength) {
        buffer[TX_DATA_LENGTH_INDEX] = txLength;
    }
    
    /**
     * Sets the RX Data Length.
     *
     * @param rxLength The Rx data length.
     */
    public void setRXLength(byte rxLength) {
        buffer[RX_DATA_LENGTH_INDEX] = rxLength;
    }
    
    /**
     * Sets the data.
     *
     * @param data The data.
     */
    public void setData(byte []data) {
        if (data.length > 16) {
            throw new IllegalArgumentException("LSWRITE data length must be less than or equal to 16 bytes");
        }
        System.arraycopy(data, 0, buffer, DATA_INDEX, data.length);
       
    }
    
}

