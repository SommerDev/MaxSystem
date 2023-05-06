package modelo;

import java.sql.Date;
import java.sql.Time;


/**
 * Esta classe modelo (JavaBean) representa uma entidade de usuário.
 *
 */

public class Usuario {
	protected int codigo;
	protected String nome;
	protected String dia;
	protected String hora;


/*	public Usuario(String nome, Date dia, Time hora) {
		super();
		this.nome = nome;
		this.dia = dia;
		this.hora = hora;
	}

	public Usuario(int codigo, String nome, Date dia, Time hora) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dia = dia;
		this.hora = hora;
	}   */

	public Usuario(String nome, String dia, String hora) {
		super();
		this.nome = nome;
		this.dia = dia;
		this.hora = hora;
	}

	public Usuario(int codigo, String nome, String dia, String hora) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dia = dia;
		this.hora = hora;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
}