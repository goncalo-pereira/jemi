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

import java.io.UnsupportedEncodingException;

import net.java.jemi.util.BitList;
import net.java.jemi.util.EncodingUtil;

public abstract class Message5x implements MessageData {

	private final char FIELD_SEPARATOR = '/';

	/**
	 * AdC  16 Num. string Address code recipient for the SM
	 */
	private String addressCodeRecipient = "";

	/**
	 * OAdC 16 Num. string Address code originator
	 *      24 Char string If the OTOA field indicates alphanumeric OAdC.
	 */
	private String addressCodeOriginator = "";

	/**
	 * AC   16 Num. string Authentication code originator (min 4 char., max 16 char)
	 */
	private String authenticationCodeOriginator = "";

	/**
	 * NRq  1  Num. char.  Notification Request
	 *                     0 = NAdC not used
	 *                     1 = NAdC used
	 */
	private String notificationRequest = "";

	/**
	 * NAdC  16 Num. string   Notification Address
	 */
	private String notificationAddress = "";

	/**
	 * NT    1  Num. char.    Notification Type:
	 *                        Buffered message notification (BN),
	 *                        Delivery Notification (DN),
	 *                        Non-delivery notification (ND),
	 *                        0 default value, 1 = DN, 2 = ND, 3 = DN+ND, 4 =BN, 5 = BN+DN, 6 = BN+ND, 7 = all.
	 */
	private String notificationType = "";

	/**
	 * NPID  4  4 num. char.  Notification PID value:
	 *                        0100 Mobile Station
	 *                        0122 Fax Group 3
	 *                        0131 X.400
	 *                        0138 Menu over PSTN
	 *                        0139 PC appl. over PSTN (E.164)
	 *                        0339 PC appl. over X.25 (X.121)
	 *                        0439 PC appl. over ISDN (E.164)
	 *                        0539 PC appl. over TCP/IP
	 */
	private String notificationPIDValue = "";

	/**
	 * LRq   1  1 num. char.  Last Resort Address request:
	 *                        0 = LRAd not used
	 *                        1 = LRAd used
	 */
	private String lastResortAddressRequest = "";

	/**
	 * LRAd  16 Num. string   Last Resort Address
	 */
	private String lastResortAddress = "";

	/**
	 * LPID  4  4 num. char.  LRAd PID value:
	 *                        0100 Mobile Station
	 *                        0122 Fax Group 3
	 *                        0131 X.400
	 *                        0138 Menu over PSTN
	 *                        0139 PC appl. over PSTN
	 *                        0339 PC appl. over X.25 (X121)
	 *                        0439 PC appl. over ISDN (E.164)
	 *                        0539 PC appl. over TCP/IP
	 */
	private String lastResortAddressPIDValue = "";

	/**
	 * DD    1  1 num. char.  Deferred Delivery requested:
	 *                        0 = DDT not used
	 *                        1 = DDT used
	 */
	private String deferredDeliveryRequested = "";

	/**
	 * DDT   10 10 num. char. Deferred delivery time in DDMMYYHHmm
	 */
	private String deferredDeliveryTime = "";

	/**
	 * VP    10 10 num. char. Validity period in DDMMYYHHmm
	 */
	private String validityPeriod = "";

	/**
	 * RPID  4  Num. string   Replace PID, value 0000, 0071, 0095, 0127, 0192...0255.
	 */
	private String replacePID = "";

	/**
	 * SCTS  12 Num. string   Service Centre Time Stamp in DDMMYYHHmmss.
	 *                        For a Short Message this is the time stamp of the
	 *                        Short Message itself. For a Notification this is the
	 *                        time stamp of the corresponding Short Message.
	 */
	private String serviceCentreTimeStamp = "";

	/**
	 * Dst   1  1 num. char.  Delivery status:
	 *                        0 = delivered
	 *                        1 = buffered (see Rsn)
	 *                        2 = not delivered (see Rsn)
	 */
	private String deliveryStatus = "";

