package com.trdsimul.gamemgmt.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

	public static final StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
	public static final String key = UUID.randomUUID().toString().toUpperCase();

	public SessionService() throws ParseException {
		
		jasypt.setPassword(key);

	}

	public Map<Integer, Boolean> isSessionValid(String encryptedMessage)
			throws ParseException, IOException, ClassNotFoundException {

		byte[] backToBytes = Base64.decodeBase64(encryptedMessage);
		ByteArrayInputStream bis = new ByteArrayInputStream(backToBytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			String decodedMesg = (String) in.readObject();
			Map<Integer, Boolean> gameDetails = new HashMap<Integer, Boolean>();
			boolean ret = true;
			String decryptedmessage = jasypt.decrypt(decodedMesg);
			String[] arr = decryptedmessage.split("\\s");
			SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date now = new Date();
			Date dtStartTime = df.parse(df.format(now.getTime()));
			Date dtEndTime = df.parse(arr[2]);
			if (dtStartTime.compareTo(dtEndTime) >= 0) {
				ret = false;
				gameDetails.put(Integer.valueOf(arr[0]), ret);
				return gameDetails;
			}
			gameDetails.put(Integer.valueOf(arr[0]), ret);
			return gameDetails;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
	}

	public String getNewSessionToken(Integer gameId, Integer interval) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date dtStartTime = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dtStartTime);
		cal.add(Calendar.MINUTE, interval);
		String authentication = gameId + " " + df.format(dtStartTime.getTime()) + " " + df.format(cal.getTime());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(jasypt.encrypt(authentication));
		  out.flush();
		  byte[] yourBytes = bos.toByteArray();
		  String base64StringHeader = Base64.encodeBase64String(yourBytes);
		  return base64StringHeader;
		} finally {
		  try {
		    bos.close();
		  } catch (IOException ex) {
		    // ignore close exception
		  }
		}	
	}

}
