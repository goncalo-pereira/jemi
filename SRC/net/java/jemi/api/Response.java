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

public class Response {

	private final char STX = 0x02;
	private final char ETX = 0x03;

	private int id = 0;
	private int type = 0;
	private int errorCode = 0;
	private String errorMsg = null;

	public Response(byte[] theResponse) throws JemiException {
		if (theResponse == null || theResponse.length < 21) { //sizeof  stx01/00041/R/01/A//E1etx
			throw new JemiException("Invalid response: response isn't long enough.");
		}

		if (theResponse[0] != STX || theResponse[theResponse.length - 1] != ETX) {
			throw new JemiException("Invalid response: start or end bytes are invalid.");
		}

		String response = new String(theResponse);
		response = response.substring(1, response.length() - 1); // Remove STX and ETX

		String respContent = response.substring(0, response.length() - 2);
		String respChecksum = response.substring(response.length() - 2);

		if (!respChecksum.equals(EncodingUtil.getChecksum(respContent.toCharArray()))) {
			throw new JemiException("Invalid response: invalid checksum " + respChecksum + ".");
		}

		try {
			id = Integer.parseInt(respContent.substring(0, 2));

			int size = Integer.parseInt(respContent.substring(3, 8));

			if (size != response.length()) {
				throw new JemiException("Invalid response: message size does not match declared size.");
			}

			char operation = respContent.charAt(9);
			if (operation != 'R') {
				throw new JemiException("Invalid response: this is not a response - operation " + operation);
			}

			type = Integer.parseInt(respContent.substring(11, 13));

			char result = respContent.charAt(14);

			switch (result) {
				case 'A':
					errorCode = 0;
					break;
				case 'N':
					errorCode = Integer.parseInt(respContent.substring(16, 18));
					errorMsg = respContent.substring(19, respContent.length() - 1);
					break;
				default:
					throw new JemiException("Invalid response: invalid answer " + result);
			}

		} catch (JemiException e) {
			throw e;
		} catch (Exception e) {
			throw new JemiException("Invalid response: invalid reponse format.", e);
		}

		// stx01/00041/R/01/A/969999999:270208171547/E1etx
		// stx01/00069/R/51/N/03/ This operation is restricted to Large Accounts/7Eetx
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
