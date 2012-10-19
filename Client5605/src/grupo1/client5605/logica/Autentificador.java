package grupo1.client5605.logica;

import grupo1.client5605.iu.InterfaceComUsuario;
import grupo1.client5605.iu.painel.*;
import grupo1.client5605.iu.painel.PainelLogado;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JOptionPane;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.RequestToken;

public class Autentificador {
	Twitter twitter;
	String codigo;
	AccessToken accessToken = null;
	RequestToken requestToken;
	InterfaceComUsuario iu;
	PainelLogado pane;

	public Autentificador() {
		twitter = new TwitterFactory().getInstance();
	}

	public void autentica() throws TwitterException, IOException,
			URISyntaxException {
		twitter.setOAuthConsumer("DMhlZgnz7sjinrbtjRH9VA",
				"8ay2d4RBH4lr8lOczA6VykWwVO6xxFpbXfvZ81l05IA");
		requestToken = twitter.getOAuthRequestToken();
		String url = requestToken.getAuthorizationURL();
		Painel abreBrowser = new Painel(url, iu);
		abreBrowser.validar(url);
	}

	public void setCodigo(String codigo) throws Exception{
		iu = new InterfaceComUsuario();
		this.codigo = codigo;
		try {
			if (codigo.length()>0) {
				accessToken = twitter.getOAuthAccessToken(requestToken, codigo);
				pane = new PainelLogado("", iu);
				iu.mostreAbertura();
			} else {
				accessToken = twitter.getOAuthAccessToken();
			}
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				JOptionPane.showMessageDialog(null,
						"Código invalido, tente novamente.");
			} else {
				te.printStackTrace();
			}
		}
	}

}