	/**
	 * Rsn   3  3 num. char.  Reason code, value '000'...'255'. Code can be found
	 *                        in an SMSC configuration file witch can be changed
	 *                        by the operator. (See appendix A)
	 */
	private String reasonCode = "";

	/**
	 * DSCTS 12 Num. string   Delivery time stamp in DDMMYYHHmmss.
	 *                        Indicates the actual time of delivery of the Short Message.
	 */
	private String deliveryTimeStamp = "";

	/**
	 * MT    1  1 num. char.  Message Type. Associated parameters depend on the value of MT.
	 */
	private String messageType = "";

	/**
	 * MT=2:
	 *  NMsg 640 Num. string    Numeric message.
	 * MT=3:
	 *  AMsg 640 Char. string   Alphanumeric message encoded into IA5 characters.
	 * MT=4:
	 *  NB    4  Num. char.     No. of bits in Transparent Data (TD) message.
	 *  TMsg 140 Char. string   TD message encoded into IA5 characters.
	 */
	private String numberOfBitsInTransparentDataMessage = "";
	private String messageData = "";

	/**
	 * MMS    1  1 num. char.   More Messages to Send (to the same SME)
	 */
	private String moreMessagesToSend = "";

	/**
	 * PR     1  1 char.        Priority Requested
	 */
	private String priorityRequest = "";

	/**
	 * DCs    1  1 num. char.   Data Coding scheme:
	 *                          0 = default alphabet
	 *                          1 = user defined data ('8 bit')
	 */
	private String dataCodingScheme = "";

	/**
	 * MCLs   1  1 num. char.   Message class:
	 *                          0 = message class 0
	 *                          1 = message class 1
	 *                          2 = message class 2
	 *                          3 = message class 3
	 */
	private String messageClass = "";

	/**
	 * RPI    1  1 num. char.   Reply Path:
	 *                          1 = request
	 *                          2 = response
	 */
	private String replyPath = "";

	/**
	 * CPg    1  Num. string    (reserved for Code Page)
	 */
	private String codePage = "";

	/**
	 * RPLy   1  1 num. char.   (reserved for Reply type)
	 */
	private String replyType = "";

	/**
	 * OTOA   4  4 num. char.   Originator Type Of Address:
	 *                          1139  The OadC is set to NPI telephone and TON international.
	 *                          5039  The OAdC contains an alphanumeric address (see OAdC and below).
	 *                          Leave OTOA empty for a numeric address in the OAdC.
	 */
	private String originatorTypeOfAddress = "";

	/**
	 * HPLMN  16 Num. string    Home PLMN Address
	 */
	private String homePLMNAddress = "";

	/**
	 * XSer  400 Num. string    Extra Services
	 *                          With the XSer field one or more additional services
	 *                          can be specified. These services consist of IA5
	 *                          encoded data constructed in the following common
	 *                          format: TTLLDD
	 *                          where
	 *                            TT     represents two HEX characters defining the
	 *                                   type of service. For a description of
	 *                                   available services refer to section
	 *                                   5.1.2 Description Of XSer Extra Services
	 *                            LL   represents two HEX characters defining the
	 *                                   number of octets present in the data field
	 *                                   DD. (Note that the number of HEX
	 *                                   characters in the data DD is twice the
	 *                                   number of octets)
	 *                            DD     represents a stream of HEX characters
	 *                                   defining the service specific data itself.
	 *                            If more than one additional service is to be specified
	 *                            in one message, this service information is
	 *                            concatenated without any separators, i.e.
	 *                            TT1LL1DD1 DD1TT2LL2DD2 DD2
	 *                            The above construction is designed such that in the
	 *                            future additional service types can be added to the
	 *                            XSer field.
	 */
	private String extraServices = "";

	/**
	 *RES4 x Num. string (reserved for future use)
	 */
	private String reserved4 = "";

	/**
	 *RES5 x Num. string (reserved for future use)
	 */
	private String reserved5 = "";


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


