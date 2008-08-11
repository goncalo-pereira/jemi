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

public class Message51 extends Message5x {

	public Message51(String addressCodeRecipient, String addressCodeOriginator, String messageType, String numberOfBitsInTransparentDataMessage, String messageData) {

		setAddressCodeRecipient(addressCodeRecipient);
		setAddressCodeOriginator(addressCodeOriginator);
		setMessageType(messageType);
		setNumberOfBitsInTransparentDataMessage(numberOfBitsInTransparentDataMessage);
		setMessageData(messageData);

	}

	public int getType() {
		return 51;
	}
}
