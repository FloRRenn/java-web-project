package cinema.ticket.booking.utils;

import org.json.JSONObject;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cinema.ticket.booking.model.Payment;

public class VNPay extends HttpServlet {
    
    private static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private static String vnp_Version = "2.1.0";
    private static String vnp_TmnCode = "064H1LVP";
    private static String vnp_HashSecret = "AEQQSYJOSEUTZRKRSQSLXXVLIASCSNXM";  
    // private static String vnp_Returnurl = "https://coderlod.xyz/order-complete";
    private static String vnp_Returnurl = "http://localhost/order-complete";
    private static String  vnp_apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    
//	@Autowired
//	private static PaymentRepository paymentREPO;
	
    public static String createPay(Payment payment, String bankCode, String ip_addr) throws ServletException, IOException {
        String vnp_Command = "pay";

        long amount = Math.round(payment.getAmount()*100);
        String vnp_IpAddr = ip_addr;
        String vnp_TxnRef = payment.getId();
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != "VNPAY") {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan ve xem phim Pengu TPHCM - Don hang: " + vnp_TxnRef);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance();
        cld.setTime(payment.getCreateAt());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }

    /*
        Return 0 -> Payment success
        Return 1 -> Payment is in processing or error while request
        Return 2 -> Payment is canceled or not completed
    */ 
    public static Integer verifyPay(Payment payment) throws ServletException, IOException {
        String vnp_RequestId = payment.getId() + getRandomID(10000,99999);
        String vnp_Command = "querydr";
        String vnp_TxnRef = payment.getId();
        String vnp_OrderInfo = "Kiem tra ket qua GD don hang " + vnp_TxnRef;
        
        
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        
        cld.setTime(payment.getCreateAt());
        String vnp_TransDate = formatter.format(cld.getTime());

        
        String vnp_IpAddr = "0.0.0.0";
        	
        try(final DatagramSocket socket = new DatagramSocket()){
      	  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      	  vnp_IpAddr = socket.getLocalAddress().getHostAddress();
        }
        
        JsonObject  vnp_Params = new JsonObject();
        
        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        //vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
        vnp_Params.addProperty("vnp_TransactionDate", vnp_TransDate);
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);
        
        String hash_Data = vnp_RequestId + "|" + vnp_Version + "|" + vnp_Command + "|" + vnp_TmnCode + "|" + vnp_TxnRef + "|" + vnp_TransDate + "|" + vnp_CreateDate + "|" + vnp_IpAddr + "|" + vnp_OrderInfo;
       
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hash_Data.toString());
        
        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);
        
        URL url = new URL(vnp_apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(vnp_Params.toString());
        wr.flush();
        wr.close();

        // int responseCode = con.getResponseCode();
        // System.out.println("nSending 'POST' request to URL : " + url);
        // System.out.println("Post Data : " + vnp_Params);
        // System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
        response.append(output);
        }
        in.close();
        System.out.println(response.toString());
        
        JSONObject json = new JSONObject(response.toString());
        System.out.println(json.toString());
        
        String res_ResponseCode = (String) json.get("vnp_ResponseCode");
        String res_TxnRef = (String) json.get("vnp_TxnRef");
        String res_Message = (String) json.get("vnp_Message");
        Double res_Amount = Double.valueOf((String) json.get("vnp_Amount"))/100;
        String res_TransactionType = (String) json.get("vnp_TransactionType");
        String res_TransactionStatus = (String) json.get("vnp_TransactionStatus");
        
        System.out.println(res_Message);

        if (!res_ResponseCode.equals("00")) // Response Code invaild
        	return 1;

        if (!res_TxnRef.equals(payment.getId()))  // Payment ID not equal
        	return 1;

        if (res_Amount != payment.getAmount()) // Amount payment not equal
        	return 2;

        if (!res_TransactionType.equals("01")) // Transaction Type invaild
        	return 2;

        if (res_TransactionStatus.equals("01")) // Transaction is pending
        	return 1;

        if (!res_TransactionStatus.equals("00")) // Transaction Status invaild
        	return 2;

        return 0;
    }
    
    


    private static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }


    private static String getRandomID(int min, int max) {
        return String.valueOf((Math.random() * (max - min)) + min);
    }
}
