package site.book.socket.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 30.
 * @Author : 김태웅
 */
public class OnOffMemberSingleton {
	//Map<GID, Map<UID, "ON">>
	private static Map<String, Map<String, String>> online = new HashMap<>();
	
	public OnOffMemberSingleton() {}
	//최초 한번만 메모리를 할당하고 Static 자원으로 인스턴스를 생성하여 사용
	public static Map<String, Map<String, String>> getInstance () {
		return online;
	}
	//Map to Json으로 변경해주는 함수
	public static String returnConvertJson(String nname, String gid){
		Gson gson = new Gson();
		
		// 그룹 생성후 처음 들어온 경우,
    	if( !online.containsKey(gid) ) {
    		online.put(gid, new HashMap<String, String>());
    	}
    	
		online.get(gid).put(nname, "ON");
        String json = gson.toJson(online.get(gid));
        return json;
	}
}
