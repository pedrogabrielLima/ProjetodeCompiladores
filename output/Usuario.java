package com.poli.compiladores.model.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

@Entity
public class Usuario {

	private String Nome;
	private String Email;
	private Long Idade;
	private Boolean Logado;
	@Id
	private Long CPF;
	private Float Saldo;
	@Temporal(TemporalType.DATE)
	private Date DataNascimento;


	public String getNome(){
		return Nome;
	}

	public void setNome(String Nome){
		this.Nome = Nome;
	}

	public String getEmail(){
		return Email;
	}

	public void setEmail(String Email){
		this.Email = Email;
	}

	public Long getIdade(){
		return Idade;
	}

	public void setIdade(Long Idade){
		this.Idade = Idade;
	}

	public Boolean getLogado(){
		return Logado;
	}

	public void setLogado(Boolean Logado){
		this.Logado = Logado;
	}

	public Long getCPF(){
		return CPF;
	}

	public void setCPF(Long CPF){
		this.CPF = CPF;
	}

	public Float getSaldo(){
		return Saldo;
	}

	public void setSaldo(Float Saldo){
		this.Saldo = Saldo;
	}

	public Date getDataNascimento(){
		return DataNascimento;
	}

	public void setDataNascimento(Date DataNascimento){
		this.DataNascimento = DataNascimento;
	}

}