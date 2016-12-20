package watchdog;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketObject {
	
	static int PORT;
	static String cameraA;
	static String cameraB;
	
	static byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
	
	// ソケットの準備
	static ServerSocket serverSocket;
	static Socket socket;
	private static OutputStream outputStream;
	private static InputStream inputStream;
	
	
	// 初期化処理1
	// ポート　カメラAのアドレス　カメラBのアドレス
	SocketObject(int PORT, String cameraA, String cameraB){
		this.PORT = PORT;
		this.cameraA = cameraA;
		this.cameraB = cameraB;
	}
	
	// 初期化処理２
	// ポート
	SocketObject(int PORT){
		this.PORT = PORT;
	}
	
	// ソケット通信スタート
	public static void start(){
		try {
			serverSocket = new ServerSocket(PORT);
			socket       = serverSocket.accept();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	//　画像の受信
	public static void SocketBmp(String outputFilepath){
		try{
	
			// ストリームの準備
			inputStream  = socket.getInputStream();
			outputStream = new FileOutputStream(outputFilepath);
			
			// ファイルをストリームで受信
			int fileLength;
			while ((fileLength = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, fileLength);
			}
		}catch(Exception e){
			e.printStackTrace();
	}
}
	
	// 画像の撮影ポイントのデータを受信
	public static int SocketPoint(){
		InputStream is;
		int point = 0;
		try {
			is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			
			// 画像撮影ポイントの取得
			point = dis.read();
			System.out.println(dis.read());
			
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return point;
	}
	
	// ソケット通信終了
	public static void close(){
		try {
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
