package controleur;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Paths;

public class TCPClient {

    private final static String serverIP = "127.0.0.1";
    private final static int serverPort = 4444;

    //Doit être remove
    public static void main( String[] args ) {
        System.out.println(TCPClient.uploadImage("C:\\Users\\GuillaumeBouchard\\Desktop\\a.jpg"));
    }

    public static String uploadImage(String chemin){
        // On va définir un protocole qui est le suivant :
        // - taille du nom du fichier sur 4 octets
        // - nom du fichier
        // - taille du fichier sur 8 octets
        // - données du fichier

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        byte[] buffer = new byte[4096];

        try{
            socket = new Socket(serverIP,serverPort);
            in = socket.getInputStream();
            out = socket.getOutputStream();

            byte[] filenameArray = Paths.get(chemin).getFileName().toString().getBytes();
            int filenameSize = filenameArray.length;

            out.write(toByteArray(filenameSize));
            out.write(filenameArray);
            File file = new File(chemin);
            out.write(toByteArray(file.length()));

            FileInputStream fileInputStream = new FileInputStream(chemin);

            int count;
            while ((count = fileInputStream.read(buffer)) > 0 && count <= file.length())
            {
                out.write(buffer, 0, count);
            }
            fileInputStream.close();

            String cheminServeur = new String(in.readAllBytes());

            out.close();
            in.close();
            socket.close();

            return cheminServeur;

        }
        catch (IOException e){
            e.printStackTrace();
        }

        return null;

    }

    private static byte[] toByteArray(int value) {
        return  ByteBuffer.allocate(4).putInt(value).array();
    }

    private static byte[] toByteArray(long value) {
        return  ByteBuffer.allocate(8).putLong(value).array();
    }

}
