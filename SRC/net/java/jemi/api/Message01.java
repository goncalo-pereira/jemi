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
package net.java.jemi.api;

import net.java.jemi.util.EncodingUtil;

public class Message01 implements MessageData {

	private final char FIELD_SEPARATOR = '/';

	/**
	 * AdC   String of num. char. M Address code recipient, maximum length is
	 *                              16 digits.
	 */
	private String addressCodeRecipient = "";

	/**
	 * OAdC  String of num. char. O Address code originator, maximum length is
	 *                              16 digits.
	 */
	private String addressCodeOriginator = "";

	/**
	 * AC    String of char.      O Authentication code originator.
	 */
	private String authenticationCodeOriginator = "";

	/**
	 * MT    1 num. char.         M Message type. Associated parameters
	 *                              depend on the value of the message type.
	 * MT=2:
	 *  NMsg String of num. char. O Numeric message, maximum length is 160                                                      
	 *                              digits.
	 * MT=3:
	 *  AMsg String of char.      O Alphanumeric message encoded into IA5                                                       
	 *                              characters, maximum length is representing
	 *                              640 characters.
	 */
	private boolean isAlpha = false;
	private String messageType = "";
	private String messageData = "";

	public String getAddressCodeRecipient() {
		return addressCodeRecipient;
	}

	public void setAddressCodeRecipient(String addressCodeRecipient) {
		this.addressCodeRecipient = addressCodeRecipient;
	}

	public String getAddressCodeOriginator() {
		return addressCodeOriginator;
	}

	public void setAddressCodeOriginator(String addressCodeOriginator) {
		this.addressCodeOriginator = addressCodeOriginator;
	}

	public String getAuthenticationCodeOriginator() {
		return authenticationCodeOriginator;
	}

	public void setAuthenticationCodeOriginator(String authenticationCodeOriginator) {
		this.authenticationCodeOriginator = authenticationCodeOriginator;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageData() {
		return messageData;
	}

	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}


	public Message01(String addressCodeRecipient, String messageData) {
		setAddressCodeRecipient(addressCodeRecipient);

		for (int i = 0; i < messageData.length(); i++) {
			if (messageData.charAt(i) < '0' || messageData.charAt(i) > '9') {
				isAlpha = true;
				break;
			}
		}

		if (isAlpha) {
			setMessageType("3");
		} else {
			setMessageType("2");
		}

		setMessageData(messageData);
	}

	public String getData() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(addressCodeRecipient);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(addressCodeOriginator);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(authenticationCodeOriginator);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(messageType);
		buffer.append(FIELD_SEPARATOR);

		if (isAlpha) {
			buffer.append(EncodingUtil.convert2IA5(messageData));
		} else {
			buffer.append(messageData);
		}

		return buffer.toString();
	}

	public int getType() {
		return 1;
	}
}
