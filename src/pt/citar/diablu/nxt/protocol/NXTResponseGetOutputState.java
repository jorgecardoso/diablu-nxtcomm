/*
 * NXTResponseGetOutputState.java
 *
 * Created on 13 de Setembro de 2007, 11:51
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.citar.diablu.nxt.protocol;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTResponseGetOutputState  extends NXTResponse {
    
    /**
     * The output port value index on packet.
     */
    private byte OUTPUT_PORT_INDEX = 3;

    /**
     * The power set point index on packet.
     */
    private byte POWER_SET_POINT_INDEX = 4;

    /**
     * The mode index on packet.
     */
    private byte MODE_INDEX = 5;

    /**
     * The regulation mode index on packet.
     */
    private byte REGULATION_MODE_INDEX = 6;

    /**
     * The turn ratio index on packet.
     */
    private byte TURN_RATIO_INDEX = 7;

    /**
     * The run state index on packet.
     */
    private byte RUN_STATE_INDEX = 8;

    /**
     * The tacho limit index on packet (four bytes).
     */
    private byte TACHO_LIMIT_INDEX = 9;

    /**
     * The tacho count index on packet (four bytes).
     */
    private byte TACHO_COUNT_INDEX = 13;

    /**
     * The block tacho count index on packet (four bytes).
     */
    private byte BLOCK_TACHO_COUNT_INDEX = 17;
    
    
    /**
     * The toration count index on packet (four bytes).
     */
    private byte ROTATION_COUNT_INDEX = 21;    
    
    
    /**
     * @return The output port. 
     */
    public byte getOutputPort() {
        return buffer[OUTPUT_PORT_INDEX];
    }

    /**
     * @return The power set point [-100;100]. 
     */
    public byte getPowerSetPoint() {
        return buffer[POWER_SET_POINT_INDEX];
    }    
    
    /**
     * @return The output mode. See mode constant values.
     */
    public byte getOutputMode() {
        return buffer[MODE_INDEX];
    }
 
    
    /**
     * @return The regulation mode. See regulation mode constant values.
     */
    public byte getRegulationMode() {
        return buffer[REGULATION_MODE_INDEX];
    }
    
     /**
     * @return The turn ratio [-100; 100]
     */
    public byte getTurnRatio() {
        return buffer[TURN_RATIO_INDEX];
    }   
    
    /**
     * @return The run state.
     */
    public byte getRunState() {
        return buffer[RUN_STATE_INDEX];
    }   
    
    
    /**
     * @return The current limit on a movement in progress, if any.
     */
    public long getTachoLimit() {
        return  ((0xff & buffer[TACHO_LIMIT_INDEX]) | ((0xff & buffer[TACHO_LIMIT_INDEX+1]) << 8) 
                | ((0xff & buffer[TACHO_LIMIT_INDEX+2]) << 16) 
                | ((0xff & buffer[TACHO_LIMIT_INDEX+3]) << 24));
    }
    
    /**
     * @return The number of counts since last reset of the motor. (Internal count).
     */
    public long getTachoCount() {
        return  ((0xff & buffer[TACHO_COUNT_INDEX]) | ((0xff & buffer[TACHO_COUNT_INDEX+1]) << 8) 
                | ((0xff & buffer[TACHO_COUNT_INDEX+2]) << 16) 
                | ((0xff & buffer[TACHO_COUNT_INDEX+3]) << 24));
    }
    
    /**
     * @return The current position relative to last programmed movement.
     */
    public long getBlockTachoCount() {
        return  ((0xff & buffer[BLOCK_TACHO_COUNT_INDEX]) | ((0xff & buffer[BLOCK_TACHO_COUNT_INDEX+1]) << 8) 
                | ((0xff & buffer[BLOCK_TACHO_COUNT_INDEX+2]) << 16) 
                | ((0xff & buffer[BLOCK_TACHO_COUNT_INDEX+3]) << 24));
    }    

        /**
     * @return The current position relative to last reset of the rotation sensor for this motor.
     */
    public long getRotationCount() {
        return  ((0xff & buffer[ROTATION_COUNT_INDEX]) | ((0xff & buffer[ROTATION_COUNT_INDEX+1]) << 8) 
                | ((0xff & buffer[ROTATION_COUNT_INDEX+2]) << 16) 
                | ((0xff & buffer[ROTATION_COUNT_INDEX+3]) << 24));
    }  
}
