/*
 * NXTResponse.java
 *
 * Created on 19 de Janeiro de 2007, 21:39
 *
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
import java.util.Formatter;


/**
 * Represents a Response to a Command that can be received from the NXTBrick. This class cannot be instantiated.
 * Only subclasses which represent concrete Responses should be used.
 *
 * Subclasses should implement the <code>receiveResponse()</code> method and any specific parameter getter methods.
 * @author Jorge Cardoso
 */
public abstract class NXTResponse {
    /* The position of the status byte in the response packet */
    protected static final int STATUS_BYTE_INDEX = 2;


    /**
     * Success status code.
     */
    public static final byte SUCCESS = 0x00;
    /**
     * PENDING_COMMUNICATION_TRANSACTION_IN_PROGRESS status code.
     */
    public static final byte PENDING_COMMUNICATION_TRANSACTION_IN_PROGRESS = 0x20;
    public static final byte SPECIFIED_MAILBOX_QUEUE_IS_EMPTY = 0x40;
    public static final byte REQUEST_FAILED = (byte)0xBD;
    public static final byte UNKNOWN_COMMAND_OPCODE = (byte)0xBE;
    public static final byte INSANE_PACKET = (byte)0xBF;
    public static final byte DATA_CONTAINS_OUT_OF_RANGE_VALUES = (byte)0xC0;
    public static final byte COMMUNICATION_BUS_ERROR = (byte)0xDD;
    public static final byte NO_FREE_MEMORY_IN_COMMUNICATION_BUFFER = (byte)0xDE;
    public static final byte SPECIFIED_CHANNEL_CONNECTION_IS_NOT_VALID = (byte)0xDF;
    public static final byte SPECIFIED_CHANNEL_CONNECTION_NOT_CONFIGURED_OR_BUSY = (byte)0xE0;
    public static final byte NO_ACTIVE_PROGRAM = (byte)0xEC;
    public static final byte ILLEGAL_SIZE_SPECIFIED = (byte)0xED;
    public static final byte ILLEGAL_MAILBOX_QUEUE_ID_SPECIFIED = (byte)0xEE;
    public static final byte ATTEMPTED_TO_ACCESS_INVALID_FIELD_OF_A_STRUCTURE = (byte)0xEF;
    public static final byte BAD_INPUT_OR_OUTPUT_SPECIFIED = (byte)0xF0;
    public static final byte INSUFFICIENT_MEMORY_AVAILABLE = (byte)0xFB;
    public static final byte BAD_ARGUMENTS = (byte)0xFF;

    /**
     * The response packet.
     */
    protected byte []buffer;

    /**
     * The length of the response packet.
     */
    protected int packetLength;

    /**
     * Constructs a new response object.
     */
    public NXTResponse() {
    }

    /**
     * Receives a response from the NXTBrick.
     *
     * Subclasses should verify the packet.
     */
    public void receiveResponse(InputStream is) throws IOException {
        readLength(is);
        buffer  = new byte[packetLength];
        is.read(buffer);
    }


    private void readLength(InputStream is) throws IOException {
        /* wait for output from NXTBrick */
        packetLength = 0;
        do {
            packetLength  = is.read();
        } while (packetLength < 0);
        int lengthMSB = is.read();
        packetLength = (0xff & packetLength) | ((0xff & lengthMSB) << 8);

       // System.out.println(packetLength);
    }

    /**
     * Returns the status of the response. A text message describing the status can be obtained by
     * invoking the <code>toString()</code> method.
     *
     * @return The status code of the response.
     */
    public int getStatus() {
        return buffer[STATUS_BYTE_INDEX];
    }

    /**
     * Returns a textual representation of the status code.
     *
     * @return The text describing the status code.
     */
    public String getStatusDescription() {
        switch (this.getStatus()) {
            case SUCCESS: return "Success";
            case PENDING_COMMUNICATION_TRANSACTION_IN_PROGRESS: return "Pending communication transaction in progress";
            case SPECIFIED_MAILBOX_QUEUE_IS_EMPTY: return "Specified mailbox queue is empty";
            case REQUEST_FAILED: return "Request failed (i.e. specified file not found)";
            case UNKNOWN_COMMAND_OPCODE: return "Unknown command opcode";
            case INSANE_PACKET: return "Insane packet";
            case DATA_CONTAINS_OUT_OF_RANGE_VALUES: return "Data contains out-of-range values";
            case COMMUNICATION_BUS_ERROR: return "Communication bus error";
            case NO_FREE_MEMORY_IN_COMMUNICATION_BUFFER: return "No free memory in communication buffer";
            case SPECIFIED_CHANNEL_CONNECTION_IS_NOT_VALID: return "Specified channel/connection is not valid";
            case SPECIFIED_CHANNEL_CONNECTION_NOT_CONFIGURED_OR_BUSY: return "Specified channel/connection not configured or busy";
            case NO_ACTIVE_PROGRAM: return "No active program";
            case ILLEGAL_SIZE_SPECIFIED: return "Illegal size specified";
            case ILLEGAL_MAILBOX_QUEUE_ID_SPECIFIED: return "Illegal mailbox queue ID specified";
            case ATTEMPTED_TO_ACCESS_INVALID_FIELD_OF_A_STRUCTURE: return "Attempted to access invalid field of a structure";
            case BAD_INPUT_OR_OUTPUT_SPECIFIED: return "Bad input or output specified";
            case INSUFFICIENT_MEMORY_AVAILABLE: return "Insufficient memory available";
            case BAD_ARGUMENTS: return "Bad arguments";
            default: return "Panic!: Unkown Status Response!";
        }
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
            //f.format("%x ", buffer[i]);
        }
        return f.toString();

    }
}
