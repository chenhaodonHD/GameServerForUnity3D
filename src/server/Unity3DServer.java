package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by HD on 2017/9/26.
 */
public class Unity3DServer implements Runnable {
    public void run() {

        ServerSocket u3dServerSocket = null;

        while (true) {

            try {

                int port = 23333;
                u3dServerSocket = new ServerSocket(port);

                System.out.println("u3d服务已经启动,监听" + port + "端口");

                while (true) {
                    Socket socket = u3dServerSocket.accept();
                    System.out.println("客户端接入");
                    new RequestReceiver(socket).start();
                }

            } catch (IOException e) {
                System.err.println("服务器接入失败");
                if (u3dServerSocket != null) {
                    try {
                        u3dServerSocket.close();
                    } catch (IOException ioe) {
                    }
                    u3dServerSocket = null;
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
        }
    }

    class RequestReceiver extends Thread {

        private int messageLengthBytes = 1024;

        private Socket socket;

        private BufferedInputStream bis = null;


        public RequestReceiver(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                bis = new BufferedInputStream(socket.getInputStream());
                byte[] buf = new byte[messageLengthBytes];

                while (true) {

                    //new RequestHandler(socket, buf).start();
                    bis.read(buf);
                    System.out.println(new String(buf, "utf-8"));
                    socket.getOutputStream().write("我是服务端".getBytes());
                }

            } catch (IOException e) {
                System.err.println("读取报文出错");
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                    }
                    socket = null;
                }
            }

        }
    }

    class RequestHandler extends Thread {

    }
}
