public class NoB {
    int n;
    int[] chave;
    String[] dado;
    NoB[] filho;
    boolean folha;

    NoB(int t, boolean folha) {
        this.n = 0;
        this.folha = folha;
        this.chave = new int[2 * t - 1];
        this.dado = new String[2 * t - 1];
        this.filho = new NoB[2 * t];
    }

    void copiarChave(int i, NoB destino, int j) {
        destino.chave[j] = chave[i];
        destino.dado[j] = dado[i];
    }
}
