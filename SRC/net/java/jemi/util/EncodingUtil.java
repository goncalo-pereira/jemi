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
package net.java.jemi.util;

public class EncodingUtil {

	private static final String[] hexCodeStr = { 
		"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F",
		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F",
		"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
		"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
		"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
		"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F",
		"60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F",
		"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F",
		"80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F",
		"90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D", "9E", "9F",
		"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF",
		"B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF",
		"C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF",
		"D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF",
		"E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED", "EE", "EF",
		"F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

	private static final String[] IA5EncodedChars = {
		"3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "0A", "3F", "3F", "0D", "3F", "3F",
		"3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "1B", "3F", "3F", "3F", "3F",
		"20", "21", "22", "23", "02", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F",
		"30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F",
		"00", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D", "4E", "4F",
		"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "1B3C", "1B2F", "1B3E", "1B14", "11",
		"27", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F",
		"70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "1B28", "1B40", "1B29", "1B3D", "3F",
		"3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F",
		"3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F",
		"20", "40", "3F", "01", "24", "03", "1B40", "5F", "3F", "3F", "3F", "3F", "3F", "2D", "3F", "3F",
		"3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "3F", "60",
		"41", "41", "41", "41", "5B", "0E", "1C", "09", "45", "1F", "45", "45", "49", "49", "49", "49",
		"3F", "5D", "4F", "4F", "4F", "4F", "5C", "3F", "3F", "55", "55", "55", "5E", "59", "3F", "1E",
		"7F", "61", "61", "61", "7B", "0F", "1D", "09", "04", "05", "85", "85", "07", "69", "69", "69",
		"6F", "7D", "08", "6F", "6F", "6F", "7C", "25", "3F", "06", "75", "75", "7E", "79", "3F", "79" };

	public static String getHexCodeStr(int index) {
		return hexCodeStr[index];
	}

	public static String getHexCodeStr(byte[] bytes) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			buffer.append(hexCodeStr[getUnsignedValue(bytes[i])]);
		}

		return buffer.toString();
	}

	private static int getUnsignedValue(byte b) {
		return b & 0x000000ff;
	}

	public static String convert2IA5(String str) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			buffer.append(IA5EncodedChars[str.charAt(i)]);
		}

		return buffer.toString();
	}

	public static String getChecksum(char[] content) {
		int sum = 0;

		for (int i = 0; i < content.length; i++) {
			sum += content[i];
		}

		return hexCodeStr[sum & 0x000000ff];
	}

}
