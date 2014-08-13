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

import gnu.io.*;

/**
 *
 * @author Jorge Cardoso
 */
public class NXTCommBluetoothSerialChannelRXTX implements NXTCommChannel {
    private static final int BAUD = 9600;
    private InputStream is;
    private OutputStream os;

    private SerialPort port;

    /** Creates a new instance of NXTCommBluetoothChannel */
    public NXTCommBluetoothSerialChannelRXTX() {
    }

    public NXTCommBluetoothSerialChannelRXTX(String commPort) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException{
        openPort(commPort);
    }

    public NXTResponse sendCommand(NXTCommand command) throws IOException {

        /* send Length of packet, this is only necessary when sending via bluetooth */
        int length = command.getPacketLength();
        os.write((byte) length); // LSB
        os.write((byte) (0xff & (length >> 8)));   // MSB

        return command.sendCommand(this);
    }
    
    public void openChannel(Object channel) throws UnsupportedCommOperationException, 
            NoSuchPortException, PortInUseException, IOException {

        openPort((String)channel);
    }
    
    public void closeChannel() throws IOException {

            closePort();

    }
    
    private void openPort(String commPort) throws IOException, NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        CommPortIdentifier portID = null;

        portID = CommPortIdentifier.getPortIdentifier(commPort);

        /* try to open the port with a timeout of 15 seconds */
        if (portID != null) {

           port = (SerialPort) portID.open("SerialSim", 15*1000);
           port.setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

           is = port.getInputStream();
           os = port.getOutputStream();
        }
    }

    private void closePort() throws IOException {
        if (is != null) {
            is.close();
        }
        if (os != null) {
            os.close();
        }
        if (port != null) {
            port.close();
        }
    }

	@Override
	public int read() throws RuntimeException {
		try {
			return is.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int read(byte[] buffer) throws RuntimeException {
		try {
			return is.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void write(byte b) throws RuntimeException {
		try {
			os.write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	@Override
	public void write(byte[] buffer) throws RuntimeException {
		try {
			os.write(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void flush() throws RuntimeException {
		try {
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}


}
