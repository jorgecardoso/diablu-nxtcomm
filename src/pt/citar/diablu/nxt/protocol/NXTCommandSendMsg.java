/*
 * NXTCommandPlayTone.java
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
 * Represents the SENDMESSAGE Command.
 * The response to this command (if required) is a <code>NXTResponseStatus</code>.
 *
 * <br>Example 1:
 * <pre>

 * </pre>
 * Example 2:
 * <pre>

 * </pre>
 * @author Anton Vanhoucke & Jorge Cardoso
 * @see NXTResponseStatus
 */
public class NXTCommandSendMsg extends NXTCommand {
    
    /**
     * byte number where the mailbox is stored
     */
    private final static int MAILBOX_INDEX = 2;

    /**
     * byte number where the message lenght is stored
     */
    private final static int MSG_LENGTH_INDEX = 3;

    /**
     * The mailbox number to send the message to.
     */
    private int mailbox = 0;

    /**
     * The string message.
     */
    private String message;
    private byte[] header = new byte[3];

    /**
     * The response to this command.
     */
    private NXTResponseStatus response;

    public NXTCommandSendMsg() {
        this(0,"");

    }
    /**
     * Constructs a new <code>NXTCommandSendMsg</code> object with a given frequency and duration and
     * with no response requirement.
     *
     * @param Mailbox
     * @param Message (string)
     */
    public NXTCommandSendMsg(int mailbox, String message) {

        this(mailbox, message, false);
    }

    /**
     * Constructs a new <code>NXTCommandSendMsg</code> object with a give message and mailbox number to
     * send to.
     *
     * @param mailbox Mailbox number for your NXT-G block
     * @param message Message string
     * @param responseRequired Indicates if a response to this command is required.
     */
    public NXTCommandSendMsg(int mailbox, String message, boolean responseRequired) {
        /* Set the response required parameter using the base class */
        super(responseRequired);
        
        //buffer = new byte[4+message.length()]; 
        header[0] = (byte)0x00; //no response required
        header[1] = (byte)0x09; //send message command
        
        response = new NXTResponseStatus();
    }

    protected NXTResponse getResponse() {
        return this.response;
    }

    /**
     * Returns the mailbox number when the command is
     * sent.
     *
     * @return The mailbox number
     */
    public int getMailbox() {
        return mailbox;
    }

    /**
     * Sets the mailbox number to send the message to. 
     * @param mailbox (0->mailbox 1)
     */
    public void setMailbox(int mailbox) {
        this.mailbox = mailbox-1;

        header[MAILBOX_INDEX] = (byte) mailbox;

    }

    /**
     * Returns the current message when the command is
     * sent.
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message. String.
     */
    public void setMessage(String message) {
        this.message = message;
        int len;
        buffer = new byte[5+message.length()]; //add space for 4 header bytes and a zero end byte
        len = message.length();
        buffer[MSG_LENGTH_INDEX] = (byte) (len+1); //add 1 to the length to include the zero byte at the end
        System.arraycopy(message.getBytes(), 0, buffer, 4, len); //copy the message into the buffer after byte 4
        System.arraycopy(header, 0, buffer, 0, 3); //copy the command header into the buffer
        
    }



}
