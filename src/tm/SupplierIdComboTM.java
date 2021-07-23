package tm;

public class SupplierIdComboTM {
    private String name;
    private String id;

    public SupplierIdComboTM(String name,String id) {

        this.name = name;
        this.id = id;
    }

    public SupplierIdComboTM() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id=id;
    }

    @Override
    public String toString() {
        return name+" "+id;
    }
}
