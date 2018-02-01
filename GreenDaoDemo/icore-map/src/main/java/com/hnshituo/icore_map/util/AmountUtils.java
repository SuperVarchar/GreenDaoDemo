package com.hnshituo.icore_map.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Administrator on 2016/5/24.
 */
public class AmountUtils {
    //三位一逗
    public static String getString(String str){
        String amount = "";
        if (!TextUtils.isEmpty(str)) {
            DecimalFormat df = new DecimalFormat("###,###.##");
            if (!"0".equals(df.format(Double.parseDouble(str)))) {
                amount = df.format(Double.parseDouble(str));
            }else {
                amount ="0";
            }
        }
        return amount;
    }

    /**
     * 检查是否含有特殊字符
     * @param name
     * @return
     */
    public static boolean isFind(String name) {
        String regEx="[-_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\"]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(name);
        return m.find();
    }

    /**
     *全是 数字、字母、中文、标点符号 就返回true
     * @param name
     * @return
     */
//    public static boolean isAllChinese(String name) {
//        String regEx="[^0-9a-zA-Z\\u4e00-\\u9fa5.，,。？“”]+";
//        return Pattern.matches(name, regEx);
//    }

    /**
     *检测输入的文字中是否含有表情
     * @return
     */
         public static boolean isEmojiCharacter(char codePoint) {
                 return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20)
                         && codePoint <= 0xD7FF))|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                         ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
            }
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、177（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8或7，其他位置的可以为0-9
    */
        String num = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[3578]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        //matches():字符串是否在给定的正则表达式匹配
        return number.matches(num);
    }
    /**
     * 区号+座机号码+分机号码
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone){
        String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +
                "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)";
        return Pattern.matches(reg, fixedPhone);
    }
    //判断是否全中文
    public static boolean isChinese(String character){
        String reg="^[\\u4e00-\\u9fa5]+";
        return Pattern.matches(reg, character);
    }
    /**
     * 判断邮箱地址格式是否正确
     */
    public static boolean isEmail(String email){
        String matcher = "^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
        return email.matches(matcher);
    }

    /**
     * 科学计数法数字转成正常数字
     * @param s
     * @return
     */
    public static String getNormalNum(String s){
        if(TextUtils.isEmpty(s)){
           return "";
        }else{
            BigDecimal bd = new BigDecimal(s);
            return bd.toPlainString();
        }
    }

    /**
     * 给下载文件路径进行编码格式转换
     */
    public static String getEncodeDownloadUrl(String downloadUrl){
        return URLEncoder.encode(downloadUrl);
    }

    /**
     * 获取金额输入框的过滤器
     */
    public static InputFilter[] getMoneyFilter(){
        return new InputFilter[]{new MoneyInputFilter()};
    }
    /**
     * 获取金额输入框的过滤器
     */
    public static InputFilter[] getMoneyNineFilter(){
        return new InputFilter[]{new MoneyNineInputFilter()};
    }

    /**
     * 获取金额输入框动态加逗号
     */
    public static MoneyTextWatcher getMoneyWatcher(EditText editText){
        return new MoneyTextWatcher(editText);
    }

    /**
     * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
     *
     * 渠道标志为：
     * 1，andriod（a）
     *
     * 识别符来源标志：
     * 1， wifi mac地址（wifi）；
     * 2， IMEI（imei）；
     * 3， 序列号（sn）；
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {

        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();

        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length()%10 + Build.BRAND.length()%10 +
                Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
                Build.DISPLAY.length()%10 + Build.HOST.length()%10 +
                Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +
                Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +
                Build.TAGS.length()%10 + Build.TYPE.length()%10 + Build.USER.length()%10 ; //13 digits

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

/*
        WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
*/

        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // Local Bluetooth adapter
        String m_szBTMAC = m_BluetoothAdapter.getAddress();
        String m_szLongID = szImei + m_szDevIDShort + m_szAndroidID + m_szBTMAC;
        String m_szUniqueID = MD5Utils.getPwd(m_szLongID).toUpperCase();
        return m_szUniqueID;
    }

    /**
     * 验证身份证号是否符合规则
     * @param text 身份证号
     * @return
     */
    public static boolean personIdValidation(String text) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }
}
