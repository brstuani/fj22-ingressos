package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {

	private List<Sessao> sessoesDaSala;
	
	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}
	
	public boolean cabe(Sessao sessaoNova) {
		System.out.println("metodo cabe");
		if(terminaAmanha(sessaoNova)) {
			return false;
		}
		
		return sessoesDaSala.stream().noneMatch(sessaoExistente -> 
											    horarioIsConfitante(sessaoExistente, sessaoNova));
		
	}
	
	private boolean terminaAmanha(Sessao sessao) {
		System.out.println("metodo terminaAmanha");
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		
		if(terminoSessaoNova.isAfter(ultimoSegundoDeHoje)) {
			return true;
		}
		return false;
	}
	
	private boolean horarioIsConfitante(Sessao sessaoExistente, Sessao sessaoNova) {
		System.out.println("metodo gconflito");
		LocalDateTime inicioSessaoExistente = getInicioSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime terminoSessaoExistente = getTerminoSessaoComDiaDeHoje(sessaoNova);
		System.out.println("meio");
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessaoNova);
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessaoNova);
		System.out.println("preencheu todas variaveis ");
		
		boolean sessaoNovaTerminaAntesDaExistente = terminoSessaoNova.isBefore(inicioSessaoExistente);
		boolean sessaoNovaComecaDepoisDaExistente = terminoSessaoExistente.isBefore(inicioSessaoNova);
		
		if(sessaoNovaTerminaAntesDaExistente || sessaoNovaComecaDepoisDaExistente) {
			
			return false;
		}
		return true;
	}
	
	private LocalDateTime getInicioSessaoComDiaDeHoje(Sessao sessao) {
		System.out.println("metodo getInicio");
		LocalDate hoje = LocalDate.now();
		
		return sessao.getHorario().atDate(hoje);
	}
	
	private LocalDateTime getTerminoSessaoComDiaDeHoje(Sessao sessao) {
		System.out.println("metodo getTermino");
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessao);
		
		return inicioSessaoNova.plus(sessao.getFilme().getDuracao());
	}
}
