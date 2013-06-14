/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.math.BigInteger;

/**
 *
 * @author Jesper
 */
public class CRRCCmdPacket {

    public static final byte PING = 0;
    public static final byte SET_SERVO = 1;
    public static final byte SET_PACKET_TYPE = 2;
    private byte[] payload;

    public CRRCCmdPacket(byte type, double[] data) {
        switch (type) {
            case CRRCCmdPacket.PING:
                payload = ping();
                break;
            case CRRCCmdPacket.SET_SERVO:
                payload = setServo(data);
                break;
            default:
                throw new AssertionError();
        }
    }

    public CRRCCmdPacket(byte type) {
        switch (type) {
            case CRRCCmdPacket.PING:
                payload = ping();
                break;
            default:
                throw new AssertionError();
        }
    }

    public CRRCCmdPacket(byte type, char data) {
        switch (type) {
            case CRRCCmdPacket.PING:
                payload = ping();
                break;
            case CRRCCmdPacket.SET_PACKET_TYPE:
                payload = setPacketType(data);
                break;
            default:
                throw new AssertionError();
        }
    }

    private byte[] setServo(double[] data) {
        byte[] cmd = {
            'S', 'S',
            0, 0,
            0, 0,
            0, 0,
            0, 0,
            0, 0,
            0, 0,
            0, 0,
            0, 0,
            0, 0
        };
        int idx = 2;
        char val;
        for (double d : data) {
            d = Math.min(1, Math.max(0, d));
            val = (char) (d * 65535);
            cmd[idx++] = (byte) (val >>> 8);
            cmd[idx++] = (byte) (val & 0xFF);
        }
        return addChecksum(cmd);
    }

    private byte[] ping() {
        byte[] cmd = {'P', 'K'};
        return cmd;
    }

    private byte[] addChecksum(byte[] data) {
        byte[] result = new byte[data.length + 2];
        System.arraycopy(data, 0, result, 0, data.length);
        short sum = 0;
        for (byte b : data) {
            sum += 0xFF & b;
        }
        result[result.length - 2] = (byte) (sum >>> 8);
        result[result.length - 1] = (byte) (sum & 0xFF);
        return result;
    }

    byte[] getData() {
        return payload;
    }

    @Override
    public String toString() {
        return "CRRCCmdPacket{" + "payload type=" + (char) payload[0] + " " + (char) payload[1] + '}';
    }

    private byte[] setPacketType(char data) {
        byte[] cmd = {'W', 'F', 0x01, 0x00, 0x03, 0x00, (byte) data};
        BigInteger bigInt = new BigInteger(cmd);
        String hexString = bigInt.toString(16); // 16 is the radix
        System.out.println("hex: " + hexString);
        return cmd;
    }
}
