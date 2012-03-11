/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmnavpilot;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 *
 * @author Jesper
 */
public class MNAV {

    private DatagramSocket sock;
    private InetSocketAddress mnav_addr;
    private static byte[] PK_PING = {'U', 'U', 'P', 'K'};

    public MNAV() throws Exception {
        this("127.0.0.1", 9002);
    }

    public MNAV(String ip, int port) throws Exception {
        sock = new DatagramSocket();
        mnav_addr = new InetSocketAddress(ip, port);
        ping();
    }

    @Override
    public String toString() {
        return "MNAV{" + "ip=" + mnav_addr.getHostName() + ", port=" + mnav_addr.getPort() + '}';
    }

    public void ping() {
        try {
            sock.send(new DatagramPacket(PK_PING, PK_PING.length, mnav_addr));
        } catch (IOException ex) { /*
             * we dont care much if ping fail
             */ }
    }

    public void sendData(byte[] data) {
        try {
            data = encode(data);
//            System.out.print("pkg: [");
//            for (byte b : data) {
//                System.out.print(b + ",");
//            }
//            System.out.println("]");
            sock.send(new DatagramPacket(data, data.length, mnav_addr));
        } catch (IOException ex) {
        }
    }

    public MNAVState update() {
        byte[] data = new byte[86];
        try {
            ping();
            // Assume Scaled mode data package
            sock.receive(new DatagramPacket(data, 86));
            MNAVState state = new MNAVState();
            state.setxAccel(2 * floatFromBytes(data, 3));
            state.setyAccel(2 * floatFromBytes(data, 5));
            state.setzAccel(2 * floatFromBytes(data, 7));
            state.setxRate(200 * floatFromBytes(data, 9));
            state.setyRate(200 * floatFromBytes(data, 11));
            state.setzRate(200 * floatFromBytes(data, 13));
            state.setxMag(floatFromBytes(data, 15));
            state.setyMag(floatFromBytes(data, 17));
            state.setzMag(floatFromBytes(data, 19));
            state.setxTemp(200 * floatFromBytes(data, 21));
            state.setyTemp(200 * floatFromBytes(data, 23));
            state.setzTemp(200 * floatFromBytes(data, 25));
            state.setAbsPress(10000 * floatFromBytes(data, 27));
            state.setPitotPress(80 * floatFromBytes(data, 27));
            for (int i = 0; i < 7; i++) {
                state.setPpm(i, (1 + floatFromBytes(data, 68 + i * 2)) / 2);
            }

            // CRC Calc validation
//            System.out.println("CRC: " + data[84] + " , " + data[85]);
//            byte[] crc = checksum(data, 2, 82);
//            System.out.println("CRC: " + crc[0] + " , " + crc[1]);

            return state;
        } catch (IOException ex) {
            System.out.println("Failed to update state.");
            return null;
        }
    }

    public byte[] encode(byte[] data) {
        byte[] head = {'U', 'U'};
        return concat(head, concat(data, checksum(data, 0, data.length)));
    }

    public float floatFromBytes(byte[] arr, int ofs) {
        int lsb = (0x000000FF & ((int) arr[ofs + 1]));
        int msb = arr[ofs];
        float val = ((float) msb * 256 + lsb) / 32768;
        return val;
    }

    public byte[] bytesFromFloat(float val) {
        byte[] data = new byte[2];
        data[0] = (byte) (32768 * val / 256);
        data[1] = (byte) (32768 * val - data[0]);
        return data;
    }
    
    public byte[] bytesFromPPM(float val) {
        byte[] data = new byte[2];
        int ival = (int)(65535 * val);
        data[0] = (byte) (0x000000FF &(ival >> 8));
        data[1] = (byte) (0x000000FF &(ival));
        return data;
    }
    
    public byte[] checksum(byte[] data, int ofs, int len) {
        byte[] crc = new byte[2];
        int crci = 0;
        for (int i = ofs; i < ofs + len; i++) {
            int val = (0x000000FF & ((int) data[i]));
            crci += val;
        }
        crc[0] = (byte) (0x000000FF & (crci >> 8));
        crc[1] = (byte) (0x000000FF & (crci));
        return crc;
    }

    public byte[] concat(byte[] arr1, byte[] arr2) {
        byte[] result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }
}
