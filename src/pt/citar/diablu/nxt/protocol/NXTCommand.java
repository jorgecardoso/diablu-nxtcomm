/*
 * NXTCommand.java
 *
 * Created on 19 de Janeiro de 2007, 21:20
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

import java.io.IOException;
import java.util.Formatter;


/**
 * Represents a Command that can be sent to the NXTBrick. This class cannot be instantiated.
 * Only subclasses which represent concrete Commands should be used.
 * Commands can generate Responses if so required. Responses are encapsulated by  <code>NXTResponse</code> subclasses.
 *
 * Subclasses should implement the <code>sendCommand()</code> method and any specific parameter setting methods.
 * @see NXTResponse
 * @author Jorge Cardoso
 */
public abstract class NXTCommand {

    /**
     * Direct Command Response Required.
     */
    protected static final byte DIRECT_COMMAND_RESPONSE_REQUIRED = 0x00;

    /**
     * Direct Command Response Required.
     */
    protected static final byte DIRECT_COMMAND_NO_RESPONSE = (byte)0x80;

    /**
     * Command type position in message frame.
     */
    protected static final int COMMAND_TYPE_INDEX = 0;
    
    /**
     * Command ID index on buffer.
     */
    protected static final int COMMAND_ID_INDEX = 1;

    /**
     * Indicates if a response to this command is required.
     **/
    protected boolean responseRequired;

    /**
     * The command packet.
     */
    protected byte []buffer;

    /**
     * Constructs a new command object with no response requirement.
     */
    public NXTCommand() {

        /* By default we should not require a response since it's faster this way*/
        this(false);
    }

    /**
     * Constructs a new command object with a response requirement.
     *
     * @param responseRequired The response requirement. <code>false</code>, no response required. <code>true</code>, response
     * required.
     */
    public NXTCommand(boolean responseRequired) {

        this.responseRequired = responseRequired;
    }
    /**
     * Sends the command to the brick and returns the response (if required).
     *
     * @param is The <code>InputStream</code> from which the response can be read.
     * @param os The <code>OutputStream</code> to send the command.
     *
     * @return The <code>NXTResponse</code> response. This should be cast to the apropriate subclass.
     */
    public NXTResponse sendCommand(NXTCommChannel channel) throws IOException {
        if (isResponseRequired()) {
            buffer[COMMAND_TYPE_INDEX] = DIRECT_COMMAND_RESPONSE_REQUIRED;

            /* send command */
            channel.write(buffer);

            channel.flush();

            /* debug code */
            //System.out.println("*** Sent packet: " + this.toString());

            /* Ask the subclass for the apropriate response */
            NXTResponse response = this.getResponse();
            //System.out.println("Response: " + response);
            response.receiveResponse(channel);
            return response;

        } else {
            buffer[COMMAND_TYPE_INDEX] = DIRECT_COMMAND_NO_RESPONSE;

            /* send command */
            channel.write(buffer);
            channel.flush();

            /* don't need to wait for a response*/
            return null;
        }
    }

    /**
     * @return The <code>NXTResponse</code> object that know how to read the
     * response to this command.
     */
    protected abstract NXTResponse getResponse();

    public int getPacketLength() {
        return buffer.length;
    }
    /**
     * Returns the response requirements for the command.
     *
     * @return The response requirement.
     */
    public boolean isResponseRequired() {
        return responseRequired;
    }

    /**
     * Sets the requirement for a response to the command.
     * If a response to a command is not strictly necessary that this should be set to false in
     * order to achieve a faster communication.
     *
     * @param responseRequired <code>true</code> if a response is required, <code>false</code> if a response is not required.
     */
    public void setResponseRequired(boolean responseRequired) {
        this.responseRequired = responseRequired;
    }

    /**
     * Formats the command packet in hex form.
     *
     * @return The command packet in hex form.
     */
    public String toString() {
        Formatter f = new Formatter();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buffer.length; i++) {
            f.format("%x ", buffer[i]);
        }
        
        return f.toString();
    }
}
