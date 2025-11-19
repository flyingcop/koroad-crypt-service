<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.co.grutech.anyauth2.client.*" %>
<%@ page import="kr.or.koroad.dlv.crypt.aria.cipher.*"%>
<%@ page import="kr.or.koroad.dlv.util.*"%>
<%@ page import="java.util.*"%><%
String client_secret = "8117a5d75e9909eb7858b5638803d72c707fb744";
String data = "[";
for(int i=0;i<10;i++) {
	if (i != 0) 
		data += ",\n";
	data += "{\"f_license_no\" : \"1212345612\","+
			"\"f_resident_name\" : \"홍길동\","+
			"\"f_resident_date\" : \"123456\","+
			"\"f_seq_no\" : \"123456\","+
			"\"f_licn_con_code\" : \"11\","+
			"\"f_from_date\" : \"20170728\","+
			"\"f_to_date\" : \"20170728\"}";
}
data += "]";
ARIACipher256 ac = new ARIACipher256(Base64.encode(client_secret.getBytes()));
byte[] encBody = data.getBytes();
byte[] encedBody = ac.encrypt(encBody);
String encStr = Base64.encode(encedBody);
%><%=encStr%>
<%
byte[] decBody = encedBody;
byte[] decedBody = ac.decrypt(decBody);
String decStr = new String(decedBody,"UTF-8");
%><%=decStr%>
<%
decStr = "WVhLW3pD0sUJGn0ztTvFDSozOLNGYYH9zFdstfFC9xB43/qelMV4TA5DlPg1CGQtEwvjh/zrq6l92sdgzMVPdg==";
decBody = Base64.decode(decStr);
decedBody = ac.decrypt(decBody);
decStr = new String(decedBody,"UTF-8");
%><%=decStr%>