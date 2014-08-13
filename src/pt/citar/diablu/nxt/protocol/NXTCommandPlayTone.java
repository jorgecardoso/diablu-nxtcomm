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




/**
 * Represents the PLAYTONE Command.
 * The response to this command (if required) is a <code>NXTResponseStatus</code>.
 *
 * <br>Example 1:
 * <pre>

 * </pre>
 * Example 2:
 * <pre>

 * </pre>
 * @author Jorge Cardoso
 * @see NXTResponseStatus
 */
public class NXTCommandPlayTone extends NXTCommand {
    
    /**
     * Frequency LSB index on buffer;
     */
    private final static int FREQUENCY_LSB_INDEX = 2;

    /**
     * Frequency MSB index on buffer;
     */
    private final static int FREQUENCY_MSB__INDEX = 3;

    /**
     * Duration LSB index on buffer;
     */
    private final static int DURATION_LSB_INDEX = 4;

     /**
     * Duration MSB index on buffer;
     */
    private final static int DURATION_MSB_INDEX = 5;



    /**
     * The frequency of the tone to play.
     */
    private int frequency;

    /**
     * The duration of the tone.
     */
    private int duration;


    /**
     * The response to this command.
     */
    private NXTResponseStatus response;

    public NXTCommandPlayTone() {
        this(0, 0);

    }
    /**
     * Constructs a new <code>NXTCommandPlayTone</code> object with a given frequency and duration and
     * with no response requirement.
     *
     * @param frequency The frequency of the tone. Only the two least significant bytes are considered.
     * @param duration The duration of the tone. Only the two least significatn bytes are considered.
     */
    public NXTCommandPlayTone(int frequency, int duration) {

        this(frequency, duration, false);
    }

    /**
     * Constructs a new <code>NXTCommandPlayTone</code> object with a given frequency, duration and
     * response requirement.
     *
     * @param frequency The frequency of the tone. Only the two least significant bytes are considered.
     * @param duration The duration of the tone. Only the two least significatn bytes are considered.
     * @param responseRequired Indicates if a response to this command is required.
     */
    public NXTCommandPlayTone(int frequency, int duration, boolean responseRequired) {
        /* Set the response required parameter using the base class */
        super(responseRequired);

        buffer = new byte[] {(byte)0x80, 0x03, 0x00, 0x00, 0x00, 0x00};

        this.setFrequency(frequency);
        this.setDuration(duration);
        response = new NXTResponseStatus();
    }

    protected NXTResponse getResponse() {
        return this.response;
    }

    /**
     * Returns the current frequency set to be played when the command is
     * sent.
     *
     * @return The frequency of the tone.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Sets the frequency of the tone to play.
     * @param frequency The frequency to play. On the two least significant bytes are considered.
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;

        buffer[FREQUENCY_LSB_INDEX] = (byte) frequency;

        buffer[FREQUENCY_MSB__INDEX] = (byte) (frequency >> 8);
    }

    /**
     * Returns the current duration set to be played when the command is
     * sent.
     *
     * @return The duration of the tone.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the tone to play.
     * @param duration The frequency to play. On the two least significant bytes are considered.
     */
    public void setDuration(int duration) {
        this.duration = duration;

        buffer[DURATION_LSB_INDEX] = (byte) duration;

        buffer[DURATION_MSB_INDEX] = (byte) (duration >> 8);
    }



}
