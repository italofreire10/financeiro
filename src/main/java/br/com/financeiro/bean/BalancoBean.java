package br.com.financeiro.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.financeiro.dao.PagamentoDAO;
import br.com.financeiro.dao.RecebimentoDAO;
import br.com.financeiro.domain.Pagamento;
import br.com.financeiro.domain.Recebimento;

@ManagedBean
@ViewScoped
public class BalancoBean {
	private List<Pagamento> pagamentos;
	private List<Recebimento> recebimentos;
	private BigDecimal totalPagamento, totalRecebimento, saldo;
	private Date dataInicio, dataFim;

	public List<Pagamento> getPagamentos() {
		return pagamentos;
	}

	public void setPagamentos(List<Pagamento> pagamentos) {
		this.pagamentos = pagamentos;
	}

	public List<Recebimento> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public BigDecimal getTotalPagamento() {
		return totalPagamento;
	}

	public void setTotalPagamento(BigDecimal totalPagamento) {
		this.totalPagamento = totalPagamento;
	}

	public BigDecimal getTotalRecebimento() {
		return totalRecebimento;
	}

	public void setTotalRecebimento(BigDecimal totalRecebimento) {
		this.totalRecebimento = totalRecebimento;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void listar() {
		totalPagamento = new BigDecimal("0");
		totalRecebimento = new BigDecimal("0");
		saldo = new BigDecimal("0");
		try {
			PagamentoDAO pagamentoDAO = new PagamentoDAO();
			pagamentos = pagamentoDAO.listar("data");
			RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
			recebimentos = recebimentoDAO.listar("data");

			for (Pagamento pagamento : pagamentos) {
				if ((pagamento.getData().after(dataInicio) || pagamento.getData().equals(dataInicio))
						&& (pagamento.getData().before(dataFim) || pagamento.getData().equals(dataFim))) {
					totalPagamento = totalPagamento.add(pagamento.getValor());
				}
			}

			for (Recebimento recebimento : recebimentos) {
				if ((recebimento.getData().after(dataInicio) || recebimento.getData().equals(dataInicio))
						&& (recebimento.getData().before(dataFim) || recebimento.getData().equals(dataFim))) {
					totalRecebimento = totalRecebimento.add(recebimento.getValor());
				}
			}
			
			saldo = (saldo.add(totalRecebimento)).subtract(totalPagamento);

		} catch (RuntimeException e) {
			Messages.addGlobalError("Erro ao listar.");
			e.printStackTrace();
		}
	}
}