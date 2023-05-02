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
	protected Date dia;
	protected Time hora;


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

	public Usuario(String nome, java.util.Date dia, Time hora) {
		super();
		this.nome = nome;
		this.dia = (Date) dia;
		this.hora = hora;
	}

	public Usuario(int codigo, String nome, java.util.Date dia, Time hora) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dia = (Date) dia;
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

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}
}