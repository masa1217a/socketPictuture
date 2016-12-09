package watchdog;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	final static int PORT = 10007;	// 待受ポート番号
	

	public static void main(String[] args) {
		SetDate date = new SetDate();
		String picName = date.str + ".bmp";
		byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
		while(true){
			// Server Start
			System.out.println("Server Start...");
			try {
				String outputFilepath = "/home/user/CamPic/" + picName;       // 受信したファイルの保存先
				// ソケットの準備
				ServerSocket serverSocket = new ServerSocket(PORT);
				Socket       socket       = serverSocket.accept();
	
				// ストリームの準備
				InputStream  inputStream  = socket.getInputStream();
				OutputStream outputStream = new FileOutputStream(outputFilepath);
				
				// ファイルをストリームで受信
				int fileLength;
				while ((fileLength = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, fileLength);
				}
				System.out.println("画像を受信");
				// 終了処理
				outputStream.flush();
				outputStream.close();
				inputStream.close();
				socket.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
