package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		// create the threads
		Thread keyboard = new Thread(new KeyboardReader(socket));
        Thread screen = new Thread(new ScreenWriter(socketInputStream));

        keyboard.start();
        screen.start();

	}

	/**
     * A class for reading from stdin and sending the input to the server
     */
	class KeyboardReader implements Runnable {
	    private Socket socket;
	    private OutputStream socketOutputStream;

	    public KeyboardReader(Socket socket) throws IOException{
	        this.socket = socket;
	        socketOutputStream = socket.getOutputStream();
        }

	    public void run() {
	        try {
                int readByte;
                // read a byte from standard input
                while ((readByte = System.in.read()) != -1) {
                    // write that byte to the socketOutputStream
                    socketOutputStream.write(readByte);
                }
                // shutdown the output so that the server knows there's nothing else to receive
                socket.shutdownOutput();
            } catch (IOException e) {System.out.print(e);}

        }
    }

    /**
     * A class for reading from the server and writing the input to stdout
     */
    class ScreenWriter implements Runnable {
        private InputStream socketInputStream;

        public ScreenWriter(InputStream inputStream) {
            socketInputStream = inputStream;
        }

        public void run() {
            try {
                int readByte;
                //
                while ((readByte = socketInputStream.read()) != -1) {
                    System.out.write(readByte);
                }
                System.out.flush();

                // close the inputStream and the socket
                socketInputStream.close();
            } catch (IOException e) {System.out.print(e);}
        }
    }
}