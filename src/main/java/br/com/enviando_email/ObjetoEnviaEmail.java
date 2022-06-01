package br.com.enviando_email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "andersonlenzjava@gmail.com";
	private String senha = "%5Senhanova";
	
	//teste
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";

	// chamar a classe para criar um construtor com atributos ja dentro e retorna
	// eles

	public ObjetoEnviaEmail(String listaDestinatário, String nomeRementente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatário;
		this.nomeRemetente = nomeRementente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}

	public void enviarEmail(boolean envioHtml) throws Exception {

		/* SMPT de cada email protocolo de envio */
		// solicita a autenticação e retorna a conexão em forma de objeto que autoriza o
		// envio do email

		Properties properties = new Properties();// objeto abstrato manipulação

		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");// autenticação
		properties.put("mail.smtp.starttls", "true");// autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");// servidor gmail google
		properties.put("mail.smtp.port", "465");// porta do servidor gogle
		properties.put("mail.smtp.socketFactory.port", "465");// especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// classe socket de conexão ao SMTP

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		// este objeto aceita ser uma lista [], no caso esta sendo passado direto
		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));// quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUser);// email de destino
		message.setSubject(assuntoEmail);// assunto do e-mail

		if (envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			message.setText(textoEmail);// texto do email
		}

		javax.mail.Transport.send(message);

		System.out.println(senha);

	}

	public void enviarEmailAnexo(boolean envioHtml) throws Exception {

		/* SMPT de cada email protocolo de envio */
		// solicita a autenticação e retorna a conexão em forma de objeto que autoriza o
		// envio do email

		Properties properties = new Properties();// objeto abstrato manipulação

		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");// autenticação
		properties.put("mail.smtp.starttls", "true");// autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com");// servidor gmail google
		properties.put("mail.smtp.port", "465");// porta do servidor gogle
		properties.put("mail.smtp.socketFactory.port", "465");// especifica a porta a ser conectada pelo socket
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		// classe socket de conexão ao SMTP

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);
			}
		});

		// este objeto aceita ser uma lista [], no caso esta sendo passado direto
		Address[] toUser = InternetAddress.parse(listaDestinatarios);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente));// quem está enviando
		message.setRecipients(Message.RecipientType.TO, toUser);// email de destino
		message.setSubject(assuntoEmail);// assunto do e-mail

		// Parte 1 do e-mail que é o texto e a descrição do e-mail
		MimeBodyPart corpoEmail = new MimeBodyPart();

		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		} else {
			corpoEmail.setText(textoEmail);// texto do email
		}

		// Parte de adicionar uma lista maior de envio, cria a lista e adiciona os arquivos 
		ArrayList<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePdf());//certificado
		arquivos.add(simuladorDePdf());//poderia ser uma imagem
		arquivos.add(simuladorDePdf());//poderia er um documento PDF
		arquivos.add(simuladorDePdf());//poderia ser uma nota fiscal

		//cria o corpo do e-mail
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);//adiciona a parte do HTML

		//adiciona os vários anexos
		int index = 1;
		for (FileInputStream fileInputStream : arquivos) {

			// Parte 2 do e-mail que são os anexos em PDF
			MimeBodyPart anexoEmail = new MimeBodyPart();

			// Onde é passado o simulador de PDF você passa o seu arquivo no seu banco de
			// dados
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail"+ index +".pdf");

			multipart.addBodyPart(anexoEmail);
			
			index++;
		}

		message.setContent(multipart);

		javax.mail.Transport.send(message);

		System.out.println(senha);

	}

	// Esse método simula a elaboração de um PDF ou qualquer arquivo que possa ser
	// enviado por anexo no email
	// você pode pegar o arquivo no seu banco de dados ex: base64, byte[], Stream de
	// Arquivos, pasta.
	// Retorna um PDF em Branco com o texto do Paragrafo de exemplo

	private FileInputStream simuladorDePdf() throws Exception {

		Document document = new Document();// document da pasta itextpdf
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Conteudo do meu PDF com Java Mail, esse texto é do PDF"));
		document.close();

		return new FileInputStream(file);

	}

}
