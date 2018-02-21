/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDeDatos;

/**
 * Define una etiqueta lingüística.
 * @author ana
 */
public class Triangulo {
    /**
     * Guarda las coordenadas de la etiqueta lingüística
     */
    private double x0, x1, x2;
    
    private double punto_max_value;
    
    /**
     * Identifica a qué antecedente o consecuente hace referencia la etiqueta
     */
    private int id = 0;
    
    public Triangulo(double x0, double x1, double x2, int id) {
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        punto_max_value = x1;
        this.id = id;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPunto_max_value() {
        return punto_max_value;
    }

    public void setPunto_max_value(double punto_max_value) {
        this.punto_max_value = punto_max_value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x0) ^ (Double.doubleToLongBits(this.x0) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x1) ^ (Double.doubleToLongBits(this.x1) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.x2) ^ (Double.doubleToLongBits(this.x2) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.punto_max_value) ^ (Double.doubleToLongBits(this.punto_max_value) >>> 32));
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Triangulo other = (Triangulo) obj;
        if (Double.doubleToLongBits(this.x0) != Double.doubleToLongBits(other.x0)) {
            return false;
        }
        if (Double.doubleToLongBits(this.x1) != Double.doubleToLongBits(other.x1)) {
            return false;
        }
        if (Double.doubleToLongBits(this.x2) != Double.doubleToLongBits(other.x2)) {
            return false;
        }
        if (Double.doubleToLongBits(this.punto_max_value) != Double.doubleToLongBits(other.punto_max_value)) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public void calcularPuntoMaxValue() {
        punto_max_value = x1;
    }
    
    
}
