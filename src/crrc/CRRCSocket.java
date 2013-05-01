/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crrc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Jesper
 */
public class CRRCSocket extends Socket {

    private byte[] buf;

    private CRRCSocket() throws UnknownHostException, IOException {
        super("localhost", 9002);
        buf = new byte[86];
        // MNAV for CRRCSim need app-layer keepalive:
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        CRRCSocket.getSocket().send(new CRRCCmdPacket(CRRCCmdPacket.PING, null));
                    } catch (UnknownHostException ex) {
                    } catch (IOException ex) {
                        System.exit(0);
                    }
                    try {
                        Thread.sleep(2000); // 2Hz
                    } catch (InterruptedException ex) {
                        // Supposed to be interupted.
                    }
                }
            }
        }).start();
    }

    public CRRCDataPacket receive() throws IOException {
        InputStream is = super.getInputStream();
        do {
            int ret = is.read(buf);
            if (ret < 0) {
            }
            if (buf[2] != 'N') {
                System.out.println("Type: " + (char) buf[2] + " received.");
            }
        } while (buf[0] != 'U' || buf[1] != 'U' || buf[2] != 'N');
        return new CRRCDataPacket(buf);
    }

    public void send(CRRCCmdPacket p) throws IOException {
        OutputStream os = super.getOutputStream();
        byte[] data = p.getData();
        byte[] head = new byte[2 + data.length];
        head[0] = 'U';
        head[1] = 'U';
        System.arraycopy(data, 0, head, 2, data.length);
        os.write(head);
    }

    public static CRRCSocket getSocket() throws UnknownHostException, IOException {
        if (ref == null) {
            ref = new CRRCSocket();
        }
        return ref;
    }
    private static CRRCSocket ref;
}