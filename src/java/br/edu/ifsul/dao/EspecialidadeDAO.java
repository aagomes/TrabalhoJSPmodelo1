/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifsul.dao;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Especialidade;
import br.edu.ifsul.util.Util;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 *
 * @author alexandre
 */
public class EspecialidadeDAO implements Serializable{
    private Especialidade objetoSelecionado;// atributo que armazena o objeto que está sendo incluido/editado
    private String mensagem = ""; // atributo que armazena mensagens que serão exibidas ao usuario
    private EntityManager em; // objeto que executa as operações de persistência

    public EspecialidadeDAO() {
        em = EntityManagerUtil.getEntityManager(); // inicialização da entityManager
    }
    public boolean validaObjeto(Especialidade obj){
        // criação do objeto que valida os dados da classe Especialidade
        Validator validador = Validation.buildDefaultValidatorFactory().getValidator();
        // armazenando os erros de validação em uma lista
        Set<ConstraintViolation<Especialidade>> erros = validador.validate(obj);
        if (erros.size() > 0){ // caso caia neste teste o objeto tem erros
            mensagem = ""; // montando a mensagem com os erros para o usuário
            mensagem += "Objeto com erros: <br/>";
            for (ConstraintViolation<Especialidade> erro : erros){
                mensagem += "Erro: "+erro.getMessage()+"<br/>";
            }
            return false;
        } else {
            return true;
        }
    }
    public List<Especialidade> getLista(){
        /// consultando e retornando um entidade persistente        
        return em.createQuery("from Especialidade order by nome").getResultList();
    }
    
    public boolean salvar(Especialidade obj){
        try {
            em.getTransaction().begin();// inicia uma transação
            if (obj.getId() == null){
                em.persist(obj); // objeto novo
            } else {
                em.merge(obj); // objeto sendo alterado
            }
            em.getTransaction().commit(); // finalizando transação
            mensagem = "Objeto persistido com sucesso";
            return true;
        } catch (Exception e){
            if (em.getTransaction().isActive() == false){ // verificando se a transação não esta ativa
                em.getTransaction().begin();
            } 
            em.getTransaction().rollback(); // desfazendo possivel alterações que ocorreram 
            mensagem = "Erro ao persistir objeto: "+Util.getMensagemErro(e);
            return false;
        }
    }
    
    public boolean remover(Especialidade obj){
        try {
            em.getTransaction().begin();// inicia uma transação
            em.remove(obj); // removendo o objeto
            em.getTransaction().commit(); // finalizando transação
            mensagem = "Objeto removido com sucesso";
            return true;
        } catch (Exception e){
            if (em.getTransaction().isActive() == false){ // verificando se a transação não esta ativa
                em.getTransaction().begin();
            } 
            em.getTransaction().rollback(); // desfazendo possivel alterações que ocorreram 
            mensagem = "Erro ao remover objeto: "+Util.getMensagemErro(e);
            return false;
        }
    } 
    
    public Especialidade localizar(Integer id){
        return em.find(Especialidade.class, id);
    }

    public Especialidade getObjetoSelecionado() {
        return objetoSelecionado;
    }

    public void setObjetoSelecionado(Especialidade objetoSelecionado) {
        this.objetoSelecionado = objetoSelecionado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
