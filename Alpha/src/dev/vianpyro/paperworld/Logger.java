package dev.vianpyro.paperworld;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private static String date = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date());;
	private static String time = new SimpleDateFormat("hh:mm:ss").format(new Date());;
	private static File logFile, mainFolder, logsFolder;

	public static void initialisation() {
		System.out.println(date);
		System.out.println(System.getProperty("user.name"));
		createFolders();

		logFile = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/.paper_game/logs/" + date + ".log");

		if(!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}

	public static void log(String info) {
		System.out.println("Logs class called");

		try {
			FileWriter writer = new FileWriter(logFile);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write("[" + time + "] System info : " + info + "\n");
			bw.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createFolders() {
		mainFolder = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/.paper_game");
		if(!mainFolder.exists()) {
			mainFolder.mkdir();
		}
		
		logsFolder = new File("C:/Users/" + System.getProperty("user.name") + "/AppData/Roaming/.paper_game/logs");
		if(!logsFolder.exists()) {
			logsFolder.mkdir();
		}
	}
}