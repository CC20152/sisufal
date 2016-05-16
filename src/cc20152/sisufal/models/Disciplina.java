/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc20152.sisufal.models;

/**
 *
 * @author Dayvson
 */
public class Disciplina {
    
    private Integer id;
    private String nome;
    private Curso curso;
    private String cargaHoraria;
    private String turno;

    public Disciplina(){
        this.curso = new Curso();
    }
    
    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(String cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
 
    @Override
    public String toString(){
        if(this.id != 0){
            return this.id + " - " + this.nome; 
        }else{
            return this.nome;
        }
    }
    
}
