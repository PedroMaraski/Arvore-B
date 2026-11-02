public class ArvoreB {
    NoB raiz;
    int t;

    ArvoreB(int t) {
        this.t = t;
        this.raiz = new NoB(t, true);
    }

    String buscar(int k) {
        NoB x = raiz;
        while (x != null) {
            int i = 0;
            while (i < x.n && k > x.chave[i]) {
                i++;
            }
            if (i < x.n && k == x.chave[i]) {
                return x.dado[i];
            }
            if (x.folha) {
                return null;
            }
            x = x.filho[i];
        }
        return null;
    }

    void dividirFilho(NoB x, int i) {
        NoB y = x.filho[i];
        NoB z = new NoB(t, y.folha);
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            y.copiarChave(j + t, z, j);
        }
        if (!y.folha) {
            for (int j = 0; j < t; j++) {
                z.filho[j] = y.filho[j + t];
            }
        }
        for (int j = x.n; j > i; j--) {
            x.filho[j + 1] = x.filho[j];
        }
        x.filho[i + 1] = z;
        for (int j = x.n - 1; j >= i; j--) {
            x.copiarChave(j, x, j + 1);
        }
        y.copiarChave(t - 1, x, i);
        y.n = t - 1;
        x.n++;
    }

    void inserirNaoCheio(NoB x, int k, String d) {
        while (!x.folha) {
            int i = x.n - 1;
            while (i >= 0 && k < x.chave[i]) {
                i--;
            }
            i++;
            if (x.filho[i].n == 2 * t - 1) {
                dividirFilho(x, i);
                if (k > x.chave[i]) {
                    i++;
                }
            }
            x = x.filho[i];
        }
        int i = x.n - 1;
        while (i >= 0 && k < x.chave[i]) {
            x.copiarChave(i, x, i + 1);
            i--;
        }
        x.chave[i + 1] = k;
        x.dado[i + 1] = d;
        x.n++;
    }

    boolean inserir(int k, String d) {
        if (buscar(k) != null) {
            return false;
        }
        if (raiz.n == 2 * t - 1) {
            NoB s = new NoB(t, false);
            s.filho[0] = raiz;
            raiz = s;
            dividirFilho(s, 0);
        }
        inserirNaoCheio(raiz, k, d);
        return true;
    }
    boolean remover(int k) {
        if (buscar(k) == null) {
            return false;
        }
        NoB x = raiz;
        while (true) {
            int i = 0;
            while (i < x.n && k > x.chave[i]) {
                i++;
            }
            if (i < x.n && k == x.chave[i]) {
                if (x.folha) {
                    // caso 1
                    for (int j = i; j < x.n - 1; j++) {
                        x.copiarChave(j + 1, x, j);
                    }
                    x.n--;
                    return true;
                }
                NoB y = x.filho[i];
                NoB z = x.filho[i + 1];
                if (y.n >= t) {
                    
                    NoB p = y;
                    while (!p.folha) {
                        p = p.filho[p.n];
                    }
                    p.copiarChave(p.n - 1, x, i);
                    k = x.chave[i];
                    x = y;
                } else if (z.n >= t) {
                    
                    NoB p = z;
                    while (!p.folha) {
                        p = p.filho[0];
                    }
                    p.copiarChave(0, x, i);
                    k = x.chave[i];
                    x = z;
                } else {
    
                    fundir(x, i);
                    if (x == raiz && x.n == 0) {
                        raiz = y;
                    }
                    x = y;
                }
            } else {
                if (x.folha) {
                    return false;
                }
                if (x.filho[i].n == t - 1) {
                    // caso 3
                    if (i > 0 && x.filho[i - 1].n >= t) {
                        emprestarDoAnterior(x, i);
                    } else if (i < x.n && x.filho[i + 1].n >= t) {
                        emprestarDoProximo(x, i);
                    } else if (i < x.n) {
                        fundir(x, i);
                    } else {
                        fundir(x, i - 1);
                        i--;
                    }
                }
                NoB prox = x.filho[i];
                if (x == raiz && x.n == 0) {
                    raiz = prox;
                }
                x = prox;
            }
        }
    }

    void fundir(NoB x, int i) {
        NoB y = x.filho[i];
        NoB z = x.filho[i + 1];
        x.copiarChave(i, y, t - 1);
        for (int j = 0; j < t - 1; j++) {
            z.copiarChave(j, y, t + j);
        }
        if (!y.folha) {
            for (int j = 0; j < t; j++) {
                y.filho[t + j] = z.filho[j];
            }
        }
        y.n = 2 * t - 1;
        for (int j = i; j < x.n - 1; j++) {
            x.copiarChave(j + 1, x, j);
        }
        for (int j = i + 1; j < x.n; j++) {
            x.filho[j] = x.filho[j + 1];
        }
        x.filho[x.n] = null;
        x.n--;
    }

    void emprestarDoAnterior(NoB x, int i) {
        NoB c = x.filho[i];
        NoB e = x.filho[i - 1];
        for (int j = c.n; j > 0; j--) {
            c.copiarChave(j - 1, c, j);
        }
        if (!c.folha) {
            for (int j = c.n + 1; j > 0; j--) {
                c.filho[j] = c.filho[j - 1];
            }
            c.filho[0] = e.filho[e.n];
        }
        x.copiarChave(i - 1, c, 0);
        e.copiarChave(e.n - 1, x, i - 1);
        e.n--;
        c.n++;
    }

    void emprestarDoProximo(NoB x, int i) {
        NoB c = x.filho[i];
        NoB d = x.filho[i + 1];
        x.copiarChave(i, c, c.n);
        if (!c.folha) {
            c.filho[c.n + 1] = d.filho[0];
        }
        d.copiarChave(0, x, i);
        for (int j = 0; j < d.n - 1; j++) {
            d.copiarChave(j + 1, d, j);
        }
        if (!d.folha) {
            for (int j = 0; j < d.n; j++) {
                d.filho[j] = d.filho[j + 1];
            }
        }
        d.n--;
        c.n++;
    }

    void imprimir() {
        NoB[] fila = new NoB[10000];
        int ini = 0;
        int fim = 0;
        fila[fim] = raiz;
        fim++;
        int nivel = 0;
        while (ini < fim) {
            int qtd = fim - ini;
            System.out.print("Nível " + nivel + ": ");
            for (int q = 0; q < qtd; q++) {
                NoB no = fila[ini];
                ini++;
                System.out.print("[");
                for (int j = 0; j < no.n; j++) {
                    if (j > 0) {
                        System.out.print(" ");
                    }
                    System.out.print(no.chave[j]);
                }
                System.out.print("] ");
                if (!no.folha) {
                    for (int j = 0; j <= no.n; j++) {
                        fila[fim] = no.filho[j];
                        fim++;
                    }
                }
            }
            System.out.println();
            nivel++;
        }
    }
}
