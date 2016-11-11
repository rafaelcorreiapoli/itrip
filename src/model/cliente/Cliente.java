package model.cliente;
import java.util.Date;

/**
 * Created by rafa93br on 04/11/16.
 */
public class Cliente {
    private int id;
    private String cpf;
    private String nome;
    private Date nascimento;
    private Boolean sexo;
    private String email;
    private String telefone;


    public Cliente(int id, String cpf, String nome, Date nascimento, Boolean sexo, String email, String telefone) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNascimento() {return nascimento;}

    public void setNascimento(Date nascimento) {this.nascimento = nascimento;}

    public Boolean getSexo() {return sexo;}

    public void setSexo(Boolean sexo) {this.sexo = sexo;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getTelefone() {return telefone;}

    public void setTelefone(String telefone) {this.telefone = telefone;}
}
