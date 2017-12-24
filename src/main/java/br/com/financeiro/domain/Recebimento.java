package br.com.financeiro.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Recebimento extends GenericDomain {

	@Column(nullable = false)
	private Date data;

	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;

	@Column(nullable = false)
	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private GrupoRecebimento grupoRecebimento;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public GrupoRecebimento getGrupoRecebimento() {
		return grupoRecebimento;
	}

	public void setGrupoRecebimento(GrupoRecebimento grupoRecebimento) {
		this.grupoRecebimento = grupoRecebimento;
	}
}