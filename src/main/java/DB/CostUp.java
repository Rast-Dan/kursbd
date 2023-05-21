package DB;

public class CostUp {
    Double coef;

    public Double getCoef() {
        return coef;
    }

    public void setCoef(Double coef) {
        this.coef = coef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CostUp costUp)) return false;

        return getCoef().equals(costUp.getCoef());
    }

    @Override
    public int hashCode() {
        return getCoef().hashCode();
    }
}