	public String getNotificationRequest() {
		return notificationRequest;
	}


	public void setNotificationRequest(String notificationRequest) {
		this.notificationRequest = notificationRequest;
	}


	public String getNotificationAddress() {
		return notificationAddress;
	}


	public void setNotificationAddress(String notificationAddress) {
		this.notificationAddress = notificationAddress;
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}


	public String getNotificationPIDValue() {
		return notificationPIDValue;
	}


	public void setNotificationPIDValue(String notificationPIDValue) {
		this.notificationPIDValue = notificationPIDValue;
	}


	public String getLastResortAddressRequest() {
		return lastResortAddressRequest;
	}


	public void setLastResortAddressRequest(String lastResortAddressRequest) {
		this.lastResortAddressRequest = lastResortAddressRequest;
	}


	public String getLastResortAddress() {
		return lastResortAddress;
	}


	public void setLastResortAddress(String lastResortAddress) {
		this.lastResortAddress = lastResortAddress;
	}


	public String getLastResortAddressPIDValue() {
		return lastResortAddressPIDValue;
	}


	public void setLastResortAddressPIDValue(String lastResortAddressPIDValue) {
		this.lastResortAddressPIDValue = lastResortAddressPIDValue;
	}


	public String getDeferredDeliveryRequested() {
		return deferredDeliveryRequested;
	}


	public void setDeferredDeliveryRequested(String deferredDeliveryRequested) {
		this.deferredDeliveryRequested = deferredDeliveryRequested;
	}


	public String getDeferredDeliveryTime() {
		return deferredDeliveryTime;
	}


	public void setDeferredDeliveryTime(String deferredDeliveryTime) {
		this.deferredDeliveryTime = deferredDeliveryTime;
	}


