package es.uma.khaos.docking_service.service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import sun.net.smtp.SmtpClient;

public final class MailService {
	
	private static MailService instance;
	
	public MailService() {}
	
	public void sendResetPasswordMail(String email, String name, String surname, String nonce) throws IOException {
		
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dione.properties"));
		//String websiteName = props.getProperty("website.name");
		String url = props.getProperty("website.url");
		
		SmtpClient client = new SmtpClient("sol10.lcc.uma.es");
		client.from("khaos@lcc.uma.es");
		client.to(email);
		
		PrintStream message = client.startMessage();
		message.println("From: "+"khaos@lcc.uma.es");
		message.println("To: "+email);
		message.println("Subject: "+"[DIONE] Reset password request");
		message.println("Dear "+name+" "+ surname+":\n");
		message.println("You have requested a password change. Visit the following url to reset your password:");
		message.println(url + "/change_password.jsp?id=" + nonce + "\n");
		
		message.println("Kind regards. Khaos Group");
		message.println(url);
		
		client.closeServer();
	}
	
	public void sendAcceptanceMail(String email, String name, String surname) throws IOException {
		
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dione.properties"));
		String websiteName = props.getProperty("website.name");
		String url = props.getProperty("website.url");
		
		SmtpClient client = new SmtpClient("sol10.lcc.uma.es");
		client.from("khaos@lcc.uma.es");
		client.to(email);
		
		PrintStream message = client.startMessage();
		message.println("From: "+"khaos@lcc.uma.es");
		message.println("To: "+email);
		message.println("Subject: "+"[DIONE] Log in data for the " + websiteName + " website");
		message.println("Dear "+name+" "+ surname+":\n");
		message.println("Your request to access the " + websiteName + " Website has been accepted. Use your email and password to log in.\n");
		message.println("Kind regards. Khaos Group");
		message.println(url);
		
		client.closeServer();
	}
	
	public void sendValidationMail(String email, String name, String surname, String emailFrom, String affiliation, String interest) throws IOException {
		
		Properties props = new Properties();
		props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dione.properties"));
		String websiteName = props.getProperty("website.name");
		String url = props.getProperty("website.url");
		
		SmtpClient client = new SmtpClient("sol10.lcc.uma.es");
		client.from("khaos@lcc.uma.es");
		client.to(email);
		
		PrintStream message = client.startMessage();
		message.println("From: "+"khaos@lcc.uma.es");
		message.println("To: "+email);
		message.println("Subject: "+"[DIONE] Solicitud de acceso a la web de " + websiteName);
		message.println("Se ha recibido una solicitud de registro a la web de " + websiteName + "\n");
		message.println("La solicitud ha sido:");
		message.println("----------------------------------------------");
		message.println("Nombre: "+name);
		message.println("Apellidos: "+surname);
		message.println("Email: "+emailFrom);
		message.println("Affiliation: "+affiliation);
		message.println("Interest: "+interest);
		message.println("----------------------------------------------\n");
		message.println("Para aceptarle y enviarle un email de confirmaci�n, visite la siguiente URL y entre con sus datos de usuario:");
		message.println(url + "/action/validateAction.jsp?email=" + emailFrom);
		
		client.closeServer();
	}
	
	public static synchronized MailService getInstance()  { //step 1
		if(instance == null) {
			instance = new MailService();
		}
		return instance;
	}
	
}
