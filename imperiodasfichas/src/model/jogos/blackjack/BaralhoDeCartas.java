package model.jogos.blackjack;

public enum BaralhoDeCartas {
    AS(11, "A"),
    DOIS(2, "2"),
    TRES(3, "3"),
    QUATRO(4, "4"),
    CINCO(5, "5"),
    SEIS(6, "6"),
    SETE(7, "7"),
    OITO(8, "8"),
    NOVE(9, "9"),
    DEZ(10, "10"),
    VALETE(10, "J"),
    DAMA(10, "Q"),
    REI(10, "K");

    private final int valor;
    private final String nome;

    BaralhoDeCartas(int valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public int getValor() {
        return valor;
    }

    public String getNome() {
        return nome;
    }

    public static BaralhoDeCartas sorteiaCarta() {
        BaralhoDeCartas[] cartas = values(); // Para pegar todas as cartas do baralho
        return cartas[(int)(Math.random() * cartas.length)]; // Sorteia uma carta
    }

    public String[] getDesenho() {
        String valor = this.nome;
        String espaco = valor.length() == 2 ? "" : " ";

        return new String[] {
                "+-----+",
                "|" + valor + espaco + "   |",
                "|     |",
                "|   " + espaco + valor + "|",
                "+-----+"
        };
    }



}
