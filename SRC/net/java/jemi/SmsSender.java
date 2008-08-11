/*
 * jEMI - Java implementation of the EMI Specification
 *
 * Copyright (c) 2008 Goncalo Pena Duarte Guerreiro Pereira
 *
 * This file is part of jEMI.
 *
 * jEMI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * jEMI is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with jEMI.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package net.java.jemi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import net.java.jemi.api.EmiMessage;
import net.java.jemi.api.MessageData;
import net.java.jemi.api.Response;
import net.java.jemi.error.JemiException;

public class SmsSender {

	private static final int DEFAULT_TIMEOUT = 30000;

	private String serverIP = null;
	private int serverPort = -1;
	private int timeout = DEFAULT_TIMEOUT;

	public SmsSender(String serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		//TODO: Validar???
	}

	public void setTimeout(int milliseconds) {
		timeout = milliseconds;
	}

	public void send(MessageData messageData) throws JemiException {
		send(new EmiMessage(messageData));
	}

	public void send(EmiMessage emiMessage) throws JemiException {
		String msgToSend = emiMessage.getMessage();

		Socket smsc = null;
		try {
			smsc = new Socket();

			smsc.connect(new InetSocketAddress(serverIP, serverPort), timeout);

			smsc.setSoTimeout(timeout);

			smsc.getOutputStream().write(msgToSend.getBytes());

			byte[] aux = new byte[5000]; //TODO: são necessários quantos?

			int responseLen = smsc.getInputStream().read(aux);

			if (responseLen <= 0) {
				throw new JemiException("No response from server " + serverIP + ":" + serverPort);
			}

			byte[] responseBytes = new byte[responseLen];
			for (int i = 0; i < responseLen; i++) {
				responseBytes[i] = aux[i];
			}

			Response response = new Response(responseBytes);

			if (response.getErrorCode() != 0) {
				throw new JemiException("Server error: code=" + response.getErrorCode() + ", message=" + response.getErrorMsg());
			}

		} catch (JemiException e) {
			throw e;
		} catch (Exception e) {
			throw new JemiException(e);
		} finally {
			close(smsc);
		}

	}


	private void close(Socket socket) {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {}
		}
	}

}
