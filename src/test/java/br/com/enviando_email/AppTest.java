package br.com.enviando_email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Unit test for simple App.
 */
public class AppTest {



	@org.junit.Test
	public void testeEmail() throws Exception{
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		//mensagem em formato html
		
		stringBuilderTextoEmail.append("Olá, <br/><br/>");//quebra de linha 
		stringBuilderTextoEmail.append("Você está recebendo o acesso ao curso Java. <br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso clique no botão abaixo. <br/><br/>");
		
		stringBuilderTextoEmail.append("<b>Login:<b/> anderson@gmail.com <br/><br/>");
		stringBuilderTextoEmail.append("<b>Senha:<b/> ###$$2@ <br/><br/>");
		
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"http://www.simepar.br/\" style=\" color:#2525a7; padding: 14px 25px; text-alingn:center; text-decoration: none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border : 3px solid green; background-color:#99DA39;\"> Acessar o portal </a><br/><br/>");
		
		stringBuilderTextoEmail.append("<span style=\"font-size:8px\"> Ass.: Anderson Miguel Lenz </span>");
		
		//mensagem em formato html
		
		//com virgula dentro das aspas é possivel passar os atributos 
		ObjetoEnviaEmail enviaEmail = 
				new ObjetoEnviaEmail("andersonlenzjava@gmail.com",
										"Anderson Miguel Lenz", 
										"Testando e-mail com Java", 
										stringBuilderTextoEmail.toString());//envia esta string para o método imprimir 

		//enviaEmail.enviarEmail(true);//chamada do método da classe para realizar o envio do e-mail
									//com true para liberar o método html

		enviaEmail.enviarEmailAnexo(true);//chamada do método da classe para realizar o envio do e-mail
		//com true para liberar o método html e anexo em PDF

	}

}
