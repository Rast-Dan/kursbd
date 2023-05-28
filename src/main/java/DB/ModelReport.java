package DB;

public class ModelReport {
    String model_name;
    Integer count_box;
    Integer count_car;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public Integer getCount_box() {
        return count_box;
    }

    public void setCount_box(Integer count_box) {
        this.count_box = count_box;
    }

    public Integer getCount_car() {
        return count_car;
    }

    public void setCount_car(Integer count_car) {
        this.count_car = count_car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelReport that)) return false;

        if (getModel_name() != null ? !getModel_name().equals(that.getModel_name()) : that.getModel_name() != null)
            return false;
        if (getCount_box() != null ? !getCount_box().equals(that.getCount_box()) : that.getCount_box() != null)
            return false;
        return getCount_car() != null ? getCount_car().equals(that.getCount_car()) : that.getCount_car() == null;
    }

    @Override
    public int hashCode() {
        int result = getModel_name() != null ? getModel_name().hashCode() : 0;
        result = 31 * result + (getCount_box() != null ? getCount_box().hashCode() : 0);
        result = 31 * result + (getCount_car() != null ? getCount_car().hashCode() : 0);
        return result;
    }

    public ModelReport(String model_name, Integer count_box, Integer count_car) {
        this.model_name = model_name;
        this.count_box = count_box;
        this.count_car = count_car;
    }
}
