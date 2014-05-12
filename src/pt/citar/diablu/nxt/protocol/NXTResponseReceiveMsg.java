/*
 * NXTResponseLSRead.java
 *
 * Created on 20 de Janeiro de 2007, 13:52
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
import java.io.IOException;

/**
 * Encapsulates the response to the MESSAGEREAD command to the NXTBrick.
 *
 * @author Jorge Cardoso
 * @author Dave Musicant
 */
public class NXTResponseReceiveMsg extends NXTResponse {

    
    /**
     * The size of the message.
     */
    private static final int MESSAGE_SIZE = 4;

     /**
     * The Data index on the buffer.
     */
    private static final int DATA_INDEX = 5;
    
    
     /**
     * Returns the number of bytes read.
     *
     * @return The number of bytes read.
     */
    public int getMessageSize() {
        return buffer[MESSAGE_SIZE];
    }
    
    /**
     * Returns the message data.
     *
     *@return The message data. Always a 59 byte array.
     */
    public byte[] getData() {
       byte [] data = new byte[59];
       System.arraycopy(buffer, DATA_INDEX, data, 0, 59);
       return data;
    }
}

