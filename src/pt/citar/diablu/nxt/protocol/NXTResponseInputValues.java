/*
 * NXTResponseInputValues.java
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
 * Encapsulates the response to the GETINPUTVALUES command to the NXTBrick.
 *
 * @author Jorge Cardoso
 */
public class NXTResponseInputValues extends NXTResponse {

    /**
     * No sensor type defined.
     */
    public static final byte NO_SENSOR_TYPE = 0x00;
    /**
     * Switch sensor type.
     */
    public static final byte SWITCH_TYPE = 0x01;

    /**
     * Temperature sensor type.
     */
    public static final byte TEMPERATURE_TYPE = 0x02;

    /**  */
    public static final byte REFLECTION_TYPE = 0x03;
    /**  */
    public static final byte ANGLE_TYPE = 0x04;
    /**  */
    public static final byte LIGHT_ACTIVE_TYPE = 0x05;
    /**  */
    public static final byte LIGHT_INACTIVE_TYPE = 0x06;
    /**  */
    public static final byte SOUND_DB_TYPE = 0x07;
    /**  */
    public static final byte SOUND_DBA_TYPE = 0x08;
    /**  */
    public static final byte CUSTOM_TYPE = 0x09;
    /**  */
    public static final byte LOWSPEED_TYPE = 0x0A;
    /**  */
    public static final byte LOWSPEED_9V_TYPE = 0x0B;
    /**  */
    public static final byte NO_OF_SENSOR_TYPES_TYPE = 0x0C;
    /**  */
    public static final byte COLOR_FULL = 0x0D;
    /**  */
    public static final byte COLOR_RED = 0x0E;
    /**  */
    public static final byte COLOR_GREEN = 0x0F;
    /**  */
    public static final byte COLOR_BLUE = 0x10;
    /**  */
    public static final byte COLOR_NONE = 0x11;



    /**  */
    public static final byte RAW_MODE = 0x00;
    /**  */
    public static final byte BOOLEAN_MODE = 0x20;
    /**  */
    public static final byte TRANSITION_CNT_MODE = 0x40;
    /**  */
    public static final byte PERIOD_COUNTER_MODE = 0x60;
    /**  */
    public static final byte PCT_FULL_SCALE_MODE = (byte)0x80;
    /**  */
    public static final byte CELSIUS_MODE = (byte)0xA0;
    /**  */
    public static final byte FAHRENHEIT_MODE = (byte)0xC0;
    /**  */
    public static final byte ANGLE_STEPS_MODE = (byte)0xE0;
    /**  */
    public static final byte SLOPE_MASK_MODE = 0x1F;
    /**  */
    public static final byte MODE_MASK_MODE = (byte)0xE0;

    /**
     * The input port value index on packet.
     */
    private byte INPUT_PORT_INDEX = 3;

    /**
     * The valid value index on packet.
     */
    private byte VALID_INDEX = 4;

    /**
     * The calibrated value index on packet.
     */
    private byte CALIBRATED_INDEX = 5;

    /**
     * The sensor type value index on packet.
     */
    private byte SENSOR_TYPE_INDEX = 6;

    /**
     * The sensor mode value index on packet.
     */
    private byte SENSOR_MODE_INDEX = 7;

    /**
     * Raw A/D value index on packet (two bytes).
     */
    private byte RAW_VALUE_INDEX = 8;

    /**
     * The normalized value index on packet (two bytes).
     */
    private byte NORMALIZED_VALUE_INDEX = 10;

    /**
     * Scaled value index on packet (two bytes).
     */
    private byte SCALED_VALUE_INDEX = 12;

    /**
     * Calibrated value index on packet (two bytes).
     */
    private byte CALIBRATED_VALUE_INDEX = 14;

    /**
     * @return The input port from which values were read.
     */
    public byte getInputPort() {
        return buffer[INPUT_PORT_INDEX];
    }

    /**
     * @return True, if new data should be seen as valid data.
     */
    public boolean isValid() {
        return buffer[VALID_INDEX] == 1 ? true : false;
    }

    /**
     * @return True, if calibration file fould and used for Calibrated Field.
     */
    public boolean isCalibrated() {
        return buffer[CALIBRATED_INDEX] == 1 ? true : false;
    }

    /**
     * @return The sensor type. See type constant values.
     */
    public byte getSensorType() {
        return buffer[SENSOR_TYPE_INDEX];
    }

    /**
     * @return The sensor mode. See mode constant values.
     */
    public byte getSensorMode() {
        return buffer[SENSOR_MODE_INDEX];
    }

    /**
     * @return The raw A/D value.
     */
    public int getRawValue() {
        return  (0xff & buffer[RAW_VALUE_INDEX]) | ((0xff & buffer[RAW_VALUE_INDEX+1]) << 8);
    }


    /**
     * @return Normalized A/D value (type dependent).
     */
    public int getNormalizedValue() {
         return  (0xff & buffer[NORMALIZED_VALUE_INDEX]) | ((0xff & buffer[NORMALIZED_VALUE_INDEX+1]) << 8);
    }

    /**
     * Scaled value (mode dependent).
     */
    public int getScaledValue() {
         return  (0xff & buffer[SCALED_VALUE_INDEX]) | ((0xff & buffer[SCALED_VALUE_INDEX+1]) << 8);
    }
    /**
     * Calibrated value (valued scaled according to calibration. Currently unused by the protocol).
     */
    public int getCalibratedValue() {
         return  (0xff & buffer[CALIBRATED_VALUE_INDEX]) | ((0xff & buffer[CALIBRATED_VALUE_INDEX+1]) << 8);
    }
}
