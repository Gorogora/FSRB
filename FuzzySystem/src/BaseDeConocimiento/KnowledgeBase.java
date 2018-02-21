/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeConocimiento;

import BaseDeDatos.DataBase;
import BaseDeReglas.RuleBase;

/**
 * Almacena el conocimiento relativo al problema concreto.
 * @author ana
 */
public class KnowledgeBase {
    private DataBase db;
    private RuleBase rb;
    
    public KnowledgeBase(){
        db = new DataBase();
        rb = new RuleBase();
    }

    public KnowledgeBase(RuleBase rb, DataBase db) {
        this.db = db;
        this.rb = rb;
    }

    public DataBase getDb() {
        return db;
    }

    public void setDb(DataBase db) {
        this.db = db;
    }

    public RuleBase getRb() {
        return rb;
    }

    public void setRb(RuleBase rb) {
        this.rb = rb;
    }
    
}
