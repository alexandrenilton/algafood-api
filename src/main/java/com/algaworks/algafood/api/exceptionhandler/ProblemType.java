package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ENTIDADE_NAO_ENCONTRADA("/entity-not-found", "Entidade não encontrada"), 
	ENTIDADE_EM_USO("/entity-in-use", "Entidade em uso"),
	ERRO_NEGOCIO("/error-negocio", "Violação de regra de negócio"),
	MENSAGEM_INCOMPREENSIVEL("/incomprehensible-message", "Incomprehensible Message");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://api.intern.com" + path;
		this.title = title;
	}
}
