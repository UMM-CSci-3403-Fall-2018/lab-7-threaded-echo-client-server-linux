package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Thread t = new Thread(new Server(serverSocket));
			t.start();
		}
	}
	class Server implements Runnable {
		private Socket socket;
		public Server(ServerSocket serverSocket) {
			try {
				this.socket = serverSocket.accept();
			} catch (IOException e){System.out.println(e);}
		}

		public void run() {
			try {
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				int b;
				while ((b = inputStream.read()) != -1) {
					outputStream.write(b);
				}
				socket.shutdownOutput();
			}
			catch (IOException e){System.out.println(e);}
			finally {
				// always close the socket
				try {
					socket.close();
				} catch (IOException e){System.out.println(e);}

			}

		}
	}
}