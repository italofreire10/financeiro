package br.com.financeiro.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Pagamento extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;

	@Column(length = 150)
	private String observacao;

	@ManyToOne
	@JoinColumn(nullable = false)
	private GrupoPagamento grupoPagamento;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Parcela parcela;

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

	public GrupoPagamento getGrupoPagamento() {
		return grupoPagamento;
	}

	public void setGrupoPagamento(GrupoPagamento grupoPagamento) {
		this.grupoPagamento = grupoPagamento;
	}

	public Parcela getParcela() {
		return parcela;
	}

	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
}
