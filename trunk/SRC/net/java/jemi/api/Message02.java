/*
 * jEMI - Java implementation of the EMI Specification
 *
 * Copyright (c) 2008 Gon�alo Pena Duarte Guerreiro Pereira
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.java.jemi.util.EncodingUtil;

public class Message02 implements MessageData {

	private final char FIELD_SEPARATOR = '/';

	/**
	 * NPL   String of num. char. M Number of parameters in the following
	 *                              RAd:s list.
	 */
	private String addressCodeRecipientNumber = "";

	/**
	 * RAd:s String of num. char. M List of parameters: Each parameter consists
	 *                              of AdC Address code recipient, maximum
	 *                              length     is   16   digits     with optional
	 *                              legitimisation code for all calls.
	 */
	private List addressCodeRecipients = new ArrayList();

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
	 * MT    1 numeric char.      M Message type. Associated parameters
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

	public String getAddressCodeRecipientNumber() {
		return addressCodeRecipientNumber;
	}

	public void setAddressCodeRecipientNumber(String addressCodeRecipientNumber) {
		this.addressCodeRecipientNumber = addressCodeRecipientNumber;
	}

	public List getAddressCodeRecipients() {
		return addressCodeRecipients;
	}

	public void setAddressCodeRecipients(List addressCodeRecipients) {
		this.addressCodeRecipients = addressCodeRecipients;
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

	public Message02(List addressCodeRecipients, String messageData) {
		setAddressCodeRecipientNumber(String.valueOf(addressCodeRecipients.size()));

		setAddressCodeRecipients(addressCodeRecipients);

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

		buffer.append(addressCodeRecipientNumber);
		buffer.append(FIELD_SEPARATOR);

		for (Iterator iterator = addressCodeRecipients.iterator(); iterator.hasNext();) {
			buffer.append((String) iterator.next());
			buffer.append(FIELD_SEPARATOR);
		}

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
		return 2;
	}

}
