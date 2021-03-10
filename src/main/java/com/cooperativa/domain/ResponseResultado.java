package com.cooperativa.domain;

public class ResponseResultado {
	private String nomePauta;
	private String totalVotos;
	private String votosSim;
	private String votosNao;

	public String getNomePauta() {
		return nomePauta;
	}

	public void setNomePauta(String nomePauta) {
		this.nomePauta = nomePauta;
	}

	public String getTotalVotos() {
		return totalVotos;
	}

	public void setTotalVotos(String totalVotos) {
		this.totalVotos = totalVotos;
	}

	public String getVotosSim() {
		return votosSim;
	}

	public void setVotosSim(String votosSim) {
		this.votosSim = votosSim;
	}

	public String getVotosNao() {
		return votosNao;
	}

	public void setVotosNao(String votosNao) {
		this.votosNao = votosNao;
	}

}
