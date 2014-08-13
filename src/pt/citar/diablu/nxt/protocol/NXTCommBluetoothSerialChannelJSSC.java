/*
 * NXTCommBluetoothSerialChannel.java
 *
 * Created on 21 de Janeiro de 2007, 12:48
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

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTCommBluetoothSerialChannelJSSC implements NXTCommChannel {
    private static final int BAUD = 9600;
   

    private SerialPort port;

    /** Creates a new instance of NXTCommBluetoothChannel */
    public NXTCommBluetoothSerialChannelJSSC() {
    }

    public NXTCommBluetoothSerialChannelJSSC(String commPort) throws RuntimeException {
        openPort(commPort);
    }

    public NXTResponse sendCommand(NXTCommand command) throws IOException {
    	//System.out.println(command);
        /* send Length of packet, this is only necessary when sending via bluetooth */
        int length = command.getPacketLength();
        //System.out.println(length);
        this.write((byte) length); // LSB
        this.write((byte) (0xff & (length >> 8)));   // MSB

        return command.sendCommand(this);
    }
    
    public void openChannel(Object channel) throws RuntimeException {

        openPort((String)channel);
    }
    
    public void closeChannel() throws IOException {

            closePort();

    }
    
    private void openPort(String commPort) throws RuntimeException {
    	port = new SerialPort(commPort);
        try {
        	port.openPort();//Open serial port
        	port.setParams(BAUD, 
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
            
        } catch (SerialPortException ex) {
        	ex.printStackTrace();
        	System.out.println(ex.getMessage());
        	throw new RuntimeException (ex);
        }
    }

    private void closePort() throws RuntimeException  {
       
        if (port != null) {
            try {
				port.closePort();
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException (e);
			}
        }
    }

	@Override
	public int read() throws RuntimeException {
		try {
			int a[] = new int[1];
			a = port.readIntArray(1);
			return a[0];
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int read(byte[] buffer) throws RuntimeException {
		try {
			java.lang.System.arraycopy(port.readBytes(buffer.length), 0, buffer, 0, buffer.length);
			 return buffer.length;
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void write(byte b) throws RuntimeException {
		try {
			port.writeByte(b);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void write(byte[] buffer) throws RuntimeException {
		try {
			port.writeBytes(buffer);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void flush() throws RuntimeException {
		
		
	}


}
