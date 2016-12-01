package watchdog;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class SetDate {
		
		//カレンダーを生成
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'_'MM'_'dd'_'HH'_'mm'_'ss");
		
		String str = sdf.format(new Date());
}
