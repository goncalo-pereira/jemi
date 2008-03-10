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

import net.java.jemi.util.EncodingUtil;

public class Message03 implements MessageData {
	
	private final char FIELD_SEPARATOR = '/';
	
	/**
	 * RAd  String of num. char. M AdC Address code recipient, maximum
	 *                             length is 16 digits, combined with optional
	 *                             legitimisation code for all calls.
	 */
	private String addressCodeRecipient = "";
	
	/**
	 * OAdC String of num. char. O Address code originator, maximum length is
	 *                             16 digits.
	 */
	private String addressCodeOriginator = "";
	
	/**
	 * AC   String of char.      O Authentication code originator.
	 */
	private String authenticationCodeOriginator = "";
	
	/**
	 * NPL  String of num. char. M Number of parameters in the following GA:s
	 *                             list. Must be '0'.
	 */
	private String gaListLength = "";
	
	/**
	 * GA:s  String of char.      O List of additional GA:s requested by the
	 *                              calling party. Not present because NPL = 0.
	 */
	private String gaList = "";
	
	/**
	 * RP    Char '1'             O Repetition requested. Must be left empty.
	 */
	private String repetitionRequested = "";
	
	/**
	 * PR    Char '1' or char '3' O Priority request 1 or 3. Must be left empty.
	 */
	private String priorityRequest = "";
	
	/**
	 * LPR   String of num. char. O Legitimisation code for priority requested.
	 *                              Must be left empty.
	 */
	private String legitimisationCodePR = "";
	
	/**
	 * UR    Char '1'             O Urgent message indicator request. Must be
	 *                              left empty.
	 */
	private String urgentRequest = "";
	
	/**
	 * LUR   String of num. char. O Legitimisation code for urgent message.
	 *                              Must be left empty.
	 */
	private String legitimisationCodeUR = "";
	
	/**
	 * RC    Char '1'             O Reverse charging request. Must be left
	 *                              empty.
	 */
	private String reverseChargingRequest = "";
	
	/**
	 * LRC   String of num. char. O Legitimisation code for reverse charging.
	 *                              Must be left empty.
	 */
	private String legitimisationCodeRC = "";
	
	/**
	 * DD    Char '1'             O Deferred delivery request.
	 */
	private String deferredDelivery = "";
	
	/**
	 * DDT   10 num. char.        O Deferred delivery time DDMMYYHHmm.
	 */
	private String deferredDeliveryTime = "";
	
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

	
	public String getGaListLength() {
		return gaListLength;
	}

	
	public void setGaListLength(String gaListLength) {
		this.gaListLength = gaListLength;
	}

	
	public String getGaList() {
		return gaList;
	}

	
	public void setGaList(String gaList) {
		this.gaList = gaList;
	}

	
	public String getRepetitionRequested() {
		return repetitionRequested;
	}

	
	public void setRepetitionRequested(String repetitionRequested) {
		this.repetitionRequested = repetitionRequested;
	}

	
	public String getPriorityRequest() {
		return priorityRequest;
	}

	
	public void setPriorityRequest(String priorityRequest) {
		this.priorityRequest = priorityRequest;
	}

	
	public String getLegitimisationCodePR() {
		return legitimisationCodePR;
	}

	
	public void setLegitimisationCodePR(String legitimisationCodePR) {
		this.legitimisationCodePR = legitimisationCodePR;
	}

	
	public String getUrgentRequest() {
		return urgentRequest;
	}

	
	public void setUrgentRequest(String urgentRequest) {
		this.urgentRequest = urgentRequest;
	}

	
	public String getLegitimisationCodeUR() {
		return legitimisationCodeUR;
	}

	
	public void setLegitimisationCodeUR(String legitimisationCodeUR) {
		this.legitimisationCodeUR = legitimisationCodeUR;
	}

	
	public String getReverseChargingRequest() {
		return reverseChargingRequest;
	}

	
	public void setReverseChargingRequest(String reverseChargingRequest) {
		this.reverseChargingRequest = reverseChargingRequest;
	}

	
	public String getLegitimisationCodeRC() {
		return legitimisationCodeRC;
	}

	
	public void setLegitimisationCodeRC(String legitimisationCodeRC) {
		this.legitimisationCodeRC = legitimisationCodeRC;
	}

	
	public String getDeferredDelivery() {
		return deferredDelivery;
	}

	
	public void setDeferredDelivery(String deferredDelivery) {
		this.deferredDelivery = deferredDelivery;
	}

	
	public String getDeferredDeliveryTime() {
		return deferredDeliveryTime;
	}

	
	public void setDeferredDeliveryTime(String deferredDeliveryTime) {
		this.deferredDeliveryTime = deferredDeliveryTime;
	}

	
	public boolean isAlpha() {
		return isAlpha;
	}

	
	public void setAlpha(boolean isAlpha) {
		this.isAlpha = isAlpha;
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
	
	
	public Message03(String addressCodeRecipient, String messageData) {
		
	}

	public String getData() {
		StringBuffer buffer = new StringBuffer();
		
		
		//TODO: Acabar

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
		return 3;
	}

}
