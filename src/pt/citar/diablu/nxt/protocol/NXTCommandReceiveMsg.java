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
public class NXTCommandReceiveMsg extends NXTCommand {
    
    /**
     * byte number where the mailboxes are stored
     */
    private final static int MAILBOX_REMOTE_INDEX = 2;
    private final static int MAILBOX_LOCAL_INDEX = 3;

    /**
     * byte number where the clear boolean is stored
     */
    private final static int REMOVE_BYTE = 4;

    /**
     * The mailbox number to send the message to.
     */
    private int mailbox = 0;
    
    /**
     * Whether or not the mailbox should be cleared after reading.
     */
    private boolean remove = false;

    /**
     * The response to this command.
     */
    private NXTResponseReceiveMsg response;

    public NXTCommandReceiveMsg() {
        this(0,false);

    }
    /**
     * Constructs a new <code>NXTCommandSendMsg</code> object with a given frequency and duration and
     * with no response requirement.
     *
     * @param Mailbox
     * @param Message (string)
     */
    public NXTCommandReceiveMsg(int mailbox) {

        this(mailbox, false);
    }

    /**
     * Represents the MESSAGEREAD Command.
     * The response to this command is a <code>NXTResponseReceiveMsg</code>.
     *
     * @author Jorge Cardoso
     * @author Dave Musicant
     * @see NXTResponseReceiveMsg
     *
     * @param mailbox Mailbox number on NXT brick to read message from
     * @param remove true indicates that NXT mailbox should be cleared after reading, false indicates otherwise
     */
    public NXTCommandReceiveMsg(int mailbox, boolean remove) {
        // When receiving a message, a response is always required
        super(true);
        
        buffer = new byte[5]; 
        buffer[0] = (byte)0x00; //no response required
        buffer[1] = (byte)0x13; //send message command
        
        this.mailbox = mailbox;
        this.remove = remove;
        response = new NXTResponseReceiveMsg();
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

        buffer[MAILBOX_REMOTE_INDEX] = (byte) mailbox;
        buffer[MAILBOX_LOCAL_INDEX] = (byte) mailbox;
    }

    /**
     * Returns the current status of the remove flag
     * 
     * @return The remove flag
     */
    public boolean getRemoveFlag() {
        return remove;
    }
    
    /**
     * Sets the value of the remove flag
     *
     * @param remove true or false
     */
    public void setRemoveFlag(boolean remove) {
        this.remove = remove;
        buffer[REMOVE_BYTE] = (byte)(remove ? 1 : 0);
    }
    
    /**
     * Returns actual string message obtained from brick
     *
     * @return The message
     */
    public String getMessageString() {
        return new String(response.getData());
    }

}
