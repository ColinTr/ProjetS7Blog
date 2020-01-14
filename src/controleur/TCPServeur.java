package controleur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TCPServeur {

    private final static int serverPort = 4444;

    public static void main( String[] args ) {
        String rootDirectory = "C:\\Images\\";
        TCPServeur.receiveImage(rootDirectory);
    }

    public static void receiveImage(String rootDirectory){
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        byte[] buffer = new byte[4096];

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("En attente de connexion...");
            while (true){
                socket = serverSocket.accept();
                System.out.println("Connexion depuis "+socket.getInetAddress());

                in = socket.getInputStream();
                out = socket.getOutputStream();

                in.readNBytes(buffer,0,4);
                int filenameSize = fromByteArray(buffer);

                in.readNBytes(buffer,0,filenameSize);
                String filename = new String(buffer,0,filenameSize);

                in.readNBytes(buffer,0,8);
                long fileSize = longFromByteArray(buffer);

                String path = (rootDirectory+filename);

                FileOutputStream fileOutputStream = new FileOutputStream(path);
                int count = 0;
                int dataReceived = 0;
                do {
                    count = in.read(buffer);
                    dataReceived += count;
                    fileOutputStream.write(buffer, 0, count);
                } while(count != -1 && dataReceived < fileSize);
                fileOutputStream.close();

                out.write(("file:\\" + path).getBytes());

                in.close();
                out.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int fromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    private static long longFromByteArray(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getLong();
    }

}
