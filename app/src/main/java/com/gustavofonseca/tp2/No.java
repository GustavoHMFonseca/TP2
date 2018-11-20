package com.gustavofonseca.tp2;

import java.io.Serializable;
import java.util.ArrayList;

public class No implements Serializable {
    private String nome;
    private String dados;
    private boolean isDocument;
    private ArrayList<No> filhos;


    public No (String nome, String dados, boolean isDocument)
    {
        this.nome = nome;
        this.dados = dados;
        this.isDocument = isDocument;
        this.filhos = new ArrayList<>();
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public String getNome()
    {
        return this.nome;
    }
    public boolean getIsDocument(){return this.isDocument;}
    public String getDados(){return this.dados;}


    public ArrayList<No> getFilhos()
    {
        return filhos;
    }

    public void addFilhos(No no)
    {
        filhos.add(no);
    }

    public void setFilhos(No no, int position)
    {
        filhos.set(position,no);
    }

    public void removeFilhos(int position)
    {
        filhos.remove(position);
    }
}
