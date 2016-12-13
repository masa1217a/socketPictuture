package watchdog;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	final static int PORT = 10007;	// 待受ポート番号
	
	static String cameraA = "172.16.107.3";
	static String cameraB = "172.16.107.2";
	

	public static void main(String[] args) {
		
		byte[] buffer         = new byte[512]; // ファイル受信時のバッファ
		while(true){
			// Server Start
			System.out.println("Server Start...");
			try {
				// ソケットの準備
				ServerSocket serverSocket = new ServerSocket(PORT);
				Socket       socket       = serverSocket.accept();
				
				// クライアントのアドレスを調べカメラ情報を付加する
				int camInf;
				String camName;
				String clientAdd = socket.getInetAddress().getHostAddress();
				if(clientAdd.equals(cameraA)){
					camInf = 0; // ０→カメラA
					camName = "A";
				}else if(clientAdd.equals(cameraB)){
					camInf = 1; // １→カメラB
					camName = "B";
				}else{		//　例外
					camInf = 3;
					camName = "E";
				}
				
				// 画像を受信した日時を画像名に設定
				SetDate date = new SetDate();
				String picName = camName +"_"+ date.str + ".bmp";
				String outputFilepath = "/home/user/CamPic/"+ picName;       // 受信したファイルの保存先

				// ストリームの準備
				InputStream  inputStream  = socket.getInputStream();
				OutputStream outputStream = new FileOutputStream(outputFilepath);
				
				System.out.println("Accept client, address:" + socket.getInetAddress().getHostAddress() + ",port:" + socket.getPort() + ".");
			    
				// ファイルをストリームで受信
				int fileLength;
				while ((fileLength = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, fileLength);
				}
				
				System.out.println("画像を受信");
				// 終了処理
				
				DBAccess dbAccess = new DBAccess(camInf, date.int_Today, date.int_Time, picName, 0);
				dbAccess.DBnextID();
				dbAccess.DBinsert();
				
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
