/*
 * NXTCommandLSRead.java
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
 * Represents the LS Write Command.
 * The response to this command (if required) is a <code>NXTResponseStatus</code>.
 *
 *
 * @author Jorge Cardoso
 * @see NXTResponseStatus
 */
public class NXTCommandLSRead extends NXTCommand {
    /**
     * The LS Read command ID.
     */
    private byte COMMAND_LS_READ = 0x10;
    
    /**
     * The input port value index on the buffer.
     */
    private byte INPUT_PORT_INDEX = 2;
    
  
    
    /**
     * The response to this command.
     */
    private NXTResponseLSRead response;
    
    public NXTCommandLSRead() {
        //this(0, 0);
        
    }
    
    
    /**
     * Constructs a new <code>NXTCommandLSRead</code> object with a given port and response requirement.
     *    
     *
     * @param inputPort The input port to send the command to.   
     * @param responseRequired Indicates if a response to this command is required.
     */
    public NXTCommandLSRead(byte inputPort, boolean responseRequired) {
        /* Set the response required parameter using the base class */
        super(responseRequired);
        
        buffer = new byte[3]; 
        
        buffer[COMMAND_ID_INDEX] = COMMAND_LS_READ;
        buffer[INPUT_PORT_INDEX] = inputPort;
        
        response = new NXTResponseLSRead();
    }
    
    protected NXTResponse getResponse() {
        return this.response;
    }
    
  
    
}


