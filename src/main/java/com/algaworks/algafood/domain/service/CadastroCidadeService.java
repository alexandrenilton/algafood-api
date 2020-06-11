package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
	
	private static final String MSG_CIDADE_EM_USO  = 
	        "Cidade de código %d não pode ser removido, pois está em uso";

	private static final String MSG_CIDADE_NAO_ENCONTRADO = 
	        "Não existe um cadastro de cidade com código %d";
	
	@Transactional
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
	}
	
	public Cidade buscarOrFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow( () -> new CidadeNaoEncontradaException(
						String.format(MSG_CIDADE_NAO_ENCONTRADO, cidadeId)) );
	}
	
}
