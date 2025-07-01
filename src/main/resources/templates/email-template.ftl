<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
        .header { background-color: #ea9797; padding: 20px; text-align: center; }
        .header img { max-width: 200px; }
        .content { padding: 20px; background-color: #f8f9fa; }
        .footer { padding: 20px; text-align: center; font-size: 12px; color: #777; }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Império das Fichas</h1>
    </div>

    <div class="content">
        <h2>Olá ${nome},</h2>

        ${mensagem}
    </div>

    <div class="footer">
        <p>© ${.now?string('yyyy')} Império das Fichas - Todos os direitos reservados</p>
        <p>Este é um e-mail automático, por favor não responda diretamente a esta mensagem.</p>
        <p>
            <a href="${emailSuporte}">Suporte</a>
        </p>
    </div>
</div>
</body>
</html>