package es.uma.khaos.docking_service.service;

import java.io.IOException;
import java.io.PrintStream;

import es.uma.khaos.docking_service.properties.Constants;
import sun.net.smtp.SmtpClient;

public final class MailService {
	
	private static MailService instance;
	
	public MailService() {}

	public void sendFinishedTaskMail(String email, int taskId, String token) throws IOException {

		SmtpClient client = new SmtpClient("sol10.lcc.uma.es");
		client.from("khaos@lcc.uma.es");
		client.to(email);

		PrintStream message = client.startMessage();
		message.println("From: "+"khaos@lcc.uma.es");
		message.println("To: "+email);
		message.println("Subject: "+"[JMETALDOCK] Task finished");
		message.println("Your task has finished.\n");
		message.println("Go to this URL to check the results:");
		message.println(Constants.WEB_URL + "rest/task/" + taskId + "?token=" + token + "\n");

		message.println("Kind regards.\nKhaos Research Group");
		message.println(Constants.WEB_URL);

		client.closeServer();
	}

	public void sendErrorTaskMail(String email, int taskId, String token) throws IOException {

		SmtpClient client = new SmtpClient("sol10.lcc.uma.es");
		client.from("khaos@lcc.uma.es");
		client.to(email);

		PrintStream message = client.startMessage();
		message.println("From: "+"khaos@lcc.uma.es");
		message.println("To: "+email);
		message.println("Subject: "+"[JMETALDOCK] Task error");
		message.println("We are sorry, your task encountered an error and could not finish.\n");
		message.println("Go to this URL to check the results:");
		message.println(Constants.WEB_URL + "rest/task/" + taskId + "?token=" + token + "\n");
		message.println("Please contact us if you need asistance.");

		message.println("Kind regards.\nKhaos Research Group");
		message.println(Constants.WEB_URL);

		client.closeServer();
	}
	
	public static synchronized MailService getInstance()  { //step 1
		if(instance == null) {
			instance = new MailService();
		}
		return instance;
	}
	
}
