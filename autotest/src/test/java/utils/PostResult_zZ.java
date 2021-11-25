package utils;

import model.bicyclingbean;

public class PostResult_zZ {
	public static String httpPostResult(String URL, bicyclingbean bcycb, boolean b) {

		OkHttp_zZZyncUtils oku = OkHttp_zZZyncUtils.builder();
		String result = null;
		String str_head = bcycb.getRequestHeader();
//		System.out.println(str_head);
		String str_param = bcycb.getRequestParameter();
//		System.out.println(str_param);

		// 加入param或head，true时"postJsonBdy":{"":"","":"","":""}。false时？
		if (b == true) {
			oku = add_post_postJsonBdy(oku, str_head, str_param);
		} else if (b == false) {
//			add_post_postJsonFms();
			// writing..........................................
			// .....................................................
		}
		// 封装请求发送
		// 1.如果传入的参数是 true，则发送的是body为json 的POST请求
		// 2.如果传入的参数是false，则发送的是forms的post请求
		result = oku.url(URL).post(b).sync();
		return result;
	}

	private static OkHttp_zZZyncUtils add_post_postJsonBdy(OkHttp_zZZyncUtils oku, String str_head, String str_param) {
		// 请求头和请求参数为空时
		if (str_head == null && str_param == null) {
			return oku;
			// 请求头不空，请求参数为空时
		} else if (str_head != null && str_param == null) {
			String[] head = str_head.split("\r\n");// 检查点中请求头是以回车换行显示
			for (int i = 0; i < head.length; i++) {
				String[] head2 = head[i].split(": ", -1);
				oku.addHeader(head2[0], head2[1]);
			}
			// 请求头为空，请求参数不为空时
		} else if (str_head == null && str_param != null) {
			String param = str_param.substring(11);// {"A":"B","A1":"B1"}
			param = param.substring(1, param.length() - 1);
			String[] paramarr = param.split(",", -1);// ["A":"B"],["A1":"B1"]
			for (int i = 0; i < paramarr.length; i++) {
				String[] strrr = paramarr[i].split(":", -1);// i=0 [["A"],["B"]]//i=1 [["A1",["B1"]]
				
				strrr[0] = strrr[0].substring(1,strrr[0].length()-1);
				strrr[1] = strrr[1].substring(1,strrr[1].length()-1);
				oku.addParam(strrr[0], strrr[1]);
			}
			// 请求头，请求参数不为空时
		} else if (str_head != null && str_param != null) {
			String param = str_param.substring(11);// {"A":"B","A1":"B1"}
			param = param.substring(1, param.length() - 1);
			String[] paramarr = param.split(",", -1);// ["A":"B"],["A1":"B1"]
			for (int i = 0; i < paramarr.length; i++) {
				String[] strrr = paramarr[i].split(":", -1);// i=0 [["A"],["B"]]//i=1 [["A1",["B1"]]
				strrr[0] = strrr[0].substring(1,strrr[0].length()-1);
				strrr[1] = strrr[1].substring(1,strrr[1].length()-1);
				oku.addParam(strrr[0], strrr[1]);
			}
			String[] head = str_head.split("\r\n");// 检查点中请求头是以回车换行显示
			for (int i = 0; i < head.length; i++) {
				String[] head2 = head[i].split(": ", -1);
				oku.addHeader(head2[0], head2[1]);
			}
		}
		return oku;
	}
}
