package com.robo.web.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.*;

import org.apache.commons.dbcp2.BasicDataSourceFactory;


public class  CustomDataSourceFactory extends BasicDataSourceFactory {

	public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {
		if( obj instanceof Reference ) {
			findDecryptAndReplace("username", (Reference)obj);
			findDecryptAndReplace("password", (Reference)obj);
			findDecryptAndReplace("url", (Reference)obj);
		}
		return super.getObjectInstance(obj, name, nameCtx, environment);
	}
	private void findDecryptAndReplace(String refType, Reference ref) throws Exception {
		int idx = find(refType, ref);
		String decrypted = EncryptionUtil.decode_datasource(ref.get(idx).getContent().toString());
		ref.remove(idx);
		ref.add(idx, new StringRefAddr(refType, decrypted));
	}
	
	private int find(String addrType, Reference ref) throws Exception {
		Enumeration<RefAddr> enu = ref.getAll();
		for(int i = 0; enu.hasMoreElements(); i++) {
			RefAddr addr = (RefAddr)enu.nextElement();
			if(addr.getType().compareTo(addrType) == 0)
			return i;
		}
		throw new Exception("The \"" + addrType + "\" name/value pair was not found" + " in the Reference object.  The reference Object is" + " " + ref.toString());
	}
}

