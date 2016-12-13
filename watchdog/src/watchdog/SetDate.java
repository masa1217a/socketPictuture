package watchdog;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SetDate {
		
		//カレンダーを生成
		//Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'_'MM'_'dd'_'HH'_'mm'_'ss");
		String str = sdf.format(new Date());
		
		//（今日の日付を取得）
		Date today = Calendar.getInstance().getTime();
		//（Dateから文字列のyyyyMMddに整形）
		String string_Today = new SimpleDateFormat("yyyyMMdd").format(today);
		//（文字列（yyyyMMdd）を８桁の数字に変換。）
		Integer int_Today = Integer.parseInt(string_Today);
		
		//（今日の日付を取得）
		Date time = Calendar.getInstance().getTime();
		//（Dateから文字列のyyyyMMddに整形）
		String string_Time = new SimpleDateFormat("HHmmss").format(time);
		//（文字列（yyyyMMdd）を８桁の数字に変換。）
		Integer int_Time = Integer.parseInt(string_Today);
}
