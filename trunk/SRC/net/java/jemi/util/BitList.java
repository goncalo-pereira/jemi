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

public class BitList {

	private final int[] bitMasks = { 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80 };

	private boolean[] array = null;
	private int index = 0;

	private int bitsPerByte = 0;

	public BitList(byte[] initial, int bitsPerByte) {


		this.bitsPerByte = bitsPerByte; //TODO: Validar limites 1 até 8


		init((initial.length * bitsPerByte) + (initial.length % 8));

		index = initial.length % 8;

		//Inverter
		for (int i = initial.length - 1; i >= 0; i--) {
			addBits(initial[i]);
		}

	}

	private void init(int length) {
		array = new boolean[length];

		for (int i = 0; i < array.length; i++) {
			array[i] = false;
		}
	}

	private void addBits(byte aByte) {
		for (int i = bitsPerByte - 1; i >= 0; i--) {
			array[index++] = getBit(aByte, i);
		}
	}

	private boolean getBit(byte aByte, int pos) {
		int mask = bitMasks[pos];

		return (aByte & mask) == mask;
	}

	public byte[] getBytes() {
		int size = (index / bitsPerByte);

		if (size % bitsPerByte != 0) {
			size++;
		}

		byte[] ret = new byte[size];

		int aux = 0;
		int count = 0;
		int j = 0;
		int total = 0;

		for (int i = 0; i < index; i++) {
			int mask = (array[i] ? 0x00000001 : 0x00000000);

			aux = (aux << 1) | mask;
			aux |= mask;
			count++;

			if (count == 8 || i == (index - 1)) {
				// adicionar ao retorno
				ret[j++] = (byte) (aux & 0x000000ff);
				total++;

				count = 0;
				aux = 0;
			}
		}

		// Inverter novamente
		byte[] ret2 = new byte[total];
		j = total - 1;
		for (int i = 0; i < total; i++) {
			ret2[i] = ret[j--];
		}

		return ret2;
	}


	public String toString() {
		return toString(array);
	}

	public String toString(boolean[] array) {
		StringBuffer ret = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			ret.append(array[i] ? "1" : "0");

			if ((i + 1) % 8 == 0) {
				ret.append(" ");
			}
		}

		return ret.toString();
	}

	public boolean[] getArray() {
		return array;
	}

	public static void main(String[] args) {
		byte[] msg = { 0x41, 0x4C, 0x50, 0x48, 0x41, 0x00, 0x4E, 0x55, 0x4D }; /* ALPHA@NUM encoded IA5 */
		//byte[] msg = { 0x41, 0x41};

		//System.out.println(new String(msg));

		BitList bitList = new BitList(msg, 7);

		System.out.println(bitList.toString());

		byte res[] = bitList.getBytes();

		String[] hexCode = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "1A", "1B", "1C", "1D", "1E", "1F", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "2A", "2B", "2C", "2D", "2E", "2F", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "3A", "3B", "3C", "3D", "3E", "3F", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "4A", "4B", "4C", "4D",
				"4E", "4F", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "5A", "5B", "5C", "5D", "5E", "5F", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "6A", "6B", "6C", "6D", "6E", "6F", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "7A", "7B", "7C", "7D", "7E", "7F", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "8A", "8B", "8C", "8D", "8E", "8F", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99", "9A", "9B", "9C", "9D",
				"9E", "9F", "A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "AA", "AB", "AC", "AD", "AE", "AF", "B0", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "BA", "BB", "BC", "BD", "BE", "BF", "C0", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "CA", "CB", "CC", "CD", "CE", "CF", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "DA", "DB", "DC", "DD", "DE", "DF", "E0", "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "EA", "EB", "EC", "ED",
				"EE", "EF", "F0", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "FA", "FB", "FC", "FD", "FE", "FF" };

		System.out.print(">");
		for (int i = 0; i < res.length; i++) {

			String xpto = hexCode[res[i] & 0x000000ff];
			System.out.print(xpto);
		}

		System.out.println();

		System.out.println("=412614190438AB4D");

	}


}
