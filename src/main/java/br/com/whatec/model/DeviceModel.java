package br.com.whatec.model;

/**
 *
 * @author armani
 */
public class DeviceModel {
    private String name;
    private String endereco;

    public DeviceModel(String name, String endereco) {
        this.name = name;
        this.endereco = endereco;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
