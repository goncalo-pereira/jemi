/*
 * jEMI - Java implementation of the EMI Specification
 *
 * Copyright (c) 2008 Gonçalo Pena Duarte Guerreiro Pereira
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
package net.java.jemi.api;

import net.java.jemi.error.JemiException;
import net.java.jemi.util.EncodingUtil;

public class EmiMessage {

	private final char SEPARATOR = 0x2f; // '/'
	private final char STX = 0x02;
	private final char ETX = 0x03;

	private MessageData messageData = null;

	public EmiMessage(MessageData messageData) throws JemiException {
		if (messageData == null) {
			throw new JemiException("MessageData cannot be null.");
		}

		this.messageData = messageData;
	}

	public EmiMessage(String origin, String destination, String message, boolean notify) {
		this.messageData = getMessageData(origin, destination, message, notify);
	}

	private MessageData getMessageData(String origin, String destination, String message, boolean notify) {
		boolean alphaOrig = false;

		for (int i = 0; i < origin.length(); i++) {
			char ch = origin.charAt(i);
			if (ch < '0' || ch > '9') {
				alphaOrig = true;
				break;
			}
		}

		MessageData messageData = null;

		if (alphaOrig || notify) {
			Message51 message51 = new Message51(destination, origin, "3", "", message);

			if (notify) {
				message51.setNotificationRequest("1");
				message51.setNotificationAddress(origin);
				message51.setNotificationType("1");
				message51.setNotificationPIDValue("0100");
			}

			messageData = (MessageData) message51;
		} else {
			Message01 message01 = new Message01(destination, message);
			message01.setAddressCodeOriginator(origin);

			messageData = (MessageData) message01;
		}

		return messageData;
	}

	public String getMessage() {
		/*
		 * Message format: STX<header>SEPARATOR<data>SEPARATOR<checksum>ETX
		 */
		String data = messageData.getData();

		StringBuffer content = new StringBuffer();
		content.append(getHeader(messageData.getType(), data));
		content.append(SEPARATOR);
		content.append(data);
		content.append(SEPARATOR);

		StringBuffer ret = new StringBuffer();
		ret.append(STX);
		ret.append(content);
		ret.append(EncodingUtil.getChecksum(content.toString().toCharArray()));
		ret.append(ETX);

		return ret.toString();
	}

	private String getHeader(int msgType, String msgData) {
		StringBuffer buffer = new StringBuffer();

		buffer.append(formatNumber(1, 2)); //TRN - Transaction Reference Number (2 chars)
		buffer.append(SEPARATOR);
		buffer.append(formatNumber(2 + 1 + 5 + 1 + 1 + 1 + 2 + 1 + msgData.length() + 1 + 2, 5)); //LEN - Number of IA5 chars between stx and etx (5 chars)
		buffer.append(SEPARATOR);
		buffer.append('O'); // O - Operation / R - Result (1 char)
		buffer.append(SEPARATOR);
		buffer.append(formatNumber(msgType, 2)); //OT - Operation Type (2 chars)

		return buffer.toString();
	}

	private String formatNumber(int number, int size) {

		StringBuffer mask = new StringBuffer();

		for (int i = 0; i < size; i++) {
			mask.append('0');
		}

		String strNumber = String.valueOf(number);

		//TODO: VALIDAR SE NUMBER.LENGTH > SIZE

		return mask.replace(mask.length() - strNumber.length(), mask.length(), strNumber).toString();
	}
}
