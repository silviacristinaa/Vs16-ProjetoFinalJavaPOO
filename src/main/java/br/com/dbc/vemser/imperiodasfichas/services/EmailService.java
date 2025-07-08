package br.com.dbc.vemser.imperiodasfichas.services;

import br.com.dbc.vemser.imperiodasfichas.entities.CarteiraEntity;
import br.com.dbc.vemser.imperiodasfichas.entities.JogadorEntity;
import br.com.dbc.vemser.imperiodasfichas.repositories.JogadorRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final Configuration fmConfiguration;
    private final JavaMailSender emailSender;
    private final JogadorRepository jogadorRepository;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendEmailCreateJogador(JogadorEntity jogador) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(jogador.getEmail());
            helper.setSubject("Cadastro realizado com sucesso!");

            String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String mensagem = String.format("    <p>Seu cadastro no <strong>Império das Fichas</strong> foi realizado com sucesso!</p>\n" +
                    "\n" +
                    "    <p>Agora você pode acessar nossa plataforma e desfrutar dos melhores jogos de cassino online.</p>\n" +
                    "\n" +
                    "        <p><strong>Dados da sua conta:</strong></p>\n" +
                    "        <ul>\n" +
                    "            <li><strong>Usuário:</strong> %s</li>\n" +
                    "            <li><strong>Data de cadastro:</strong> %s</li>\n" +
                    "        </ul>\n" +
                    "\n" +
                    "    <p>Caso você não tenha realizado este cadastro, por favor entre em contato imediatamente com" +
                    " nosso suporte.</p>", jogador.getNickname(), dataAtual);

            String html = getEmailTemplate(jogador.getNome(), from, mensagem);
            helper.setText(html, true);

            emailSender.send(mimeMessage);
            log.info("Email de criação de cadastro enviado com sucesso para: {}", jogador.getEmail());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailDeleteJogador(JogadorEntity jogador) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(jogador.getEmail());
            helper.setSubject("Atenção: seu cadastro foi removido!");

            String mensagem = String.format("<p>Seu cadastro no Império das Fichas foi removido conforme solicitado.</p>\n" +
                    "        \n" +
                    "        <p>Todos os seus dados pessoais foram permanentemente excluídos de nossos sistemas, em conformidade com a Lei Geral de Proteção de Dados (LGPD).</p>\n" +
                    "        \n" +
                    "        <p>Caso tenha sido uma decisão equivocada ou queira voltar a utilizar nossos serviços no futuro, você poderá criar um novo cadastro a qualquer momento.</p>\n" +
                    "        \n" +
                    "        <p>Agradecemos por ter feito parte da nossa comunidade e esperamos poder servi-lo novamente no futuro.</p>");

            String html = getEmailTemplate(jogador.getNome(), from, mensagem);
            helper.setText(html, true);

            emailSender.send(mimeMessage);
            log.info("Email de exclusão de cadastro enviado com sucesso para: {}", jogador.getEmail());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailDepositarCarteira(CarteiraEntity carteira, double valor) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            JogadorEntity jogador = carteira.getJogador();

            helper.setFrom(from);
            helper.setTo(jogador.getEmail());
            helper.setSubject("Depósito identificado com sucesso!");

            String mensagem = String.format("<div class=\"success-box\">\n" +
                    "            <p>Seu depósito foi identificado em nossa plataforma!</p>\n" +
                    "            <p>Valor creditado: <span class=\"amount\">R$ %.2f</span></p>\n" +
                    "            <p>Data do crédito: %s</p>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <p>O valor já está disponível em sua carteira virtual e pronto para ser utilizado em nossas mesas.</p>\n" +
                    "        \n" +
                    "        <p>Caso não reconheça esta transação ou tenha qualquer dúvida, por favor entre em " +
                    "contato imediatamente com nosso Suporte.</p>\n" +
                    "        \n" +
                    "        <p>Agradecemos sua confiança e bom jogo!</p>", valor, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            String html = getEmailTemplate(jogador.getNome(), from, mensagem);
            helper.setText(html, true);

            emailSender.send(mimeMessage);
            log.info("Email de notificação de depósito enviado com sucesso para: {}", jogador.getEmail());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailSacarCarteira(CarteiraEntity carteira, double valor) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            JogadorEntity jogador = carteira.getJogador();

            helper.setFrom(from);
            helper.setTo(jogador.getEmail());
            helper.setSubject("Notificação de saque realizado");

            String mensagem = String.format("    <div class=\"transaction-box\">\n" +
                    "    <p>Seu pedido de saque foi processado com sucesso!</p>\n" +
                    "    <p>Valor sacado: <span class=\"amount\">R$ %.2f</span></p>\n" +
                    "    <p>Data do saque: %s</p>\n" +
                    "\n" +
                    "    <p>Caso não tenha solicitado este saque ou encontre qualquer divergência, entre em contato imediatamente com\n" +
                    "    nosso suporte.</p>\n" +
                    "\n" +
                    "    <p>Agradecemos por utilizar nossos serviços!</p>\n" +
                    "    </div>", valor, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            String html = getEmailTemplate(jogador.getNome(), from, mensagem);
            helper.setText(html, true);

            emailSender.send(mimeMessage);
            log.info("Email de notificação de saque enviado com sucesso para: {}", jogador.getEmail());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private String getEmailTemplate(String nome, String emailSuporte, String mensagem)
            throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", nome);
        dados.put("emailSuporte", emailSuporte);
        dados.put("mensagem", mensagem);

        Template template = fmConfiguration.getTemplate("email-template.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
