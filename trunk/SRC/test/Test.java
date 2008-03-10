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
package test;

import net.java.jemi.SmsSender;
import net.java.jemi.api.EmiMessage;
import net.java.jemi.error.JemiException;

public class Test {

	public static void main(String[] args) {
		SmsSender smsSender = new SmsSender("127.0.0.1", 5003); //SMSC

		try {
			smsSender.send(new EmiMessage("testSender", "123456789", "Test message", false));
		} catch (JemiException e) {
			e.printStackTrace();
		}
	}

}
