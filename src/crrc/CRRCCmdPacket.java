/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

/**
 *
 * @author Jesper
 */
public class CRRCCmdPacket {

    public static final byte PING = 0;
    public static final byte SET_SERVO = 1;
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
}