	public String getValidityPeriod() {
		return validityPeriod;
	}


	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}


	public String getReplacePID() {
		return replacePID;
	}


	public void setReplacePID(String replacePID) {
		this.replacePID = replacePID;
	}


	public String getServiceCentreTimeStamp() {
		return serviceCentreTimeStamp;
	}


	public void setServiceCentreTimeStamp(String serviceCentreTimeStamp) {
		this.serviceCentreTimeStamp = serviceCentreTimeStamp;
	}


	public String getDeliveryStatus() {
		return deliveryStatus;
	}


	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}


	public String getReasonCode() {
		return reasonCode;
	}


	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}


	public String getDeliveryTimeStamp() {
		return deliveryTimeStamp;
	}


	public void setDeliveryTimeStamp(String deliveryTimeStamp) {
		this.deliveryTimeStamp = deliveryTimeStamp;
	}


	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	public String getNumberOfBitsInTransparentDataMessage() {
		return numberOfBitsInTransparentDataMessage;
	}


	public void setNumberOfBitsInTransparentDataMessage(String numberOfBitsInTransparentDataMessage) {
		this.numberOfBitsInTransparentDataMessage = numberOfBitsInTransparentDataMessage;
	}


	public String getMessageData() {
		return messageData;
	}


	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}


	public String getMoreMessagesToSend() {
		return moreMessagesToSend;
	}


	public void setMoreMessagesToSend(String moreMessagesToSend) {
		this.moreMessagesToSend = moreMessagesToSend;
	}


	public String getPriorityRequest() {
		return priorityRequest;
	}


	public void setPriorityRequest(String priorityRequest) {
		this.priorityRequest = priorityRequest;
	}


	public String getDataCodingScheme() {
		return dataCodingScheme;
	}


	public void setDataCodingScheme(String dataCodingScheme) {
		this.dataCodingScheme = dataCodingScheme;
	}


	public String getMessageClass() {
		return messageClass;
	}


	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}


	public String getReplyPath() {
		return replyPath;
	}


	public void setReplyPath(String replyPath) {
		this.replyPath = replyPath;
	}


	public String getCodePage() {
		return codePage;
	}


	public void setCodePage(String codePage) {
		this.codePage = codePage;
	}


	public String getReplyType() {
		return replyType;
	}


	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}


	public String getOriginatorTypeOfAddress() {
		return originatorTypeOfAddress;
	}


	public void setOriginatorTypeOfAddress(String originatorTypeOfAddress) {
		this.originatorTypeOfAddress = originatorTypeOfAddress;
	}


	public String getHomePLMNAddress() {
		return homePLMNAddress;
	}


	public void setHomePLMNAddress(String homePLMNAddress) {
		this.homePLMNAddress = homePLMNAddress;
	}


	public String getExtraServices() {
		return extraServices;
	}


	public void setExtraServices(String extraServices) {
		this.extraServices = extraServices;
	}


	public String getReserved4() {
		return reserved4;
	}


	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}


	public String getReserved5() {
		return reserved5;
	}


	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}


	public String getData() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(addressCodeRecipient);
		buffer.append(FIELD_SEPARATOR);

		boolean alphaOriginator = false;

		for (int i = 0; i < addressCodeOriginator.length(); i++) {
			if (addressCodeOriginator.charAt(i) < '0' || addressCodeOriginator.charAt(i) > '9') {
				alphaOriginator = true;
				break;
			}
		}


		if (alphaOriginator) {
			if ((7 * addressCodeOriginator.length()) % 4 == 0) {
				buffer.append(EncodingUtil.getHexCodeStr((7 * addressCodeOriginator.length()) / 4));
			} else {
				buffer.append(EncodingUtil.getHexCodeStr((7 * addressCodeOriginator.length()) / 4 + 1));
			}

			BitList bitList;
			try {
				bitList = new BitList(addressCodeOriginator.getBytes("ISO-8859-1"), 7);
				buffer.append(EncodingUtil.getHexCodeStr(bitList.getBytes()));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			buffer.append(addressCodeOriginator);
		}


		buffer.append(FIELD_SEPARATOR);
		buffer.append(authenticationCodeOriginator);
		buffer.append(FIELD_SEPARATOR);


		buffer.append(notificationRequest);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(notificationAddress);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(notificationType);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(notificationPIDValue);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(lastResortAddressRequest);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(lastResortAddress);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(lastResortAddressPIDValue);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(deferredDeliveryRequested);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(deferredDeliveryTime);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(validityPeriod);
		buffer.append(FIELD_SEPARATOR);

		buffer.append(replacePID);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(serviceCentreTimeStamp);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(deliveryStatus);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(reasonCode);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(deliveryTimeStamp);
		buffer.append(FIELD_SEPARATOR);
		
		
		boolean alphaMsg = false;

		for (int i = 0; i < messageData.length(); i++) {
			if (messageData.charAt(i) < '0' || messageData.charAt(i) > '9') {
				alphaMsg = true;
				break;
			}
		}
		
		if (alphaMsg) {
			messageType = "3";
		} else {
			messageType = "2";
		}
		
		buffer.append(messageType);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(numberOfBitsInTransparentDataMessage);
		buffer.append(FIELD_SEPARATOR);




		if (alphaMsg) {
			buffer.append(EncodingUtil.convert2IA5(messageData));
		} else {
			buffer.append(messageData);
		}


		buffer.append(FIELD_SEPARATOR);
		buffer.append(moreMessagesToSend);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(priorityRequest);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(dataCodingScheme);
		buffer.append(FIELD_SEPARATOR);

		//messageClass="0";


		buffer.append(messageClass);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(replyPath);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(codePage);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(replyType);
		buffer.append(FIELD_SEPARATOR);

		if (alphaOriginator) {
			originatorTypeOfAddress = "5039";
		}

		buffer.append(originatorTypeOfAddress);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(homePLMNAddress);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(extraServices);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(reserved4);
		buffer.append(FIELD_SEPARATOR);
		buffer.append(reserved5);

		return buffer.toString();
	}


}
