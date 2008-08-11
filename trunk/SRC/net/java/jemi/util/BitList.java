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

}
